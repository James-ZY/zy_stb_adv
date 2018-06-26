package com.gospell.aas.service.adv;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.gospell.aas.entity.adv.*;
import com.gospell.aas.repository.hibernate.adv.*;
import jersey.repackaged.com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.StringUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.adv.AdelementDTOUtil;
import com.gospell.aas.dto.adv.AdChannelNCDTO;
import com.gospell.aas.dto.adv.AdelementDTO;
import com.gospell.aas.dto.adv.AdelementNCDTO;
import com.gospell.aas.dto.push.PushAdelementDTO;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IAdControlDao;
import com.gospell.aas.repository.mybatis.adv.IAdDefaultControlDao;
import com.gospell.aas.repository.mybatis.adv.IAdRangeDao;
import com.gospell.aas.repository.mybatis.adv.IAdelementDao;
import com.gospell.aas.repository.mybatis.adv.IAdvNetWorkDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.repository.mybatis.sys.ISysParamDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.webservice.netty.client.Client;

@Service
@Transactional(readOnly = true)
public class AdelementService extends BaseService {

	@Autowired
	private AdelementDao thisDao;
	@Autowired
	private IAdelementDao mybatisDao;

	@Autowired
	private INetworkDao networDao;

	@Autowired
	private AdNetworkDao adNetworDao;

	@Autowired
	private AdChannelDao adChannelDao;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	@Autowired
	private IAdComboDao comboDao;

	@Autowired
	private AdComboDao adComboDao;

	@Autowired
	private IAdvNetWorkDao middleTableDao;

	@Autowired
	private AdControllDao adControllDao;

	@Autowired
	private IAdControlDao controllDao;

	@Autowired
	private AdTypeDao adTypeDao;

	@Autowired
	private IAdRangeDao rangeDao;

	@Autowired
	private AdRangeDao rangeDao1;

	@Autowired
	private AdTrackDao trackDao;

	@Autowired
	private AdPositionDao positionDao;

	@Autowired
	private ISysParamDao sysParamDao;

	@Autowired
	private IAdDefaultControlDao defaultControllDao;

	private static final Logger logger = LoggerFactory
			.getLogger(AdelementService.class);

	public Adelement get(String id) {
		return thisDao.get(id);
	}

	public void flush() {
		thisDao.flush();
	}

	public void clear() {
		thisDao.clear();
	}

	// public List<Adelement> findAll() {
	// return thisDao.findAll();
	// }

	/**
	 * 根据条件查询广告
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<Adelement> find(Page<Adelement> page, Adelement entity) {

		if (UserUtils.getUser().getAdvertiser() != null) {
			entity.setAdvertiser(UserUtils.getUser().getAdvertiser());
		}

		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.start_date desc,a.end_date desc");
		}else{
			String order = page.getOrderBy();
			String[] orders = order.split(",")[0].split(" ");
			if(orders[0].contains("a.start_date")){
               order = "a.start_date "+orders[1]+",a.end_date "+orders[1] + ",c.start_hour "+orders[1]+",c.start_minutes "+orders[1]+",c.start_second "+orders[1];
			}
			if(orders[0].contains("a.end_date")){
				order = "a.end_date "+orders[1]+",a.start_date "+orders[1] + ",c.start_hour "+orders[1]+",c.start_minutes "+orders[1]+",c.start_second "+orders[1];
			}
			page.setOrderBy(order);
		}
		entity.setPage(page);
		List<Adelement> list = mybatisDao.findList(entity);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdCombo().getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

				AdType type = list.get(i).getChildAdType();
				if (type != null)
					AdTypeUtils.getLocaleAdType(type);
			}
		}
		page.setList(list);

		return page;

	}

	/**
	 * 根据条件查询广告
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<Adelement> findAudit1(Page<Adelement> page, Adelement entity) {

		User user = UserUtils.getUser();
		List<Integer> list = Lists.newArrayList();
		List<Office> childOffice = null;
		Office office = null;
		Integer status = entity.getStatus();
		if (user.isAdmin()) {
			childOffice = UserUtils.getOfficeList();
		} else {
			office = user.getOffice();
			childOffice = office.getChildList();
			if (childOffice.contains(office)) {
				childOffice.remove(office);
			}
		}

		if (null != childOffice && childOffice.size() > 0) {
			if (null != office) {
				childOffice.add(office);
			}

			List<String> officeList = Lists.newArrayList();
			for (int i = 0; i < childOffice.size(); i++) {
				officeList.add(childOffice.get(i).getId());
			}
			entity.setOfficeList(officeList);
			if (null != status) {
				list.add(entity.getStatus());
			} else {
				list.add(Adelement.ADV_STATUS_WAIT);
				list.add(Adelement.ADV_STATUS_CLAIM);
				list.add(Adelement.ADV_STATUS_PASS);
				list.add(Adelement.ADV_STATUS_FAIL);
				list.add(Adelement.ADV_STATUS_SHOW);
				list.add(Adelement.ADV_STATUS_END);
			}
		} else {
			entity.setAuditUser(user);

			if (null != status) {
				if (status == Adelement.ADV_STATUS_PASS) {
					list.add(Adelement.ADV_STATUS_PASS);
					list.add(Adelement.ADV_STATUS_SHOW);
					list.add(Adelement.ADV_STATUS_END);
				} else {
					list.add(entity.getStatus());
				}
			} else {

				list.add(Adelement.ADV_STATUS_CLAIM);
				list.add(Adelement.ADV_STATUS_PASS);
				list.add(Adelement.ADV_STATUS_FAIL);
				list.add(Adelement.ADV_STATUS_SHOW);
				list.add(Adelement.ADV_STATUS_END);
			}
		}

		entity.setStatusList(list);

		entity.setStatus(Adelement.ADV_STATUS_WAIT);
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.ad_status ASC");
		}
		entity.setPage(page);

		List<Adelement> pageList = mybatisDao.findAuditList(entity);
		for (int i = 0; i < pageList.size(); i++) {
			Adelement a = pageList.get(i);
			if (a.getStatus() == Adelement.ADV_STATUS_SHOW
					|| Adelement.ADV_STATUS_END == a.getStatus()) {
				a.setStatus(Adelement.ADV_STATUS_PASS);
			}
		}

		page.setList(pageList);
		entity.setStatus(status);// 因为后台需要待审核的传值，在查询完成后，需要把原来页面状态值写回
		return page;

	}

	/**
	 * 根据条件查询广告
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<Adelement> findAudit(Page<Adelement> page, Adelement entity) {
		User user = UserUtils.getUser();
		List<Integer> list = Lists.newArrayList();

		Integer status = entity.getStatus();

		if (user.isAdmin()) {
			if (null != status) {
				list.add(entity.getStatus());
			} else {
				list.add(Adelement.ADV_STATUS_WAIT);
				list.add(Adelement.ADV_STATUS_CLAIM);
			}
		} else {
			entity.setAuditUser(user);
			if (null == status) {
				list.add(Adelement.ADV_STATUS_CLAIM);
			} else {
				if (null != status && status == Adelement.ADV_STATUS_CLAIM) {
					list.add(entity.getStatus());
				}
			}
		}

		entity.setStatusList(list);

		// entity.setStatus(Adelement.ADV_STATUS_WAIT);
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.ad_status ASC,a.claim_date desc");
		}
		entity.setPage(page);
		List<Adelement> pageList = mybatisDao.findAuditList(entity);
		if (null != pageList && pageList.size() > 0) {
			for (int i = 0; i < pageList.size(); i++) {
				AdType adtype = pageList.get(i).getAdCombo().getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

				AdType type = pageList.get(i).getChildAdType();
				if (type != null)
					AdTypeUtils.getLocaleAdType(type);
			}
		}
		page.setList(pageList);
		entity.setStatus(status);// 因为后台需要待审核的传值，在查询完成后，需要把原来页面状态值写回
		return page;

	}

	/**
	 * 根据条件查询广告审核的数据
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<Adelement> findAuditQuery(Page<Adelement> page, Adelement entity) {

		List<Integer> list = Lists.newArrayList();
		Integer status = entity.getStatus();
		if (null == status) {
			list.add(Adelement.ADV_STATUS_PASS);
			list.add(Adelement.ADV_STATUS_FAIL);
			list.add(Adelement.ADV_STATUS_SHOW);
			list.add(Adelement.ADV_STATUS_END);
			entity.setStatusList(list);
		} else {
			if (status == Adelement.ADV_STATUS_PASS) {
				list.add(Adelement.ADV_STATUS_PASS);
				list.add(Adelement.ADV_STATUS_SHOW);
				list.add(Adelement.ADV_STATUS_END);

			} else {
				list.add(Adelement.ADV_STATUS_FAIL);
			}
			entity.setStatusList(list);
		}
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.ad_status ASC,a.audit_date desc");
		}
		entity.setPage(page);

		List<Adelement> pageList = mybatisDao.findAuditStatisticList(entity);
		if (null != pageList && pageList.size() > 0) {
			for (int i = 0; i < pageList.size(); i++) {
				AdType adtype = pageList.get(i).getAdCombo().getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

				AdType type = pageList.get(i).getChildAdType();
				if (type != null)
					AdTypeUtils.getLocaleAdType(type);
			}
		}

		page.setList(pageList);
		return page;

	}

	/**
	 * 根据查询发布广告
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<Adelement> findRelease(Page<Adelement> page, Adelement entity) {
		if ((entity.getStatus() == null)
				|| (entity.getStatus() != null && entity.getStatus() == Adelement.ADV_STATUS_WAIT)) {
			List<Integer> list = Lists.newArrayList();
			list.add(Adelement.ADV_STATUS_PASS);
			list.add(Adelement.ADV_STATUS_SHOW);
			list.add(Adelement.ADV_STATUS_END);
			entity.setStatus(null);
			entity.setStatusList(list);
		}
		if (UserUtils.getUser().getAdvertiser() != null) {
			entity.setAdvertiser(UserUtils.getUser().getAdvertiser());
		}
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.ad_status ASC,t.id desc");
		}
		entity.setPage(page);
		List<Adelement> list = mybatisDao.findList(entity);
		for (int i = 0; i < list.size(); i++) {
			AdType adtype = list.get(i).getAdCombo().getAdType();
			AdTypeUtils.getLocaleAdType(adtype);

			AdType type = list.get(i).getChildAdType();
			if (type != null)
				AdTypeUtils.getLocaleAdType(type);
		}
		page.setList(list);
		return page;

	}

	/**
	 * 保存广告类型
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public synchronized void save(Adelement entity) throws ServiceException {
		thisDao.clear();
		if (null == entity.getAdvertiser()
				|| org.apache.commons.lang3.StringUtils.isBlank(entity.getAdvertiser().getId())) {
			entity.setAdvertiser(UserUtils.getUser().getAdvertiser());
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(entity.getId())) {
			setAdId(entity);
		} else {
			String oldCategory = entity.getOldAdCategoryId();
			AdCategory catrgory = entity.getAdCategory();
			if (org.apache.commons.lang3.StringUtils.isNotBlank(entity.getAdId())&&!oldCategory.equals(catrgory.getId())) {
				setAdId(entity);
			}
		}
		AdPosition pos;
		AdPosition hdPos;
		AdType type = entity.getAdCombo().getAdType();
		if(null != entity.getPosition()){
			pos = entity.getPosition();
			if(org.apache.commons.lang3.StringUtils.isBlank(pos.getId())){
				String id = IdGen.uuid();
				pos.setId(id);
				entity.setPosition(pos);
			}
			pos.setPositionName(type.getTypeName());
			pos.setAdType(type);
			pos.setCreateBy(UserUtils.getUser());
			pos.setCreateDate(new Date());
			positionDao.save(pos);
		}
		if(null != entity.getHdPosition()){
			hdPos = entity.getHdPosition();
			if(org.apache.commons.lang3.StringUtils.isBlank(hdPos.getId())){
				String id = IdGen.uuid();
				hdPos.setId(id);
				entity.setHdPosition(hdPos);
			}
			hdPos.setPositionName(type.getTypeName());
			hdPos.setAdType(type);
			hdPos.setCreateBy(UserUtils.getUser());
			hdPos.setCreateDate(new Date());
			positionDao.save(hdPos);
		}
		thisDao.save(entity);

	}

	@Transactional(readOnly = false)
	public synchronized void setAdId(Adelement entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		String categoryId = entity.getAdCategory().getId();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("categoryId", categoryId);
		Integer count = mybatisDao.findAdelementByAdCategory(map);
		int total = count + 1;
		String s1 = Integer.toHexString(total);
		String number = StringUtils.tencentToHex(s1);
		String adId = StringUtils.tencentToHex(categoryId) + number;
		entity.setAdId(adId);

	}

	/**
	 * 保存广告类型
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void updateStatus(Adelement entity) throws Exception {
		thisDao.clear();
		entity.setUpdateBy(UserUtils.getUser());
		mybatisDao.update(entity);

	}

	/**
	 * 当审核通过的时候，推送该广告到客户端
	 *
	 * @param entity
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void pushAdvToClient(Adelement a) throws Exception {
		// thisDao.clear();
		// thisDao.flush();
		if(null == a.getStatus()){
			return;
		}
		Integer status = a.getStatus();
		if (status.equals(Adelement.ADV_STATUS_PASS)) {
			AdCombo combo = a.getAdCombo();
			if (null == combo) {

				logger.error("根据广告Id查询广告套餐的详细信息出错！");
				return;
			}

			push(a, combo);
			thisDao.clear();
			thisDao.flush();

		}

	}

	/**
	 * 推送广告的执行方法
	 *
	 * @param adelement
	 * @param combo
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void push(Adelement adelement, AdCombo combo) throws Exception {
		Adelement entity = new Adelement();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("id", adelement.getId());
		// 根据广告是否跟频道相关查询广告的具体信息，因为跟频道相关的广告有坐标参数，需要单独查询
		PushAdelementDTO dto = new PushAdelementDTO();
		Integer combo_isFlag = combo.getIsFlag();
		if (combo_isFlag.equals(AdCombo.ADCOMOBO_CHANNEL_ISFLAG)) {
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			entity = mybatisDao.getSingelAdelementChannel(map);

			setAdControll(entity);
			setAdelementDto(entity, dto,null,false);

			setPositionToAdelement(adelement, dto);
		} else {
			map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
			entity = mybatisDao.getSingelAdelementNotChannel(map);
			setAdControll(entity);
			setAdelementDto(entity, dto,null,false);

		}

		List<String> clientIds = getClientIdsByAdv(entity);
		// 在推送以前把数据插入到数据库中间表，防止在推送过程中出现客户端连接正常，但是没有收到数据，这样数据就不会出现不完整的情况
		Integer status = AdvNetwork.PUSH_CLIENT_ADV_FAIL;
		Integer isPlayNow = Adelement.ADV_CLOSE_NOW_NO;
		if (null != adelement.getIsPlayNow()) {
			isPlayNow = adelement.getIsPlayNow();

			if (isPlayNow.equals(Adelement.ADV_CLOSE_NOW_YES)) {
				status = AdvNetwork.PUSH_PLAY_NOW_CLIENT_ADV_FAIL;// 紧急插播
			}

		}

		insertSendFail(clientIds, entity, status);
		Client.getInstance().putAdv(clientIds, dto, isPlayNow);
		entity.setStatus(Adelement.ADV_STATUS_SHOW);

		mybatisDao.updateStatus(entity);

		logger.error("当推送到客户端成功的时候，更新广告状态成功!");

	}

	/**
	 * 推送广告的执行方法
	 *
	 * @param adelement
	 * @param combo
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void pushDefault(Adelement adelement, AdCombo combo) throws Exception {
		Adelement entity = new Adelement();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("id", adelement.getId());
		// 根据广告是否跟频道相关查询广告的具体信息，因为跟频道相关的广告有坐标参数，需要单独查询
		PushAdelementDTO dto = new PushAdelementDTO();
		Integer combo_isFlag = combo.getIsFlag();
		if (combo_isFlag.equals(AdCombo.ADCOMOBO_CHANNEL_ISFLAG)) {
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			entity = mybatisDao.getSingelAdelementChannel(map);

			setAdControll(entity);
			setAdelementDto(entity, dto,null,true);
			setPositionToAdelement(adelement, dto);
			List<String> clientIds = getClientIdsByAdv(entity);
			// 在推送以前把数据插入到数据库中间表，防止在推送过程中出现客户端连接正常，但是没有收到数据，这样数据就不会出现不完整的情况
			Integer status = AdvNetwork.PUSH_CLIENT_ADV_FAIL;
			Integer isPlayNow = Adelement.ADV_CLOSE_NOW_NO;
			if (null != adelement.getIsPlayNow()) {
				isPlayNow = adelement.getIsPlayNow();

				if (isPlayNow.equals(Adelement.ADV_CLOSE_NOW_YES)) {
					status = AdvNetwork.PUSH_PLAY_NOW_CLIENT_ADV_FAIL;// 紧急插播
				}

			}

			insertSendFail(clientIds, entity, status);
			Client.getInstance().putAdv(clientIds, dto, isPlayNow);
			entity.setStatus(Adelement.ADV_STATUS_SHOW);

			mybatisDao.updateStatus(entity);
		} else {
			map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
			entity = mybatisDao.getSingelAdelementNotChannel(map);
			setAdControll(entity);
			List<AdNetwork> nets = combo.getNetworkList();
			for (AdNetwork adNetwork : nets) {
				setAdelementDto(entity, dto,adNetwork.getId(),true);
				List<String> clientIds = getClientIdsByAdv(entity);
				clientIds.add(adNetwork.getNetworkId());
				// 在推送以前把数据插入到数据库中间表，防止在推送过程中出现客户端连接正常，但是没有收到数据，这样数据就不会出现不完整的情况
				insertSendFail(clientIds, entity, AdvNetwork.PUSH_PLAY_NOW_CLIENT_ADV_FAIL);
				Client.getInstance().putDefaultAdv(clientIds, dto, Adelement.ADV_CLOSE_NOW_YES);
				logger.error("推送默认开机画面广告成功!");
			}

		}
	}

	public void setAdControll(Adelement entity) {
		if (null == entity) {
			return;
		}
		List<AdControll> c_list = entity.getControllerList();// 这个地方为什么要重复查询，
		// 因为遇到查询出来的广告不知道为什么始终有问题，党广告审核通过的时候，对应的控制文件只有ID，没有其它字段的值，但是当第一次失败，第二次在页面点击直接投放的时候又正确，不知道为什么，以后必须解决
		if (null != c_list && c_list.size() > 0) {
			List<String> idList = Lists.newArrayList();
			for (int i = 0; i < c_list.size(); i++) {
				String id = c_list.get(i).getId();
				idList.add(id);
			}
			List<AdControll> queryList = controllDao.getControlByIds(idList);
			entity.setControllerList(queryList);
		}
	}

	/**
	 * 连接netty服务器异常的时候，需要把广告写入中间表
	 *
	 * @throws Exception
	 *
	 */
	@Transactional(readOnly = false)
	public void insertSendFail(List<String> clienIdList, Adelement entity,
							   Integer status) throws Exception {

		List<AdvNetwork> insert_list = Lists.newArrayList();
		for (int i = 0; i < clienIdList.size(); i++) {
			AdvNetwork insert = new AdvNetwork();
			insert.setId(IdGen.uuid());
			insert.setCreateDate(new Date());
			insert.setAdvId(entity.getAdId());
			insert.setClientId(clienIdList.get(i));

			insert.setStatus(status);
			insert_list.add(insert);
		}

		middleTableDao.insertEntity(insert_list);

		logger.info("连接netty失败，数据写入中间库成功");

	}

	/**
	 *
	 * @param adelement
	 * @param dto
	 */
	public void setAdelementDto(Adelement adelement, PushAdelementDTO dto,String networkId, boolean def) {
		dto.setAdcomboId(adelement.getAdCombo().getId());
		dto.setIsFlag(adelement.getIsFlag());
		dto.setAddText(adelement.getAddText());
		dto.setPlayTime(adelement.getPlayTime());
		if (null != adelement.getAdvertiser()) {
			dto.setAdvId(adelement.getAdvertiser().getAdvertiserId());
		}
		if (null != adelement.getAdCombo().getAdType()) {
			dto.setAdvType(adelement.getAdCombo().getAdType().getTypeId());
		}
		dto.setAdvName(adelement.getAdName());
		Date start = adelement.getStartDate();
		Date end = adelement.getEndDate();
		//转换成格林尼治时间

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (null != start) {
			dto.setStartDate(format.format(start));
		}
		if (null != end) {
			dto.setEndDate(format.format(end));
		}
		if(def){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adNetworkId", networkId);
			map.put("adTypeId", dto.getAdvType());
			map.put("flag", Adelement.ADV_RESOLUTION_SD);
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			List<AdDefaultControll> sdlist = defaultControllDao.getControlByTypeId(map);
			if(sdlist != null && sdlist.size()>0){
				dto.setFile_path(getDefaultFilePathByControll(sdlist)+"@5");
			}


			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("adNetworkId", networkId);
			map1.put("adTypeId", dto.getAdvType());
			map1.put("flag", Adelement.ADV_RESOLUTION_HD);
			map1.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			List<AdDefaultControll> hdlist = defaultControllDao.getControlByTypeId(map1);
			if(hdlist != null && hdlist.size()>0){
				dto.setHd_file_path(getDefaultFilePathByControll(hdlist)+"@5");
			}
		}else{
			List<AdControll> controllerList = adelement.getControllerList();
			List<AdControll> hd_controllerList = adelement.getHdControllerList();
			dto.setFile_path(getFilePathByControll(adelement.getSdShowParam(),controllerList));
			dto.setHd_file_path(getFilePathByControll(adelement.getHdShowParam(),hd_controllerList));
		}
	/*	dto.setSdShowParam(adelement.getSdShowParam());
		dto.setHdShowParam(adelement.getHdShowParam());*/
		if (adelement.getChildAdType() != null) {
			dto.setSonAdvType(adelement.getChildAdType().getTypeId());// 广告子类型，现在默认设置为0
		} else {
			dto.setSonAdvType("0");// 广告子类型，现在默认设置为0
		}
		dto.setId(adelement.getAdId());
		// dto.setDesType(String.valueOf(adelement.getShowWay()));

	}

	public  String getDefaultFilePathByControll(List<AdDefaultControll> controllerList){
		List<String> nameIdList = Lists.newArrayList();
		for (AdDefaultControll a : controllerList) {
			String filePath = a.getFilePath();
			String[] all = filePath.split("/");
			if (all != null && all.length > 0) {
				String[] path = new String[all.length - 2];
				for (int i = 2; i < all.length; i++) {
					path[i - 2] = all[i];
				}
				String paths = "/" + org.apache.commons.lang3.StringUtils.join(path, "/");
				nameIdList.add(paths);
			}
		}
		return org.apache.commons.lang3.StringUtils.join(nameIdList, ",");
	}
	/**
	 * 根据素材资源获取标清素材和高清素材
	 * @param list
	 * @return
	 */
	public  String getFilePathByControll(String param,List<AdControll> controllerList){
		if (null != controllerList && controllerList.size() > 0) {
			List<String> nameIdList = Lists.newArrayList();
			AdControll ac= null;
			if(org.apache.commons.lang3.StringUtils.isNotBlank(param)){
				String[]	rsParam	= param.split(",");
				for(int i=0;i<rsParam.length;i++){
					ac = adControllDao.get(rsParam[i].split("@")[0]);
					String filePath = ac.getFilePath();
					String[] all = filePath.split("/");
					if (all != null && all.length > 0) {
						String[] path = new String[all.length - 2];
						for (int j = 2; j < all.length; j++) {
							path[j - 2] = all[j];
						}
						String paths = "/" + org.apache.commons.lang3.StringUtils.join(path, "/")+"@"+rsParam[i].split("@")[1];
						nameIdList.add(paths);
					}
				}
			}else{
				for (AdControll a : controllerList) {
					String filePath = a.getFilePath();
					String[] all = filePath.split("/");
					if (all != null && all.length > 0) {
						String[] path = new String[all.length - 2];
						for (int i = 2; i < all.length; i++) {
							path[i - 2] = all[i];
						}
						String paths = "/" + org.apache.commons.lang3.StringUtils.join(path, "/");
						nameIdList.add(paths);
					}
				}
			}
			return org.apache.commons.lang3.StringUtils.join(nameIdList, ",");

		}
		return null;
	}

	public void setPositionToAdelement(Adelement adelement, PushAdelementDTO dto) {
		if (adelement.getPosition() != null) {
			AdPosition p = adelement.getPosition();
			dto.setSd_beginPointX(StringUtils.getIntegerStr(p.getBeginPointX()));
			dto.setSd_beginPointY(StringUtils.getIntegerStr(p.getBeginPointY()));
			dto.setSd_endPointX(StringUtils.getIntegerStr(p.getEndPointX()));
			dto.setSd_endPointY(StringUtils.getIntegerStr(p.getEndPointY()));
			if(adelement.getAdCombo().getAdType().getId().equals(AdType.TYPE_ROLL_ADV_ID)){
				if(null != adelement.getVelocity()){
					dto.setSd_velocity(StringUtils.getIntegerStr(adelement.getVelocity()));
				}else{
					dto.setSd_velocity("5");
				}
			}
/*			if (null != p.getVelocity()) {
				dto.setSd_velocity(StringUtils.getIntegerStr(p.getVelocity()));
			}*/
		}
		if (adelement.getHdPosition() != null) {
			AdPosition p = adelement.getHdPosition();
			dto.setHd_beginPointX(StringUtils.getIntegerStr(p.getBeginPointX()));
			dto.setHd_beginPointY(StringUtils.getIntegerStr(p.getBeginPointY()));
			dto.setHd_endPointX(StringUtils.getIntegerStr(p.getEndPointX()));
			dto.setHd_endPointY(StringUtils.getIntegerStr(p.getEndPointY()));
			/*if (null != p.getVelocity()) {
				dto.setHd_velocity(StringUtils.getIntegerStr(p.getVelocity()));
			}*/
			if(adelement.getAdCombo().getAdType().getId().equals(AdType.TYPE_ROLL_ADV_ID)){
				if(null != adelement.getVelocity()){
					dto.setHd_velocity(StringUtils.getIntegerStr(adelement.getVelocity()));
				}else{
					dto.setHd_velocity("5");
				}
			}
		}
	}

	/**
	 * DELETE广告类型
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(Adelement entity) throws Exception {
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		thisDao.save(entity);

	}

	public Adelement findAdelementByAdId(String adId) throws ServiceException {
		return thisDao.findAdElemetByAdId(adId);
	}

	/**
	 * 获取广告预览所需数据
	 *
	 * @param adelement
	 * @return
	 */
	@Transactional(readOnly = false)
	public AdelementDTO showAdelement(Adelement adelement, AdCombo adcombo) {
		if (null == adelement)
			return null;
		if (null != adcombo) {
			AdType type = adcombo.getAdType();
			if (null != type) {//先判断套餐的广告类型是否是插屏广告的轨迹模式  是轨迹模式按照轨迹模式单独处理
				if(type.getId().equals(AdType.TYPE_INSERT_SCREEN_ID) && adcombo.getTrackMode().equals(2)){
					if (type.getStatus() != null) {
						Integer type_status = type.getStatus();
						if (AdType.TYPE_STATUS_IMAGE.equals(type_status)) {
							AdTrack sdTrack =trackDao.get(adcombo.getSdTrack().getId());
							AdTrack hdTrack = trackDao.get(adcombo.getHdTrack().getId());
							return AdelementDTOUtil.getAdelementDTO(adelement,
									adcombo, null, null,null,null,sdTrack,hdTrack);
						}
					}
				}else{
					String positionId = "";
					String hdPositionId = "";
					if (null != adelement.getPosition()) {
						positionId = adelement.getPosition().getId();
					}
					if (null != adelement.getHdPosition()) {
						hdPositionId = adelement.getHdPosition().getId();
					}
					if (type.getStatus() != null) {
						Integer type_status = type.getStatus();
						if (AdType.TYPE_STATUS_IMAGE.equals(type_status)) {

							AdPosition sdPosition = null;//具体位置
							AdPosition hdPosition = null;
							sdPosition = positionDao.get(positionId);
							hdPosition = positionDao.get(hdPositionId);
						/*if (null != map && map.containsKey(positionId)) {
							sdPosition = map.get(positionId);

						}
						if (null != map && map.containsKey(hdPositionId)) {
							hdPosition = map.get(hdPositionId);
						}*/
							AdRange sdRange = adcombo.getSdRange();//图片范围
							if(sdRange!=null && sdRange.getBeginX()==null){
								sdRange = rangeDao1.get(sdRange.getId());
							}
							AdRange hdRange = adcombo.getHdRange();
							if(hdRange!=null && hdRange.getBeginX()==null){
								hdRange = rangeDao1.get(hdRange.getId());
							}
							if(adcombo.getAdType().getTypeId().equals("2") || adcombo.getAdType().getTypeId().equals("4") ||  adcombo.getAdType().getTypeId().equals("5") ||  adcombo.getAdType().getTypeId().equals("10")){
								if(sdRange ==null){
									Map<String,Object> sdmap = new HashMap<String,Object>();
									sdmap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
									sdmap.put("typeId", type.getTypeId());
									sdmap.put("flag", "0");
									sdmap.put("status", AdRange.RANGE_START_STAUTS);
									List<AdRange> list = rangeDao.getUseInAdRange(sdmap);
									if(list !=null && list.size()>0){
										sdRange = list.get(0);
									}
								}
								if(hdRange ==null){
									Map<String,Object> hdmap = new HashMap<String,Object>();
									hdmap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
									hdmap.put("typeId", type.getTypeId());
									hdmap.put("flag", "1");
									hdmap.put("status", AdRange.RANGE_START_STAUTS);
									List<AdRange> list = rangeDao.getUseInAdRange(hdmap);
									if(list !=null && list.size()>0){
										hdRange = list.get(0);
									}
								}
							}
							adcombo.setHdRange(hdRange);
							adcombo.setSdRange(sdRange);
							return AdelementDTOUtil.getAdelementDTO(adelement,
									adcombo, sdPosition, hdPosition,sdRange,hdRange,null,null);
						}
						else {
							return AdelementDTOUtil.getAdelementDTOForVedio(adelement,
									adcombo);
						}
					}
				}

			}
			return null;

		}
		return null;
	}

	@Transactional(readOnly = false)
	public void updateIsPut(List<PushAdelementDTO> list) throws Exception {
		if (null != list && list.size() > 0) {
			List<String> id_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				id_list.add(list.get(i).getId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", id_list);
			map.put("isPlay", Adelement.ADV_STATUS_PLAY);
			map.put("status", Adelement.ADV_STATUS_SHOW);
			mybatisDao.updateIsPut(map);
		}
	}

	@Transactional(readOnly = false)
	public void updatePutEnd(Map<String, Object> map) throws Exception {
		if (null != map) {
			mybatisDao.updatePutEnd(map);
		}
	}

	/**
	 * 只允许删除待审核、审核不通过、审核通过合约终止的广告
	 *
	 * @param adelement
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer isDelete(Adelement adelement) throws Exception {
		int status = adelement.getStatus();
		if (status == Adelement.ADV_STATUS_WAIT
				|| status == Adelement.ADV_STATUS_PASS
				|| status == Adelement.ADV_STATUS_FAIL
				|| status == Adelement.ADV_STATUS_STOP) {
			delete(adelement);
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 停播当前正在播放的广告，停播的时候把广告状态变为投放结束，更新广告结束时间为当前系统时间， 并且及时通知广告发送器停止播放该广告,停播前推送默认广告过去
	 *
	 * @param id
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void closeDown(final String id, final Integer isDeleteNow)
			throws Exception {
		final Adelement a = get(id);
		if(a.getAdCombo().getAdType().getId().equals(AdType.Type_OPEN_IMGAE) || a.getAdCombo().getAdType().getId().equals(AdType.Type_BROCAST)){
			taskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						AdCombo combo = a.getAdCombo();
						if (null == combo) {
							logger.error("根据广告Id查询广告套餐的详细信息出错！");
							return;
						}
						pushDefault(a, combo);
					} catch (Exception e) {
						logger.error("推送默认广告出错");
						e.printStackTrace();
					}

				}
			});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("endStatus", Adelement.ADV_STATUS_END);
		map.put("originalEndDate", a.getEndDate());
/*		map.put("endDate", new Date());
*/		map.put("update", new Date());
		map.put("userId", UserUtils.getUser().getId());
		mybatisDao.closeDown(map);
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					deleteClient(a, isDeleteNow);
				} catch (Exception e) {
					logger.error("删除客户端的广告出错");
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 紧急停播当前正在播放的广告，停播的时候把广告状态变为投放结束，更新广告结束时间为当前系统时间， 并且及时通知广告发送器停止播放该广告
	 *
	 * @param id
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void closeDownNow(final String id, final Integer isDeleteNow)
			throws Exception {

		final Adelement a = get(id);
		if(a.getAdCombo().getAdType().getId().equals(AdType.Type_OPEN_IMGAE)){
			taskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						AdCombo combo = a.getAdCombo();
						if (null == combo) {
							logger.error("根据广告Id查询广告套餐的详细信息出错！");
							return;
						}
						pushDefault(a, combo);
					} catch (Exception e) {
						logger.error("推送默认广告出错");
						e.printStackTrace();
					}

				}
			});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("endStatus", Adelement.ADV_STATUS_END);
		map.put("originalEndDate", a.getEndDate());
/*		map.put("endDate", new Date());
*/		map.put("update", new Date());
		map.put("userId", UserUtils.getUser().getId());
		mybatisDao.closeDown(map);
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					deleteClient(a, isDeleteNow);
				} catch (Exception e) {
					logger.error("删除客户端的广告出错");
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 通知客户端删除播控停播的广告
	 *
	 * @param a
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void deleteClient(Adelement a, Integer isDeleteNow) throws Exception {

		/**
		 * 先找到该广告对应的所有广告发送器ID
		 */
		AdCombo combo = a.getAdCombo();
		List<String> clientIds = Lists.newArrayList();

		List<AdNetwork> list = null;
		String comboId = combo.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboId", comboId);
		Integer combo_isFlag = combo.getIsFlag();
		if (combo_isFlag.equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)) {
			list = networDao.getNetworkByComboId(map);
		} else {
			list = networDao.getNetworkByComboAndChannel(map);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				clientIds.add(list.get(i).getNetworkId());
			}
			Integer status = AdvNetwork.DELETE_CLIENT_ADV_FAIL;
			if (isDeleteNow.equals(Adelement.ADV_CLOSE_NOW_YES)) {
				status = AdvNetwork.DELETE_NOW_CLIENT_ADV_FAIL;// 紧急停播
			}
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("advId", a.getAdId());
			deleteMap.put("clientItem", clientIds);
			List<Integer> statusItem = Lists.newArrayList();
			statusItem.add(AdvNetwork.PUSH_CLIENT_ADV_FAIL);
			statusItem.add(AdvNetwork.PUSH_PLAY_NOW_CLIENT_ADV_FAIL);
			deleteMap.put("statusItem", statusItem);
			middleTableDao.deleteList1(deleteMap);
			insertSendFail(clientIds, a, status);
			Client.getInstance().deleteAdv(a.getAdId(), isDeleteNow, clientIds);

		}

	}

	/**
	 * 通过广告获取广告对应的发送器id
	 *
	 * @param entity
	 * @return
	 */
	public List<String> getClientIdsByAdv(Adelement entity) {

		AdCombo combo = entity.getAdCombo();
		if (null == combo) {
			entity = get(entity.getId());
		}
		List<String> clientIds = Lists.newArrayList();
		List<AdNetwork> list = null;
		String comboId = combo.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboId", comboId);
		Integer combo_isFlag = combo.getIsFlag();
		if (combo_isFlag.equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)) {
			list = networDao.getNetworkByComboId(map);
		} else {
			list = networDao.getNetworkByComboAndChannel(map);
		}
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				clientIds.add(list.get(i).getNetworkId());
			}
		}
		return clientIds;
	}



	/**
	 * 判断相同时间同一个广告套餐同一个广告商的广告是否重复，不能计算已经投放结束的广告（因为如果紧急停播添加广告是允许的）
	 *
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public boolean checkAdvIsRepeat(Map<String, Object> paramMap)
			throws Exception {
		if (paramMap != null) {
			String comboId = (String) paramMap.get("comboId");
			String start = (String) paramMap.get("startDate");
			String end = (String) paramMap.get("endDate");
			String id = (String) paramMap.get("id");
			String childAdType = (String) paramMap.get("childAdType");

			// 参数不完整
			if (org.apache.commons.lang3.StringUtils.isBlank(comboId) || org.apache.commons.lang3.StringUtils.isBlank(start)
					|| org.apache.commons.lang3.StringUtils.isBlank(end)) {
				return false;
			}
			Date input_startDate = org.apache.commons.lang3.time.DateUtils.parseDate(start, "yyyy-MM-dd");
			Date input_endDate = org.apache.commons.lang3.time.DateUtils.parseDate(end, "yyyy-MM-dd");
			String advId = (String) paramMap.get("advId");
			if (org.apache.commons.lang3.StringUtils.isBlank(advId)) {
				if (UserUtils.getUser().getAdvertiser() == null)
					return false;
				advId = UserUtils.getUser().getAdvertiser().getId();
			}
			int count = 0;
			if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
				count = getAdelementCount(advId, comboId, input_startDate,
						input_endDate, childAdType, null);

			} else {
				count = getAdelementCount(advId, comboId, input_startDate,
						input_endDate, childAdType, id);// 修改的时候，需要查询不能重复，但是自己本身不算在内
			}
			if (count == 0)
				return true;
		}

		return false;
	}

	public Integer getAdelementCount(String advId, String comboId,
									 Date startDate, Date endDate, String childAdType, String id)
			throws Exception {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("advId", advId);
		queryMap.put("comboId", comboId);
		queryMap.put("startDate", startDate);
		queryMap.put("endDate", endDate);
		queryMap.put("status", Adelement.ADV_STATUS_END);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(childAdType)) {
			queryMap.put("childAdType", childAdType);
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(id)) {
			queryMap.put("id", id);
		}

		return mybatisDao.checkIsAdvRepeat(queryMap);

	}

	public List<Adelement> findAdelementByAdName(String adName){
		Adelement adelement = new Adelement();
		adelement.setAdName(adName);
		adelement.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		return mybatisDao.findAdelementByAdName(adelement);
	}
	/*public static void main(String[] args) {
		List<AdControll> controllerList = new ArrayList<AdControll>();
		AdControll a1 = new AdControll();
		a1.setFilePath("/upload/advs/admin/m2v/admin_20170605150119216.png");
		AdControll a2 = new AdControll();
		a2.setFilePath("/upload/advs/admin/m2v/admin_20170605150119216.png");
		AdControll a3 = new AdControll();
		a3.setFilePath("/upload/advs/admin/m2v/admin_20170605150119216.png");
		controllerList.add(a1);
		controllerList.add(a2);
		controllerList.add(a3);
		String filePath = "/upload/advs/admin/m2v/admin_20170605150119216.png";
		String[] all = filePath.split("/");
		if (all != null && all.length > 0) {
			String[] path = new String[all.length - 2];
			for (int j = 2; j < all.length; j++) {
				path[j - 2] = all[j];
			}
			String paths = "/" + org.apache.commons.lang3.StringUtils.join(path, "/")+"@"+10;
			System.out.println(paths);
		}
	}*/

	/**
	 * 获取当前时间范围广告占用的内存
	 * @param adelement
	 * @return
	 */
	public AdChannelNCDTO getCurrentNC(String typeId, String sendMode, String networkList,String channelList,String startDate,String endDate,String startHour,String startMinutes,String startSecond,String endHour,String endMinutes,String endSecond){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("sendMode", sendMode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("startHour", startHour);
		map.put("startMinutes", startMinutes);
		map.put("startSecond", startSecond);
		map.put("endHour", endHour);
		map.put("endMinutes", endMinutes);
		map.put("endSecond", endSecond);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		paramMap.put("enable", SysParam.ENABLE_YES);

		if(typeId.equals(AdType.Type_SWITCH_ON_VEDIO)){
			paramMap.put("paramType", SysParam.NETWORK_VIDEO_RATE);
		}else{
			paramMap.put("paramType", SysParam.NETWORK_PICTURE_RATE);
		}
		SysParam param = sysParamDao.getMinParam(paramMap);
		Integer minRate = 1250000;
		if(param!=null){
			BigDecimal b = new BigDecimal(param.getParamValue());
			minRate =b.multiply(new BigDecimal(1000000)).intValue();
		}
		map.put("type", "SD");
		Integer sd = getNCByNetChl(map,typeId,networkList,channelList);

		map.put("type", "HD");
		Integer hd = getNCByNetChl(map,typeId,networkList,channelList);
		AdChannelNCDTO dto = new AdChannelNCDTO();
		dto.setSdMaxNC(minRate-sd);
		dto.setHdMaxNC(minRate-hd);
		System.out.println(sd+"@"+hd);
		return dto ;
	}

	/**
	 * 获取当前时间范围广告占用的内存
	 * @param adelement
	 * @return
	 */
	public AdChannelNCDTO getCurrentNC1(String typeId,String startDate,String endDate,String comboId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		paramMap.put("enable", SysParam.ENABLE_YES);

		if(typeId.equals(AdType.Type_SWITCH_ON_VEDIO)){
			paramMap.put("paramType", SysParam.NETWORK_VIDEO_RATE);
		}else{
			paramMap.put("paramType", SysParam.NETWORK_PICTURE_RATE);
		}
		SysParam param = sysParamDao.getMinParam(paramMap);
		Integer minRate = 1250000;
		if(param!=null){
			BigDecimal b = new BigDecimal(param.getParamValue());
			minRate =b.multiply(new BigDecimal(1000000)).intValue();
		}
		map.put("type", "SD");
		Integer sd = getNCByCombo(map,typeId,startDate,endDate,comboId);

		map.put("type", "HD");
		Integer hd = getNCByCombo(map,typeId,startDate,endDate,comboId);
		AdChannelNCDTO dto = new AdChannelNCDTO();
		dto.setSdMaxNC(minRate-sd);
		dto.setHdMaxNC(minRate-hd);
		System.out.println((minRate-sd)+"@"+(minRate-hd));
		return dto ;
	}

	public Integer getNCByCombo(Map<String, Object> map ,String typeId,String startDate,String endDate,String comboId){
		AdCombo combo = adComboDao.get(comboId);
		map.put("sendMode", combo.getSendMode());
		Map<String, Integer> ncMap = new HashMap<String, Integer>();
		if(typeId.equals(AdType.Type_OPEN_IMGAE) || typeId.equals(AdType.Type_BROCAST)){
			map.put("adType", "main");
			String[] ids = combo.getNetworkIds().split(",");
			for (String string : ids) {
				map.put("netWorkList", string);
				Integer s = getUsedNC(map);
				ncMap.put(string, s);
			}
		}else if(typeId.equals(AdType.Type_SWITCH_ON_VEDIO)){
			map.put("adType", "video");
			String[] ids = combo.getNetworkIds().split(",");
			for (String string : ids) {
				map.put("netWorkList", string);
				Integer s = getUsedNC(map);
				ncMap.put(string, s);
			}
		}else{
			map.put("adType", "other");
			if(combo.getIsFlag().equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)){
				String[] ids = combo.getNetworkIds().split(",");
				for (String string : ids) {
					List<String> channel_List = Lists.newArrayList();
					List<AdChannel> cl = adNetworDao.get(string).getChannelList();
					if(null != cl && cl.size()>0){
						for (AdChannel adChannel : cl) {
							channel_List.add(adChannel.getId());
						}
					}else{
						channel_List.add("-1");
					}
					map.put("netWorkList", string);
					map.put("channelList", channel_List);
					Integer s = getUsedNC(map);
					ncMap.put(string, s);
				}
			}else{
				map.put("startHour", String.valueOf(combo.getStartHour()));
				map.put("startMinutes", String.valueOf(combo.getStartMinutes()));
				map.put("startSecond", String.valueOf(combo.getStartSecond()));
				map.put("endHour", String.valueOf(combo.getEndHour()));
				map.put("endMinutes", String.valueOf(combo.getEndMinutes()));
				map.put("endSecond", String.valueOf(combo.getEndSecond()));
				List<AdChannel> channelList = combo.getChannelList();
				Map<String, List<String>> netmap = new HashMap<String, List<String>>();
				for (AdChannel adChannel : channelList) {
					String netId = adChannel.getAdNetWork().getId();
					if(netmap.containsKey(netId)){
						List<String> cls = netmap.get(netId);
						cls.add(adChannel.getId());
						netmap.put(netId,cls);
					}else{
						List<String> cls = Lists.newArrayList();
						cls.add(adChannel.getId());
						netmap.put(netId, cls);
					}
				}
				for (Entry<String, List<String>> entry : netmap.entrySet()) {
					// System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					map.put("netWorkList", entry.getKey());
					map.put("channelList", entry.getValue());
					Integer s = getUsedNC(map);
					ncMap.put(entry.getKey(), s);
				}
			}
		}
		Integer a = (Integer) getMaxValue(ncMap);
		System.out.println(a);
		return a;

	}

	public Integer getNCByNetChl(Map<String, Object> map,String typeId,String networkList,String channelList){
		Map<String, Integer> ncMap = new HashMap<String, Integer>();
		if(typeId.equals(AdType.Type_OPEN_IMGAE) || typeId.equals(AdType.Type_BROCAST)){
			map.put("adType", "main");
			String[] ids = networkList.split(",");
			for (String string : ids) {
				map.put("netWorkList", string);
				Integer s = getUsedNC(map);
				ncMap.put(string, s);
			}
		}else if(typeId.equals(AdType.Type_SWITCH_ON_VEDIO)){
			map.put("adType", "video");
			String[] ids = networkList.split(",");
			for (String string : ids) {
				map.put("netWorkList", string);
				Integer s = getUsedNC(map);
				ncMap.put(string, s);
			}
		}else{
			map.put("adType", "other");
			if(channelList != null){
				String[] ids = networkList.split(",");
				String[] s1 = channelList.split("@");
				for (int i = 1; i < s1.length; i++) {
					List<String> channel_List = Lists.newArrayList();
					String[] ids1 = s1[i].split(",");
					for (String string : ids1) {
						channel_List.add(string);
					}
					map.put("netWorkList", ids[i-1]);
					map.put("channelList", channel_List);
					Integer s = getUsedNC(map);
					ncMap.put(ids[i-1], s);
				}
			}else{
				String[] ids = networkList.split(",");
				for (String string : ids) {
					List<String> channel_List = Lists.newArrayList();
					adNetworDao.clear();
					AdNetwork nt = adNetworDao.get(string);
					if(null != nt){
						List<AdChannel> cl = nt.getChannelList();
						if(null != cl && cl.size()>0){
							for (AdChannel adChannel : cl) {
								channel_List.add(adChannel.getId());
							}
						}else{
							channel_List.add("-1");
						}
						map.put("netWorkList", string);
						map.put("channelList", channel_List);
						Integer s = getUsedNC(map);
						ncMap.put(string, s);
					}
				}
			}
		}
		Integer a = (Integer) getMaxValue(ncMap);
		System.out.println(a);
		return a;

	}


	public static Object getMaxValue(Map<String, Integer> map) {
		if (map == null || map.isEmpty()) return 0;
		Collection<Integer> c = map.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		return obj[obj.length-1];
	}

	/**
	 * 将占用的广告按照通道每天的每小时累加
	 * 对于频道无关广告中的开机图片和广播背景，尽在主频点通道发送，
	 * 对应频道无关广告中的开机视频，在视频通道发送
	 * 对于频道无关广告中的菜单和提示窗广告，在所有通道发送，
	 * 对于频道相关的广告，应检查该广告相关套餐所包含的频道，只在所属频道所属的通道中发送
	 * @param map
	 * @return
	 */
	private Integer getUsedNC(Map<String, Object> map){
		Map<String, Integer> ncMap = new HashMap<String, Integer>();
		Date startDate =DateUtils.parseDate(map.get("startDate")) ;
		Date endDate =  DateUtils.parseDate(map.get("endDate"));
		List<AdelementNCDTO> list = Lists.newArrayList();

		String adType = (String) map.get("adType");
		if(adType.equals("main")){
			List<AdelementNCDTO>  networkList= mybatisDao.findNetworkAdelements(map);
			list.addAll(networkList);
		}else if(adType.equals("video")){
			List<AdelementNCDTO>  networkList= mybatisDao.findNetworkAdelements(map);
			list.addAll(networkList);
		}else{
			List<AdelementNCDTO>  networkList= mybatisDao.findNetworkAdelements(map);
			if(map.get("startHour") != null){
				Integer startHour =Integer.parseInt((String) map.get("startHour"));
				Integer startMinutes =Integer.parseInt((String) map.get("startMinutes"));
				Integer startSecond =Integer.parseInt((String) map.get("startSecond"));
				Integer endHour =Integer.parseInt((String) map.get("endHour"));
				Integer endMinutes =Integer.parseInt((String) map.get("endMinutes"));
				Integer endSecond =Integer.parseInt((String) map.get("endSecond"));
				Integer startTime = startHour*3600+startMinutes*60+startSecond;
				Integer endTime = endHour*3600+endMinutes*60+endSecond;
				map.put("startTime", startTime);
				map.put("endTime", endTime);
			}else{
				map.put("startTime", 0);
				map.put("endTime", 86399);
			}
			List<AdelementNCDTO> channelList = mybatisDao.findChannelAdelements(map);

			list.addAll(channelList);
			list.addAll(networkList);
		}
		for (AdelementNCDTO adelementNCDTO : list) {
			//System.out.println(adelementNCDTO.getAdName());
			Integer fileSize =( adelementNCDTO.getFileSize()==null?0:adelementNCDTO.getFileSize());
			if(!(adelementNCDTO.getFileFormat().equals("m2v") || adelementNCDTO.getFileFormat().equals("ts"))){
				fileSize = fileSize * 1000;
			}
			Integer serviceId = (adelementNCDTO.getServiceId()==null?0:adelementNCDTO.getServiceId());
			Date minDate = adelementNCDTO.getStartDate();
			Date maxDate = adelementNCDTO.getEndDate();
			Date startRange1 = new Date();
			Date endRange1 = new Date();
			if(minDate.getTime() <= startDate.getTime() && maxDate.getTime() >= endDate.getTime()){
				startRange1 = startDate;
				endRange1 = endDate;
			}else if(minDate.getTime() >= startDate.getTime() && maxDate.getTime() <= endDate.getTime()){
				startRange1 = minDate;
				endRange1 = maxDate;
			}else if(minDate.getTime() <= startDate.getTime() && maxDate.getTime() >= startDate.getTime() && maxDate.getTime() <= endDate.getTime()){
				startRange1 = startDate;
				endRange1 = maxDate;
			}else if(minDate.getTime() >= startDate.getTime() && minDate.getTime() <= endDate.getTime() && maxDate.getTime() >= endDate.getTime()){
				startRange1 = minDate;
				endRange1 = endDate;
			}

			if(adelementNCDTO.getStartHour() == null){
				Calendar c = Calendar.getInstance();
				//开始时间必须小于结束时间
				Date date = startRange1;
				while (DateUtils.judgeDateSize(date, endRange1)!=-1) {
					//System.out.println(DateUtils.getDate(date));
					int start=0; int end = 23;
					if(adType.equals("other")){
						if(map.get("startHour") != null){
							start = Integer.parseInt((String) map.get("startHour"));
						}
						if(map.get("endHour") != null){
							end = Integer.parseInt((String) map.get("endHour"));
						}
					}
					for(int j = start;j<=end;j++){
						if(ncMap.containsKey(DateUtils.getDate(date)+"@"+serviceId+"@"+j)){
							ncMap.put(DateUtils.getDate(date)+"@"+serviceId+"@"+j, ncMap.get(DateUtils.getDate(date)+"@"+serviceId+"@"+j)+fileSize);
						}else{
							ncMap.put(DateUtils.getDate(date)+"@"+serviceId+"@"+j, fileSize);
						}
						//System.out.println(DateUtils.getDate(date)+"@"+serviceId+"@"+j+"===="+ncMap.get(DateUtils.getDate(date)+"@"+serviceId+"@"+j));
					}
					c.setTime(date);
					c.add(Calendar.DATE, 1); // 日期加1天
					date = c.getTime();
				}
			}else{
				Calendar c = Calendar.getInstance();
				//开始时间必须小于结束时间
				Date date = startRange1;
				Integer minTime = adelementNCDTO.getStartHour();
				Integer maxTime = adelementNCDTO.getEndHour();
				Integer startHour = 0;
				Integer endHour = 23;
				if(map.get("startHour") != null){
					startHour = Integer.parseInt((String) map.get("startHour"));
				}
				if(map.get("endHour") != null){
					endHour = Integer.parseInt((String) map.get("endHour"));
				}
				Integer startRange = 0;
				Integer endRange = 0;
				while (DateUtils.judgeDateSize(date, endRange1)!=-1) {
					//System.out.println(DateUtils.getDate(date));
					if(minTime<=startHour && maxTime>=endHour){
						startRange = startHour;
						endRange = endHour;
					}else if(minTime >= startHour && maxTime<=endHour){
						startRange = minTime;
						endRange = maxTime;
					}else if(minTime <= startHour && maxTime >= startHour  && maxTime<=endHour){
						startRange = startHour;
						endRange = maxTime;

					}else if(minTime >= startHour && minTime <=endHour  && maxTime>=endHour){
						startRange = minTime;
						endRange = endHour;
					}
					for(int j = startRange;j<=endRange;j++){
						if(ncMap.containsKey(DateUtils.getDate(date)+"@"+serviceId+"@"+j)){
							ncMap.put(DateUtils.getDate(date)+"@"+serviceId+"@"+j, ncMap.get(DateUtils.getDate(date)+"@"+serviceId+"@"+j)+fileSize);
						}else{
							ncMap.put(DateUtils.getDate(date)+"@"+serviceId+"@"+j, fileSize);
						}
						//System.out.println(DateUtils.getDate(date)+"@"+serviceId+"@"+j+"===="+ncMap.get(DateUtils.getDate(date)+"@"+serviceId+"@"+j));
					}
					c.setTime(date);
					c.add(Calendar.DATE, 1); // 日期加1天
					date = c.getTime();
				}
			}

		}
		Integer a = (Integer) getMaxValue(ncMap);
		System.out.println(a);
		return a;

	}

	public Integer findStatusById(Adelement adelement){
		return  mybatisDao.findStatusById(adelement);
	}
}
