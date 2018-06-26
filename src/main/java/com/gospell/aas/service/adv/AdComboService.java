package com.gospell.aas.service.adv;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.adv.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.CalculateUtil;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.adv.AdComboUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.dto.adv.AdChannelForComboDTO;
import com.gospell.aas.dto.adv.AdComboByUserDTO;
import com.gospell.aas.dto.adv.AdComboCanSellQueryDTO;
import com.gospell.aas.dto.adv.AdComboNotChannelSellDTO;
import com.gospell.aas.dto.adv.AdComboStatisticSellDTO;
import com.gospell.aas.dto.adv.AdSellDTO;
import com.gospell.aas.dto.adv.AdcomboCanSellDTO;
import com.gospell.aas.dto.adv.AdcomboSellDTO;
import com.gospell.aas.dto.adv.ChildAdTypeDTO;
import com.gospell.aas.dto.adv.ComboAdvtiserSellDTO;
import com.gospell.aas.dto.adv.ComboCountDTO;
import com.gospell.aas.dto.adv.ComboSellDetailDTO;
import com.gospell.aas.dto.adv.SelectChannelDTO;
import com.gospell.aas.dto.adv.SelectNetworkDTO;
import com.gospell.aas.dto.adv.TypeDTO;
import com.gospell.aas.dto.push.PushAdComboDTO;
import com.gospell.aas.repository.hibernate.adv.AdComboDao;
import com.gospell.aas.repository.hibernate.adv.AdComboDistrictDao;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDao;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDistrictDao;
import com.gospell.aas.repository.hibernate.adv.AdRangeDao;
import com.gospell.aas.repository.hibernate.adv.AdTypeDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboPushFailRecordDao;
import com.gospell.aas.repository.mybatis.adv.IAdRangeDao;
import com.gospell.aas.repository.mybatis.adv.IAdSellDao;
import com.gospell.aas.repository.mybatis.adv.IAdTypeDao;
import com.gospell.aas.repository.mybatis.adv.IChannelDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.webservice.netty.client.Client;

@Service
@Transactional(readOnly = true)
public class AdComboService extends BaseService {

	@Autowired
	private AdComboDao thisDao;
	
	@Autowired
	private AdNetworkDao adNetworkDao;
	
	@Autowired
	private IChannelDao channelDao;
	@Autowired
	private AdTypeDao typeDao;

	@Autowired
	private IAdComboDao mybatisDao;

	@Autowired
	private IAdSellDao sellDao;

	@Autowired
	private IAdTypeDao typeMybatisDao;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	@Autowired
	private INetworkDao networkDao;

	@Autowired
	private IAdComboPushFailRecordDao middleTableDao;

	@Autowired
	private AdelementService adelementService;
	
	@Autowired
	private AdRangeDao adRangeDao;
	
	@Autowired
	private IAdRangeDao IadRangeDao;
	
	@Autowired
	private AdComboDistrictDao adComboDistrictDao;
	
	@Autowired
	private AdNetworkDistrictDao adNetworkDistrictDao;
	
	private Logger logger = LoggerFactory.getLogger(AdComboService.class);

	public AdCombo get(String id) {
		/*thisDao.clear();
		AdCombo combo = thisDao.get(id);
		return combo;*/
		return thisDao.get(id);
	}

	public void clear() {
		thisDao.clear();
	}

	/**
	 * 根据条件查询广告套餐
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告套餐
	 * @return
	 */
	public Page<AdCombo> find(Page<AdCombo> page, AdCombo entity) {

		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("c.is_valid desc,t.id asc,c.valid_start_time desc");
		}else{
			String order = page.getOrderBy();
			String[] orders = order.split(",")[0].split(" ");
			if(orders[0].contains("c.valid_start_time")){
				order = "c.is_valid desc,t.id asc"+",c.valid_start_time "+orders[1]+",c.valid_end_time "+orders[1]+ ",c.start_hour "+orders[1]+",c.start_minutes "+orders[1]+",c.start_second "+orders[1];
			}
			if(orders[0].contains("c.valid_end_time")){
				order = "c.is_valid desc,t.id asc"+",c.valid_end_time "+orders[1]+",c.valid_start_time "+orders[1]+ ",c.start_hour "+orders[1]+",c.start_minutes "+orders[1]+",c.start_second "+orders[1];
			}
			page.setOrderBy(order);
		}

		entity.setPage(page);
		List<AdCombo> list = mybatisDao.findAll(entity);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

			}
		}
		page.setList(list);
		return page;
	}

	@Transactional(readOnly = false)
	public void save(AdCombo entity) throws  Exception {
		thisDao.clear();
		 if(StringUtils.isBlank(entity.getId())){
			 entity.setId(IdGen.uuid());
			 entity.setCreateBy(UserUtils.getUser());
			 entity.setCreateDate(new Date());
		 }
		Integer isFlag = entity.getIsFlag();
		// 保证信息的准确性
		if (AdCombo.ADCOMOBO_NETWORK_ISFLAG.equals(isFlag)) {// 跟频道无关	
			if (null != entity.getChannelList()
					&& entity.getChannelList().size() > 0) {
				entity.setChannelList(null);
			}
			if (null != entity.getWeek())
				entity.setWeek(null);
			if (null != entity.getStartHour()) {
				entity.setStartHour(null);
			}
			if (null != entity.getEndHour()) {
				entity.setEndHour(null);
			}
			if (null != entity.getStartMinutes()) {
				entity.setStartMinutes(null);
			}
			if (null != entity.getStartSecond()) {
				entity.setStartSecond(null);
			}
			if (null != entity.getEndMinutes()) {
				entity.setEndMinutes(null);
			}
			if (null != entity.getEndMinutes()) {
				entity.setEndMinutes(null);
			}
			if (null != entity.getShowTime())
				entity.setShowTime(null);
			if (null != entity.getShowCount())
				entity.setShowCount(null);
			if(null != entity.getPictureInterval())
				entity.setPictureInterval(null);
			if(null != entity.getPictureTimes())
				entity.setPictureTimes(null);
/*            if(null != entity.getValidStartTime())
            	entity.setValidStartTime(null);
            if(null != entity.getValidEndTime())
            	entity.setValidEndTime(null);*/
			AdType type = entity.getAdType();
			if (null != type) {
				AdType adType = typeDao.get(type.getId());
				entity.setAdType(adType);
				typeDao.clear();
			}
		} else {
			if (null != entity.getNetworkIdList()
					&& entity.getNetworkIdList().size() > 0) {
				entity.setNetworkList(null);
			}

			AdType type = entity.getAdType();
			if (null != type
					&& !type.getId().equals(AdType.TYPE_INSERT_SCREEN_ID)
					&& !type.getId().equals(AdType.TYPE_ROLL_ADV_ID)) {
				if (null != entity.getShowTime())
					entity.setShowTime(null);
				if (null != entity.getShowCount())
					entity.setShowCount(null);
			}
			AdRange sdrange = entity.getSdRange();
			if(null != sdrange){
				sdrange = adRangeDao.get(sdrange.getId());
				entity.setSdRange(sdrange);
				adRangeDao.clear();
			}
			
			AdRange hdrange = entity.getHdRange();
			if(null != hdrange){
				hdrange = adRangeDao.get(hdrange.getId());
				entity.setHdRange(hdrange);
				adRangeDao.clear();
			}
		}
		thisDao.clear();
		
		//设置套餐设置区域
		
		/*String selArea = entity.getSelArea();
		adComboDistrictDao.deleteComDis(entity.getId());
		if(StringUtils.isNotBlank(selArea)){
			String[] list = selArea.split("-");
			List<AdNetworkDistrict> adDistrictCategorys = new ArrayList<AdNetworkDistrict>();
			for (String string : list) {
				
				AdComboDistrict acd = new AdComboDistrict();
				acd.setId(IdGen.uuid());
				acd.setAdcombo(entity);
			}

		}
		*/
		entity.setIsValid(AdCombo.ADCOMOBO_YES_VALID);
		thisDao.save(entity);

	}

	/**
	 * 当状态是已运营的时候，推送广告套餐到客户端
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void pushAdcomboToClient(AdCombo entity) throws Exception {
		int status = entity.getStatus();
		if (status == AdCombo.ADCOMOBO_ALREADY_STATUS) {// 已运营的套餐才进行推送
			List<String> clientIds = getClientIdsByAdv(entity);
			for (String string : clientIds) {
				PushAdComboDTO dto = getPushAdCombo1(entity,string);	
				// 在推送以前把数据插入到数据库中间表，防止在推送过程中出现客户端连接正常，但是没有收到数据，这样数据就不会出现不完整的情况
				insertSendFail(clientIds, entity,
						AdComboPushFailRecord.PUSH_CLIENT_ADCOMBO_FAIL);
				Client.getInstance().putAdcombo1(string, dto);
				
			}

			List<String> list = Lists.newArrayList();
			list.add(entity.getId());

			mybatisDao.updateIsPut(list);
			logger.info("更新套餐状态为已投放成功！");

			// 当投放过的广告改为可运营或者是未生效的时候，需要通知发送器删除该套餐
		} else if (entity.getStatus() != AdCombo.ADCOMOBO_ALREADY_STATUS
				&& entity.getIsPut() == AdCombo.ADCOMOBO_YES_PUT) {
			List<String> clientIds = getClientIdsByAdv(entity);
		 
			insertSendFail(clientIds, entity,
					AdComboPushFailRecord.DELETE_CLIENT_ADCOMBO_FAIL);// 道理同上
			Client.getInstance().deleteAdCombo(entity.getId(), clientIds);

		}

	}

	/**
	 * 获取单个套餐的信息信息
	 * 
	 * @param combo
	 * @return
	 * @throws Exception
	 */
	public PushAdComboDTO getPushAdCombo1(AdCombo combo,String networkId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", combo.getDelFlag());
		map.put("comboId", combo.getId());
		Integer isFlag = combo.getIsFlag();
		PushAdComboDTO dto = null;
		if (isFlag == AdCombo.ADCOMOBO_CHANNEL_ISFLAG) {
			AdCombo adcombo = mybatisDao.getSingleAdComboChannel(map);
			if (null != adcombo) {
				dto = new PushAdComboDTO();
				setAdComboDTO(adcombo, dto,networkId);
				List<AdChannel> channelList = adcombo.getChannelList();
				if (null != channelList && channelList.size() > 0) {
					dto.setChannelList(channelList);
				}

			}
		} else {
			AdCombo adcombo = mybatisDao.getSingleAdComboNotChannel(map);
			dto = new PushAdComboDTO();
			setAdComboDTO(adcombo, dto,networkId);

		}

		return dto;
	}

	/**
	 * 连接netty服务器异常的时候，需要把广告写入中间表
	 * 
	 */
	@Transactional(readOnly = false)
	public void insertSendFail(List<String> clienIdList, AdCombo entity,
			Integer status) {

		List<AdComboPushFailRecord> insert_list = Lists.newArrayList();
		for (int i = 0; i < clienIdList.size(); i++) {
			AdComboPushFailRecord insert = new AdComboPushFailRecord();
			insert.setId(IdGen.uuid());
			insert.setCreateDate(new Date());
			insert.setComboId(entity.getId());
			insert.setClientId(clienIdList.get(i));
			insert.setStatus(status);
			insert_list.add(insert);
		}
		try {
			middleTableDao.insertEntity(insert_list);
			logger.info("连接netty失败，数据写入中间库成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("连接netty失败，数据写入中间库失败");

		}

	}
	
	/**
	 * 连接netty服务器异常的时候，需要把广告写入中间表
	 * 
	 */
	@Transactional(readOnly = false)
	public void insertSendFail(String clienId, AdCombo entity,
			Integer status) {

		List<AdComboPushFailRecord> insert_list = Lists.newArrayList();
			AdComboPushFailRecord insert = new AdComboPushFailRecord();
			insert.setId(IdGen.uuid());
			insert.setCreateDate(new Date());
			insert.setComboId(entity.getId());
			insert.setClientId(clienId);
			insert.setStatus(status);
			insert_list.add(insert);
		try {
			middleTableDao.insertEntity(insert_list);
			logger.info("连接netty失败，数据写入中间库成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("连接netty失败，数据写入中间库失败");

		}

	}

	public void setAdComboDTO(AdCombo combo, PushAdComboDTO dto,String clientId) {
		if (null != combo.getAdType())
			dto.setAdTypeId(combo.getAdType().getId());
		dto.setComName(combo.getComboName());
		dto.setEndTime(combo.getEndTime());
		dto.setStartTime(combo.getStartTime());
		if (null != combo.getShowTime()) {
			dto.setShowTime(combo.getShowTime());
		} else {
			dto.setShowTime(0);
		}
		if (null != combo.getIntervalTime()) {
			dto.setIntervalTime(combo.getIntervalTime());
		} else {
			dto.setIntervalTime(0);
		}
		if (null != combo.getShowCount()) {
			dto.setShowCount(combo.getShowCount());
		} else {
			dto.setShowCount(0);
		}
		if (null != combo.getPictureInterval()) {
			dto.setPictureIntervalTime(combo.getPictureInterval());
		} else {
			dto.setPictureIntervalTime(0);
		}

		if (null != combo.getPictureTimes()) {
			dto.setPictureShowCount(combo.getPictureTimes());
		} else {
			dto.setPictureShowCount(0);
		}
		dto.setId(combo.getId());
		dto.setIsFlag(combo.getIsFlag());
		dto.setWeek(combo.getWeek());


		List<AdChannel> list = Lists.newArrayList();
		dto.setChannelList(list);
		
		if(combo.getSendMode() == 2){
			String regionIds = "";
			if(StringUtils.isNotBlank(combo.getSelArea())){
				AdNetwork network = adNetworkDao.findByNetworkId(clientId);
				if(null != network){
					List<AdNetworkDistrict> netL =  adNetworkDistrictDao.getNetsByParam(combo.getSelArea(), network.getId());	
					for (AdNetworkDistrict adNetworkDistrict : netL) {
						if(null != adNetworkDistrict.getSelfDistrictId()){
							regionIds += adNetworkDistrict.getSelfDistrictId() + ",";									
						}
					}
					if(StringUtils.isNotBlank(regionIds)){
						regionIds = regionIds.substring(0, regionIds.lastIndexOf(","));
					}
				}
				System.out.println(regionIds);
			}
			dto.setRegionId(regionIds);
		}

		//设置插屏轨迹信息
		if(combo.getAdType().getId().equals(AdType.TYPE_INSERT_SCREEN_ID) && combo.getTrackMode().equals(2)){
			AdTrack sdTrack = combo.getSdTrack();
			if(null != sdTrack){
				dto.setSdCoordinates(sdTrack.getCoordinates());
				dto.setSdCpShowTime(String.valueOf(sdTrack.getShowTime()));
			}

			AdTrack hdTrack = combo.getHdTrack();
			if(null != hdTrack){
				dto.setHdCoordinates(hdTrack.getCoordinates());
				dto.setHdCpShowTime(String.valueOf(hdTrack.getShowTime()));
			}
		}
	}

	/**
	 * 通过广告获取广告对应的发送器id
	 * 
	 * @param combo
	 * @return
	 */
	public List<String> getClientIdsByAdv(AdCombo combo) {
		List<String> clientIds = Lists.newArrayList();
		List<AdNetwork> list = null;
		String comboId = combo.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboId", comboId);
		int isflag = combo.getIsFlag();
		if (isflag == AdCombo.ADCOMOBO_NETWORK_ISFLAG) {
			list = networkDao.getNetworkByComboId(map);
		} else {
			list = networkDao.getNetworkByComboAndChannel(map);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				clientIds.add(list.get(i).getNetworkId());
			}
		}
		return clientIds;
	}

	public AdCombo findAdComboByComboId(String comboId) {
		return thisDao.findAdComboByComboId(comboId);
	}

	public List<AdCombo> findAdComboByComboName(String comboName) throws Exception {
		AdCombo combo = new AdCombo();
		combo.setComboName(comboName);
		combo.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		return mybatisDao.findAdComboByComboName(combo);
	}

	/**
	 * 修改时获取用户以前选择的频道和发送器
	 * 
	 * @param pageMap
	 * @return
	 */
	public List<SelectNetworkDTO> findSelectChByComboId(
			Map<String, Object> pageMap) {
		String comboId = (String) pageMap.get("comboId");

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", comboId);
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<AdChannelForComboDTO> list = null;
		try {
			list = channelDao.getChannelByComboId(queryMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SelectNetworkDTO> returnList = Lists.newArrayList();
		if (null != list && list.size() > 0) {

			AdChannelForComboDTO d = list.get(0);
			boolean b = true;
			if (pageMap.containsKey("comboId") && pageMap.size() == 1) {// 表示点击了修改
				b = true;
			} else {
				b = AdComboUtils.compareCombo(d, pageMap);
			}

			if (!b) {
				checkChannelIsRepeat(list, pageMap);
			}
			String typeId = d.getTypeId();
			Map<String, List<SelectChannelDTO>> map = new HashMap<String, List<SelectChannelDTO>>();
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					AdChannelForComboDTO dto = list.get(i);
					List<SelectChannelDTO> channelList = null;
					String networkId = dto.getNetworkId();
					if (!map.isEmpty() && map.containsKey(networkId)) {
						channelList = map.get(dto.getNetworkId());

					} else {
						channelList = Lists.newArrayList();
					}
					SelectChannelDTO cDto = new SelectChannelDTO();
					cDto.setChannelId(dto.getChannelId());
					cDto.setChannelName(dto.getChannelName());
					channelList.add(cDto);
					map.put(networkId, channelList);
				}
				for (String key : map.keySet()) {
					SelectNetworkDTO sDto = new SelectNetworkDTO();
					sDto.setNetworkId(key);
					sDto.setTypeId(typeId);
					sDto.setChannelList(map.get(key));
					returnList.add(sDto);
				}
			}
		}
		return returnList;
	}

	public void checkChannelIsRepeat(List<AdChannelForComboDTO> list,
			Map<String, Object> map) {
		List<String> channelIdList = Lists.newArrayList();

		for (int i = 0; i < list.size(); i++) {
			AdChannelForComboDTO dto = list.get(i);
			channelIdList.add(dto.getChannelId());

		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("ids", channelIdList);
		queryMap.put("startDate", map.get("startDate"));
		queryMap.put("endDate", map.get("endDate"));
		queryMap.put("startHour",
				Integer.parseInt((String) map.get("startHour")));
		queryMap.put("endHour", Integer.parseInt((String) map.get("endHour")));
		queryMap.put("endMinutes",
				Integer.parseInt((String) map.get("endMinute")));
		queryMap.put("endSecond",
				Integer.parseInt((String) map.get("endSecond")));
		queryMap.put("startMinutes",
				Integer.parseInt((String) map.get("startMinute")));
		queryMap.put("startSecond",
				Integer.parseInt((String) map.get("startSecond")));
		queryMap.put("comboId", map.get("comboId"));

		List<AdChannel> channelList = null;
		try {
			channelList = channelDao.getChannelNotRepeat(queryMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != channelList && channelIdList.size() > 0) {
			Map<String, AdChannelForComboDTO> list_map = new HashMap<>();
			for (int i = 0; i < list.size(); i++) {
				list_map.put(list.get(i).getChannelId(), list.get(i));
			}
			list.clear();
			for (int i = 0; i < channelList.size(); i++) {
				AdChannel channel = channelList.get(i);
				if (list_map.containsKey(channel.getId())) {
					list_map.remove(channel.getId());
				}

			}
			if (!list_map.isEmpty()) {

				for (String key : list_map.keySet()) {
					list.add(list_map.get(key));
				}
			}
		}

	}

	public void set(Map<String, Object> map, AdChannelForComboDTO dto)
			throws ParseException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String s_Date = (String) map.get("startDate");
		String e_Date = (String) map.get("endDate");
		String s_hour = (String) map.get("startHour");
		String s_minutes = (String) map.get("startMinute");
		String s_second = (String) map.get("startSecond");
		String e_hour = (String) map.get("endHour");
		String e_minutes = (String) map.get("endMinute");
		String e_second = (String) map.get("endSecond");
		Date startDate = org.apache.commons.lang3.time.DateUtils.parseDate(s_Date, "yyyy-MM-dd");
		Date endDate = org.apache.commons.lang3.time.DateUtils.parseDate(e_Date, "yyyy-MM-dd");
		Integer startHour = Integer.parseInt(s_hour);
		Integer startMinutes = Integer.parseInt(s_minutes);
		Integer startSecond = Integer.parseInt(s_second);
		Integer endHour = Integer.parseInt(e_hour);
		Integer endMinutes = Integer.parseInt(e_minutes);
		Integer endSecond = Integer.parseInt(e_second);

		Date defaultStartDate = dto.getValidStartTime();
		Date defaultEndDate = dto.getValidEndTime();

		// 第一种（比如startDate = 2016-10-01 endDate = 2016-0-10-31 defaultStartDate
		// =
		// 2016-09-01 defaultEndDate == 2016-12-31)
		if ((DateUtils.judgeDateSize(startDate, defaultStartDate) == -1 || DateUtils
				.judgeDateSize(startDate, defaultStartDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, defaultEndDate) == 1 || DateUtils
						.judgeDateSize(endDate, defaultEndDate) == 0)) {

		} else {

		}

	}

	public void getAdChannelInDate(Date startDate, Date endDate,
			Map<String, Object> map, Map<String, AdChannel> channelList,
			boolean flag) {
		String s_Date = (String) map.get("startDate");
		String e_Date = (String) map.get("endDate");
		String s_hour = (String) map.get("startHour");
		String s_minutes = (String) map.get("startMinute");
		String s_second = (String) map.get("startSecond");
		String e_hour = (String) map.get("endHour");
		String e_minutes = (String) map.get("endMinute");
		String e_second = (String) map.get("endSecond");

		String currentStartTime = s_hour + ":" + s_minutes + ":" + s_second;

		String currentEndTime = e_hour + ":" + e_minutes + ":" + e_second;
		List<AdCombo> comboList = Lists.newArrayList();
		if (null != comboList && comboList.size() > 0) {
			for (int i = 0; i < comboList.size(); i++) {
				AdCombo combo = comboList.get(i);
				Integer d_startHour = combo.getStartHour();
				Integer d_startMinutes = combo.getStartMinutes();
				Integer d_startSecond = combo.getEndMinutes();
				Integer d_endHour = combo.getEndHour();
				Integer d_endMinutes = combo.getEndMinutes();
				Integer d_endSecond = combo.getEndSecond();
				String startTime = d_startHour + ":" + d_startMinutes + ":"
						+ d_startSecond;

				String endTime = d_endHour + ":" + d_endMinutes + ":"
						+ d_endSecond;

				if (flag) {
					boolean b = AdComboUtils.isIntime(startTime, endTime,
							currentStartTime, currentEndTime);
					if (!b) {
						List<AdChannel> list = combo.getChannelList();
						for (int j = 0; j < list.size(); j++) {
							if (channelList.containsKey(list.get(i).getId())) {
								channelList.remove(list.get(i).getId());
							}
						}
					}
				}

			}
		}

	}

	/**
	 * 通过套餐ID查询套餐的广告类型以及该广告类型是否有子类型ID
	 * 
	 * @param comboId
	 * @return
	 * @throws ServiceException
	 */
	public TypeDTO findAdTypeByComboId(String comboId) throws ServiceException {
		AdCombo combo = new AdCombo();
		combo.setId(comboId);
		List<AdCombo> list = mybatisDao.getAdTypeByComboId(combo);
		TypeDTO dto = null;
		if (null != list && list.size() > 0) {
			AdCombo c = list.get(0);
			dto = new TypeDTO();
			dto.setIsPosition(String.valueOf(c.getAdType().getIsPosition()));
			dto.setAdType(String.valueOf(c.getAdType().getStatus()));
			dto.setComboId(c.getId());
			dto.setComboFlag(c.getIsFlag());
			dto.setTypeId(c.getAdType().getId());
			dto.setTypeName(c.getAdType().getTypeName());
			List<AdType> child = c.getAdType().getChildList();
			if (null != child && child.size() > 0) {
				List<ChildAdTypeDTO> childList = Lists.newArrayList();
				for (int i = 0; i < child.size(); i++) {
					ChildAdTypeDTO ca = new ChildAdTypeDTO();
					ca.setTypeName(child.get(i).getTypeName());
					ca.setId(child.get(i).getId());
					childList.add(ca);
				}
				dto.setChildList(childList);
			}

		}
		return dto;
	}

	/**
	 * 
	 * @param id
	 *            广告逻辑ID
	 * @param date
	 *            当前时间
	 * @param status
	 *            套餐状态
	 * @param advId
	 *            广告商ID
	 * @return
	 */
	public List<AdComboByUserDTO> getComboByAdvId(String id,
			int adv_status, String date, int status, String advId) {
		List<AdComboByUserDTO> dtoAllList = Lists.newArrayList();
		Adelement a = null;
		AdCombo combo = null;
		String comboId = null;
		if (StringUtils.isNotBlank(id)) {
			a = adelementService.get(id);
			combo = a.getAdCombo();
			comboId = combo.getId();
		}
		int count = 0;
		if (adv_status == Adelement.ADV_STATUS_FAIL
				|| adv_status == Adelement.ADV_STATUS_WAIT) {
			List<AdComboByUserDTO> dtoList = mybatisDao.getComboByAdvId(date,
					status, advId);
			if (dtoList != null && dtoList.size() > 0) {
				for (int i = 0; i < dtoList.size(); i++) {
					if(dtoList.get(i).getId().equals(comboId)){
						count++;
					}
					dtoAllList.add(dtoList.get(i));
				}
			}
		}  
		if(count==0){
			// 当该条修改当前广告的时候，当前广告对应的套餐已经是过期的套餐，仍然应该出现改套餐的名称
			if (StringUtils.isNotBlank(comboId)) {
				Advertiser aa = a.getAdvertiser();
				if (aa.getId().equals(advId)) {
					AdComboByUserDTO dto = new AdComboByUserDTO();
					dto.setId(combo.getId());
					dto.setComboName(combo.getComboName());
					dtoAllList.add(dto);
				}
			}
		}

		return dtoAllList;
	}

	@Transactional(readOnly = false)
	public void updateIsPut(List<PushAdComboDTO> list) throws Exception {
		if (null != list && list.size() > 0) {
			List<String> id_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				id_list.add(list.get(i).getId());
			}
			mybatisDao.updateIsPut(id_list);
		}
	}

	/**
	 * 判断套餐是否可以更新 条件：当前套餐销售的时间结束时间大于当前系统时间，不可以删除
	 * 
	 * @param combo
	 * @throws Exception
	 */
	public Boolean isCanUpdate(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboId", id);
		map.put("currentDate", new Date());
		map.put("status", AdCombo.ADCOMOBO_ALREADY_STATUS);
		List<String> list = sellDao.findComboInSell(map);
		if (null == list || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断套餐是否可以删除 条件：当前套餐的状态为未生效和可运营的状态时候，如果曾经销售过，也不可以删除
	 * 
	 * @param combo
	 * @throws Exception
	 */
	public Boolean isCanDelete(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("id", id);
		String list = sellDao.findSellByComboId(map);
		if (StringUtils.isBlank(list)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取套餐的时间以及套餐是否跟频道相关
	 * 
	 * @param id
	 * @return
	 */
	public AdSellDTO getAdSellDTO(String id) {
		AdCombo t = new AdCombo();
		t.setId(id);
		AdCombo combo = null;

		combo = mybatisDao.find(t);

		AdSellDTO dto = new AdSellDTO();
		if (null != combo) {
			dto.setComboId(combo.getId());
			if (AdCombo.ADCOMOBO_CHANNEL_ISFLAG.equals(combo.getIsFlag())) {
				dto.setIsChannel(true);
				dto.setEndDate(combo.getValidEndTime());
				dto.setStartDate(combo.getValidStartTime());
			} else {
				dto.setIsChannel(false);
				/*Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				c.set(Calendar.YEAR, year);
				dto.setEndDate(null);
				dto.setStartDate(new Date());*/
				dto.setEndDate(combo.getValidEndTime());
				dto.setStartDate(combo.getValidStartTime());
			}

		}
		return dto;
	}

	/**
	 * 获取套餐的详细销售信息
	 * 
	 * @return
	 */
	public String getComboSellDetail(String id, String comboName,
			String startDate, String endDate, String sellDay) throws Exception {

		Date queryStartDate = DateUtils.getDateFromString(startDate);
		Date queryEndDate = DateUtils.getDateFromString(endDate);
		ComboSellDetailDTO dto = null;
		AdCombo entity = new AdCombo();
		entity.setId(id);
		AdCombo combo = mybatisDao.find(entity);
		entity.setValidStartTime(queryStartDate);
		entity.setValidEndTime(queryEndDate);
		List<ComboAdvtiserSellDTO> list = mybatisDao
				.getAdcomboSellAdvtiserDay(entity);
		if (null != list && list.size() > 0) {
			dto = new ComboSellDetailDTO();
			//int total = DateUtils.dateBetweenDay(queryStartDate, queryEndDate);// 用户界面输入的总时间
			int total = getAdComboDays(queryStartDate,queryEndDate,combo.getValidStartTime(),combo.getValidEndTime());
			dto.setComboId(id);
			dto.setComboName(comboName);
			dto.setTotalDay(total);
			dto.setSellDay(Integer.valueOf(sellDay));
			dto.setMerchantList(list);
		}
		return JsonMapper.toJsonString(dto);
	}

	/**
	 * 获取频道相关的套餐的销售情况
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<AdCombo> finsStatistic(Page<AdCombo> page, AdCombo entity) {
		try {
			entity.setPage(page);
			Date startDate = entity.getValidStartTime();
			Date endDate = entity.getValidEndTime();
			List<AdCombo> result_list = Lists.newArrayList();
			List<AdComboStatisticSellDTO> sell_list = getAdComboSellDTO(entity);// 查询上面的套餐在查询的时间段内的销售天数
			if (null != sell_list && sell_list.size() > 0) {
				Map<String, AdCombo> map = new HashMap<String, AdCombo>();
				List<String> ids = Lists.newArrayList();
				for (AdComboStatisticSellDTO dto : sell_list) {
					ids.add(dto.getComboId());
				}
				result_list = mybatisDao.getAdComboByAType(ids);
				for (int i = 0; i < result_list.size(); i++) {
					AdType type = result_list.get(i).getAdType();
					if (type != null) {
						String id = type.getId();
						AdType adType = AdTypeUtils.get(id);
						result_list.get(i).setAdType(adType);
					}
					map.put(result_list.get(i).getId(), result_list.get(i));
				}

				//int total = DateUtils.dateBetweenDay(startDate, endDate);// 用户界面输入的总时间
				//获取查询时间段内可运营总时间

				for (AdComboStatisticSellDTO dto : sell_list) {
					String comboId = dto.getComboId();
					if (map.containsKey(comboId)) {
						AdCombo combo = map.get(comboId);
						int total = getAdComboDays(startDate,endDate,combo.getValidStartTime(),combo.getValidEndTime());
						combo.setValidDay(total);
						combo.setQueryStartDate(startDate);
						combo.setQueryEndDate(endDate);
						if (total == 0) {
							combo.setTimeScale("0");
							combo.setSellDay(0);

						} else {
							int sellDay = dto.getSellDay();
							combo.setSellDay(sellDay);
							BigDecimal percent = CalculateUtil.getPercent(
									sellDay, total);
							String p = CalculateUtil.decimalFormat(percent);// 保证输出的数据小数点后只有两位
							combo.setTimeScale(p + "%");
						}
					}
				}
			}

			page.setList(result_list);
			return page;
		} catch (Exception e) {

			return new Page<AdCombo>();
		}

	}

	/**
	 * 用于前台显示列表的json字符串
	 * 
	 * @return
	 */
	public String getSellJsonStr(List<AdCombo> list, Date startDate,
			Date endDate) {

		if (null != list && list.size() > 0) {
			List<AdcomboSellDTO> result = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				AdCombo c = list.get(i);
				AdcomboSellDTO dto = new AdcomboSellDTO();
				dto.setComboName(c.getComboName());
				dto.setId(c.getId());
				dto.setSellDay(c.getSellDay());
				dto.setTotalDay(c.getValidDay());
				dto.setStartDate(startDate);
				dto.setEndDate(endDate);
				dto.setTimeScale(c.getTimeScale());
				if (null != c.getAdType()) {
					dto.setTypeName(c.getAdType().getTypeName());
				}
				result.add(dto);
			}
			return JsonMapper.toJsonString(result);
		}
		return "";

	}

	/**
	 * 查询套餐的销售天数，如果是与频道无关的需要添加查询时间，因为与频道 无关的广告没有生效时间，只能查询在用户输入的时间范围以内卖了多少天
	 * 
	 * @param entity
	 *            广告类型，用来判定是否跟频道相关，如果不相关，需要添加查询时间
	 * @return
	 */
	public List<AdComboStatisticSellDTO> getAdComboSellDTO(AdCombo entity) {
		List<AdComboStatisticSellDTO> sell_list = null;
		try {
			sell_list = mybatisDao.getAdComboStatisticSellDTO(entity);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return sell_list;
	}

	/**
	 * 根据查询的结果查询与频道无关的广告的每个套餐的销售天数
	 * 
	 * @param list
	 *            查询数据
	 * @param id_list
	 *            套餐ID
	 * @param startDate
	 *            用户页面输入的开始时间
	 * @param endDate
	 *            用户页面输入的结束时间
	 * @return
	 */
	public List<AdComboStatisticSellDTO> getNotChannelSellDay(
			List<AdComboNotChannelSellDTO> list, List<String> id_list,
			Date startDate, Date endDate) {
		List<AdComboStatisticSellDTO> result_list = Lists.newArrayList();
		if (null == list || list.size() == 0) {
			for (int i = 0; i < id_list.size(); i++) {
				String id = id_list.get(i);
				AdComboStatisticSellDTO dto = new AdComboStatisticSellDTO();
				dto.setComboId(id);
				dto.setSellDay(0);
				result_list.add(dto);
			}
			return result_list;
		}
		Map<String, AdComboStatisticSellDTO> map = new HashMap<String, AdComboStatisticSellDTO>();
		for (int i = 0; i < id_list.size(); i++) {
			AdComboStatisticSellDTO dto = new AdComboStatisticSellDTO();
			String id = id_list.get(i);
			dto.setComboId(id);
			dto.setSellDay(0);
			map.put(id, dto);
		}
		for (int i = 0; i < list.size(); i++) {
			AdComboNotChannelSellDTO sell = list.get(i);
			String comboId = sell.getComboId();
			Date queryStartDate = sell.getStartDate();
			Date queryEndDate = sell.getEndDate();
			int count = getNotChannelSellDayByDate(startDate, endDate,
					queryStartDate, queryEndDate);
			if (map.containsKey(comboId)) {
				AdComboStatisticSellDTO dto = map.get(comboId);
				int sellDay = dto.getSellDay();
				sellDay += count;
				dto.setSellDay(sellDay);
			}
		}
		for (String key : map.keySet()) {
			result_list.add(map.get(key));
		}

		return result_list;
	}

	/**
	 * 跟频道无关的广告查询用户的输入的时间范围内一共卖了多少天
	 * 
	 * @param startDate
	 *            用户输入的开始时间
	 * @param endDate
	 *            用户输入的结束时间
	 * @param queryStartDate
	 *            查询出来的开始时间
	 * @param queryEndDate
	 *            查询出来的结束时间
	 * @return
	 */
	public Integer getNotChannelSellDayByDate(Date startDate, Date endDate,
			Date queryStartDate, Date queryEndDate) {

		// 分为四种情况
		// 第一种（比如startDate = 2016-10-01 endDate = 2016-0-10-31 queryStartDate =
		// 2016-09-01 queryEndDate == 2016-12-31)
		if ((DateUtils.judgeDateSize(startDate, queryStartDate) == -1 || DateUtils
				.judgeDateSize(startDate, queryStartDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, queryEndDate) == 1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)) {
			return DateUtils.dateBetweenDay(startDate, endDate);
			// 第二种情况（比如startDate = 2016-09-01 endDate = 2016-11-07
			// queryStartDate = 2016-10-01 queryEndDate == 2016-10-31）
		} else if ((DateUtils.judgeDateSize(startDate, queryStartDate) == 1 || DateUtils
				.judgeDateSize(startDate, queryStartDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, queryEndDate) == -1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)) {

			return DateUtils.dateBetweenDay(queryStartDate, queryEndDate);
			// 第三种情况（比如startDate = 2016-09-01 endDate = 2016-10-25 queryDate =
			// 2016-10-01 queryEndDate == 2016-12-08）
		} else if ((DateUtils.judgeDateSize(startDate, queryStartDate) == 1 || DateUtils
				.judgeDateSize(startDate, queryStartDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, queryEndDate) == 1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, queryStartDate) == -1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)) {
			return DateUtils.dateBetweenDay(queryStartDate, endDate);
			// 第四种情况（比如startDate = 2016-10-01 endDate = 2016-12-25 queryDate =
			// 2016-09-01 queryEndDate == 2016-10-08）
		} else if ((DateUtils.judgeDateSize(startDate, queryStartDate) == -1 || DateUtils
				.judgeDateSize(startDate, queryStartDate) == 0)
				&& (DateUtils.judgeDateSize(endDate, queryEndDate) == -1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)
				&& (DateUtils.judgeDateSize(queryEndDate, startDate) == -1 || DateUtils
						.judgeDateSize(endDate, queryEndDate) == 0)) {
			return DateUtils.dateBetweenDay(startDate, queryEndDate);
		}
		return 0;
	}

	/**
	 * 根据广告类型和发送器ID获取有效的广告套餐
	 * 
	 * @param typeId
	 * @param netWorkIdList
	 * @return
	 */
	public List<AdcomboCanSellDTO> getCanSellAdcombo(
			AdComboCanSellQueryDTO queryDto) throws Exception {
		if (null == queryDto) {
			return null;
		}
		String typeId = queryDto.getTypeId();
/*		List<String> netWorkIdList = queryDto.getNetworkIdList();
*/		if (StringUtils.isBlank(typeId) /*|| netWorkIdList == null
				|| netWorkIdList.size() == 0*/) {
			return null;
		}
		List<AdcomboCanSellDTO> list = Lists.newArrayList();
		List<AdCombo> canSellList = Lists.newArrayList();
		AdType adType = AdTypeUtils.get(typeId);
		int isFlag = adType.getIsFlag();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
/*		map.put("networkList", netWorkIdList);
*/		map.put("status", AdCombo.ADCOMOBO_ALREADY_STATUS);
		map.put("isValid", AdCombo.ADCOMOBO_YES_VALID);

		if (AdType.TYPE_CHANNEL == isFlag) {
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			map.put("nowDate", new Date());

			canSellList = mybatisDao.selectChannelAdComboByType(map);
		} else {
			map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
			canSellList = mybatisDao.selectNotChannelAdComboByType(map);
		}
		if (canSellList != null && canSellList.size() > 0) {
			for (int i = 0; i < canSellList.size(); i++) {
				AdCombo combo = canSellList.get(i);
				AdcomboCanSellDTO dto = new AdcomboCanSellDTO();
				dto.setId(combo.getId());
				dto.setComboName(combo.getComboName());
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * 根据套餐获取套餐所属发送器以及该套餐对应的广告类型ID
	 * 
	 * @param combo
	 * @return
	 */
	public AdComboCanSellQueryDTO getTypeAndNetworkByCombo(AdCombo combo) {
		AdComboCanSellQueryDTO dto = null;
		if (combo != null && combo.getIsFlag() != null) {
			dto = new AdComboCanSellQueryDTO();
			Integer isFlag = combo.getIsFlag();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("id", combo.getId());
			List<AdNetwork> list = Lists.newArrayList();
			if (AdCombo.ADCOMOBO_NETWORK_ISFLAG == isFlag) {
				list = mybatisDao.selectNotChannelNetworkByCombo(map);
			} else {
				list = mybatisDao.selectChannelNetworkByCombo(map);
			}
			List<String> str_list = null;
			String netWorkStr = "";
			if (null != list && list.size() > 0) {
				str_list = Lists.newArrayList();
				for (int i = 0; i < list.size(); i++) {
					String id = list.get(i).getId();
					str_list.add(id);
					netWorkStr =  netWorkStr + "," + list.get(i).getNetworkName();
				}
				dto.setNetWork(netWorkStr.substring(1,netWorkStr.length()));
			}
			
			dto.setNetworkIdList(str_list);
			if (null != combo.getAdType()) {
				String typeId = combo.getAdType().getId();
				dto.setTypeId(typeId);
			}
		}

		return dto;
	}

	public List<AdNetwork> findNetWorkByCombo(AdCombo adCombo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("id", adCombo.getId());
		List<AdNetwork> list = Lists.newArrayList();
		if (AdCombo.ADCOMOBO_CHANNEL_ISFLAG.intValue() == adCombo.getIsFlag().intValue()) {
			list = mybatisDao.selectChannelNetworkByCombo(map);
		} else {
			list = mybatisDao.selectNotChannelNetworkByCombo(map);
		}
		return list;
	}
	
	/**
	 * <!-- 当广告发送器失效的时候，必须让对应的套餐失效 -->
	 * 
	 * @param map
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public void updateAdcombovalidBynetworkId(String networkId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("isValid", AdCombo.ADCOMOBO_NO_VALID);
		map.put("yesIsValid", AdCombo.ADCOMOBO_YES_VALID);
		map.put("networkId", networkId);
		mybatisDao.updateAdNotChannlecombovalid(map);
		mybatisDao.updateAdChannlecombovalid(map);

	}
	
	/**
	 *  根据与频道无关的广告类型ID与发送器ID查询是否有套餐
	 * @param networkId
	 * @param typeId
	 * @return
	 */
	public Integer getComboCountByTypeIdAndNetwork(String networkId,String typeId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("networkId", networkId);
		map.put("id", typeId);
		return mybatisDao.getComboCountByTypeIdAndNetwork(map);
	}
	
	/**
	 *  根据频道ID和广告发送器ID查询当前频道下是否有套餐
	 * @param networkId
	 * @param channeId
	 * @return
	 */
	public Integer getComboCountChannelIdAndNetwork(String networkId,String channeId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("networkId", networkId);
		map.put("channeId", channeId);
		return mybatisDao.getComboCountChannelIdAndNetwork(map);
	}
	
	/**
	 * 判断套餐是否冲突
	 * @param typeId
	 * @param networkList
	 * @return
	 */
	public ComboCountDTO  checkComboIsConflict(String typeId,String networkList,String startDate,String endDate,String sendMode,String districts){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("sendMode", sendMode);
		map.put("districts", districts);
		List<String> network_List = Lists.newArrayList();
		String[] ids = networkList.split(",");
		for (String string : ids) {
			network_List.add(string);
		}
		map.put("netWorkList", network_List);
		map.put("size", ids.length);
		ComboCountDTO model = null;
		List<ComboCountDTO> dto = null;
		if(ids.length==1){
			dto = mybatisDao.checkComboIsExist(map);			
			if(dto != null && dto.size()>0){//有结果表示有该发送器的套餐  可以添加销售记录 
				model = dto.get(0);
				model.setCount(1);
			}else{//没有结果表示没有该发送器的套餐  可以添加
				model = new ComboCountDTO();
				model.setCount(-1);
			}
		}else{ //先判断所选发送器是否都没有相关套餐
			dto = mybatisDao.checkComboIsExist(map);
			if(dto==null || dto.size()==0){//没有结果表示没有该发送器的套餐  可以添加
				model = new ComboCountDTO();
				model.setCount(-1);	
			}else{
				dto = mybatisDao.checkComboIsConflict(map);			
				if(dto!=null && dto.size()>0){	//有结果表示有该发送器的套餐  可以添加销售记录 
					model = dto.get(0);
				}else{//没有结果表示套餐冲突
					model = new ComboCountDTO();
					model.setCount(0);
				}	
			}
		}
		return model;
	}

  public boolean checkIfAddCombo(AdCombo entity){
	    boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", entity.getAdType().getId());
		map.put("startDate", entity.getValidStartTime());
		map.put("endDate", entity.getValidEndTime());
		map.put("sendMode", entity.getSendMode());
		if(StringUtils.isNotBlank(entity.getId())){
			map.put("comboId", entity.getId());
		}
		if (entity.getIsFlag().equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)) {
             List<AdNetwork> list = entity.getNetworkList();
             List<String> network_List = Lists.newArrayList();
             for (AdNetwork adNetwork : list) {
            	 network_List.add(adNetwork.getId());
			}
             map.put("netWorkList", network_List);
             if(entity.getSendMode().equals("1")){
				 Integer count =  mybatisDao.getAdNetworkCombo(map);
				 if(count != null && count >0){
					 flag = false;
				 }
			 }
		} else {
			map.put("startTime", entity.getStartHour()*3600+entity.getStartMinutes()*60+entity.getStartSecond());
			map.put("endTime", entity.getEndHour()*3600+entity.getEndMinutes()*60+entity.getEndSecond());
			 List<AdChannel> list = entity.getChannelList();
             List<String> channel_List = Lists.newArrayList();
             for (AdChannel adChannel : list) {
            	 channel_List.add(adChannel.getId());
			}  
             map.put("channelList", channel_List);
             Integer count =  mybatisDao.getAdChannelCombo(map);	
             if(count != null && count >0){
            	 flag = false;
             }
		}
	  
	return flag;
	  
  }

	/**
	 * 分页查询与传入时间有交集的记录
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<AdCombo> findPageListByTimeIntersection(Page<AdCombo> page, AdCombo entity) {

		entity.setPage(page);
		List<AdCombo> list = mybatisDao.findByTimeIntersection(entity);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdType();
				AdTypeUtils.getLocaleAdType(adtype);
			}
		}
		page.setList(list);
		return page;
	}

	public List<AdCombo> findListByTimeIntersection(Date startDate,Date endDate){
		AdCombo adCombo = new AdCombo();
		adCombo.setValidStartTime(startDate);
		adCombo.setValidEndTime(endDate);
		return mybatisDao.findByTimeIntersection(adCombo);
	}

	/**
	 * 计算实际天数
	 * @param queryStartDate
	 * @param queryEndDate
	 * @param dbStartDate
	 * @param dbEndDate
	 * @return
	 */
	private Integer getAdComboDays(Date queryStartDate,Date queryEndDate,Date dbStartDate,Date dbEndDate){
		Integer daysDiff = 0;
		//查询开始时间或者结束时间为空 以套餐开始结束时间计算
		if(queryStartDate==null||queryEndDate==null){
			return DateUtils.daysBetween(dbStartDate,dbEndDate);
		}
		//查询开始时间小于或等于套餐开始时间 查询结束时间大于或等于套餐结束时间  以套餐开始结束时间计算
		if(queryStartDate.getTime()<=dbStartDate.getTime()&&queryEndDate.getTime()>=dbEndDate.getTime()){
			return DateUtils.daysBetween(dbStartDate,dbEndDate);
		}
		//查询开始时间大于或等于套餐开始时间 查询结束时间小于或等于套餐结束时间  以查询开始结束时间计算
		if(queryStartDate.getTime()>=dbStartDate.getTime()&&queryEndDate.getTime()<=dbEndDate.getTime()){
			return DateUtils.daysBetween(queryStartDate,queryEndDate);
		}
		//查询开始时间小于等于套餐开始时间 查询结束时间大于等于套餐开始时间且小于等于套餐结束时间  开始时间以套餐开始时间计算，结束时间以查询结束时间计算
		if(queryStartDate.getTime()<=dbStartDate.getTime()&&queryEndDate.getTime()>=dbStartDate.getTime()&&queryEndDate.getTime()<=dbEndDate.getTime()){
			return DateUtils.daysBetween(dbStartDate,queryEndDate);
		}
		//查询开始时间大于等于套餐开始时间且小于等于套餐结束时间 查询结束时间大于等于套餐结束时间  开始时间以查询开始时间计算，结束时间以套餐结束时间计算
		if(queryStartDate.getTime()>dbStartDate.getTime()&&queryEndDate.getTime()<dbEndDate.getTime()){
			return DateUtils.daysBetween(queryStartDate,dbEndDate);
		}
		return daysDiff;
	}
}
