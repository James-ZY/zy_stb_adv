package com.gospell.aas.service.adv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.adv.AdNetWorkUtils;
import com.gospell.aas.dto.adv.AdNetWorkDTO;
import com.gospell.aas.dto.adv.ChannelDTO;
import com.gospell.aas.dto.adv.SelectAdNetworkDTO;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdDistrictCategory;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdNetworkDistrict;
import com.gospell.aas.entity.adv.AdOperators;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdDistrictCategoryDao;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDao;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDistrictDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkChannelDTO;
import com.gospell.aas.webservice.netty.dto.AdvNetWorkDistrictDTO;

@Service
@Transactional(readOnly = true)
public class AdNetworkService extends BaseService {

	@Autowired
	private AdNetworkDao thisDao;
	@Autowired
	private AdChannelService channelService;
	@Autowired
	private INetworkDao mybaitisDao;

	@Autowired
	private AdComboService comboService;
	@Autowired
	private AdTypeService typeService;

	@Autowired
	private AdNetworkDistrictDao adNetworkDistrictDao;

	@Autowired
	private AdDistrictCategoryDao adDistrictCategoryDao;

	public AdNetwork get(String id) {
		return thisDao.get(id);
	}

	public List<AdNetwork> findAll() {
		return thisDao.findAll();
	}

	public void clear() {
		thisDao.clear();
	}

	@Transactional(readOnly = false)
	public void save(AdNetwork entity) {
		thisDao.clear();
		String selArea = entity.getSelAllArea();
		if(StringUtils.isNotBlank(selArea)){
			adNetworkDistrictDao.deleteNtwDis(entity.getId());

			String[] list = selArea.split("-");
			List<AdNetworkDistrict> adDistrictCategorys = new ArrayList<AdNetworkDistrict>();
			for (String string : list) {
				AdNetworkDistrict aod = new AdNetworkDistrict();
				aod.setId(IdGen.uuid());
				aod.setAdNetwork(entity);
				aod.setDistrict(new AdDistrictCategory(string.split(":")[0]));
				if(string.split(":").length>1){
					aod.setSelfDistrictId(string.split(":")[1]);
				}
				adDistrictCategorys.add(aod);
			}
			adNetworkDistrictDao.save(adDistrictCategorys);
		}

		thisDao.save(entity);
	}
	/**
	 * 根据条件查询广告发送器
	 *
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 * @throws Exception
	 */
	public Page<AdNetwork> find(Page<AdNetwork> page, AdNetwork entity) throws Exception {
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("n.ad_network_id ASC");
		}
		entity.setPage(page);
		List<AdNetwork> list = mybaitisDao.findAllPage(entity);
		page.setList(list);

		return page;
	}

	public Page<AdNetwork> findAll(Page<AdNetwork> page, AdNetwork entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();

		dc.createAlias("adOperators", "adOperators");
		if (entity.getAdOperators() != null
				&& StringUtils.isNotBlank(entity.getAdOperators().getOperatorsName())) {
			dc.add( Restrictions.like("adOperators.operatorsName", "%"
					+ entity.getAdOperators().getOperatorsName() + "%"));
		}

		// System.out.println(dataScopeFilterString(currentUser, "office", ""));
		if (StringUtils.isNotEmpty(entity.getNetworkName())) {
			dc.add(Restrictions.like("networkName", "%" +entity.getNetworkName()
					+ "%"));
		}
		if (StringUtils.isNotEmpty(entity.getNetworkId() )) {
			dc.add(Restrictions.like("networkId",   entity.getNetworkId() + "%"));
		}

		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("networkId"));

		page = thisDao.find(page, dc);

		return page;
	}


	/**
	 * 注册广告发送器
	 *
	 * @param dto
	 * @param op
	 */
	@Transactional(readOnly = false)
	public void registerAdNetwork(AdNetWorkDTO dto, AdOperators op)  throws Exception{
		AdNetwork network = new AdNetwork();
		network.setStatus(AdNetwork.NETWORK_YES_STATUS);
		network.setAdOperators(op);
		network.setCpu(dto.getCpu());
		network.setMemory(dto.getMemory());
		network.setNetworkId(dto.getNetworkId());
		network.setNetworkName(dto.getNetworkName());
		network.setWayEncryption(dto.getWayEncryption());
		network.setIp(dto.getIp());
		network.setPort(dto.getPort());
		network.setSecretKey(dto.getSecretKey());
		network.setOnlineStatus(dto.getOnlineStatus());
		network.setIsControlAllAD(dto.getIsControlAllAD());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String validDate = dto.getValidDate()==null?format.format(new Date()):dto.getValidDate();
		Date date  =format.parse(validDate);
		network.setValidDate(date);
		thisDao.save(network);
/*		op.setNetwork(network);// 保存电视运营商与广告发送器之间的关系
*/


	}

	/**
	 * 更新广告发送器
	 *
	 * @param dto
	 * @param network
	 */
	@Transactional(readOnly = false)
	public void updateAdNetwork(AdNetWorkDTO dto, AdNetwork network) throws Exception{

		network.setStatus(AdNetwork.NETWORK_YES_STATUS);
		network.setCpu(dto.getCpu());
		network.setMemory(dto.getMemory());
		network.setNetworkId(dto.getNetworkId());
		network.setNetworkName(dto.getNetworkName());
		network.setWayEncryption(dto.getWayEncryption());
		network.setIp(dto.getIp());
		network.setPort(dto.getPort());
		network.setSecretKey(dto.getSecretKey());
		network.setOnlineStatus(dto.getOnlineStatus());
		network.setIsControlAllAD(dto.getIsControlAllAD());
		String validDate = dto.getValidDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date  =format.parse(validDate);
		network.setValidDate(date);
		thisDao.save(network);

	}

	/**
	 * 保存频道信息
	 *
	 * @param dto
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public String saveChannleToNetwork(AdvNetWorkChannelDTO dto,
									 AdNetwork adNetwork) throws ServiceException {

		List<ChannelDTO> dtoList = dto.getChannelList();
		// 保存节目
		if (null != dtoList && dtoList.size() > 0) {
			return channelService.saveChannelByNetwork(dtoList, adNetwork);
		}/*else{
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("delFlag", BaseEntity.DEL_FLAG_DELETE);
			queryMap.put("networkId", adNetwork.getId());
			channelService.updateChannel(queryMap);
		}*/
		return null;

	}

	/**
	 * 停用广告发送器
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void stopNetwork(AdNetwork entity) throws Exception {
		thisDao.clear();
		comboService.updateAdcombovalidBynetworkId(entity.getId());
		thisDao.save(entity);

	}

	/**
	 * 启用广告发送器
	 *
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void startNetwork(AdNetwork entity) throws Exception {
		thisDao.clear();

		thisDao.save(entity);

	}

	/**
	 * 通过广告发送器ID获取g广告发送器信息
	 *
	 * @param networkId
	 * @return
	 */
	public AdNetwork findByNetworkId(String networkId) {
		return thisDao.findByNetworkId(networkId);
	}

	/**
	 * 通过广告发送器ID和运营商获取g广告发送器信息
	 *
	 * @param networkId
	 * @return
	 */
	public AdNetwork findByNetworkIdAndOperatorId(String networkId,String adOperatorId) {
		return thisDao.findByNetworkIdAndOperatorId(networkId,adOperatorId);
	}

	public List<AdNetworkDistrict> getNetDis(String networkId){
		return adNetworkDistrictDao.getNetDis(networkId);
	}

	/**
	 * 通过广告运营商ID查询广告发送器
	 *
	 * @param operatorId
	 * @return
	 */
	public AdNetwork findAdNetworkByOperatorId(String operatorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("operatorId", operatorId);
		return mybaitisDao.getNetworkByOperatorId(map);
	}

	/**
	 * 与频道无关的套餐，同一个发送器同一种广告类型只能有一个有效的广告套餐(普通模式)
	 *
	 * @throws Exception
	 */
	public List<SelectAdNetworkDTO> findNetWorkByTypeAndCombo(String typeId,String chlidType,String startDate,String endDate,String sendMode,String advertiserId)
			throws Exception {
		List<AdNetwork> allList = findAllNetworkByTypeId(typeId);
		if (null == allList || allList.size() == 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
		map.put("status", AdNetwork.NETWORK_YES_STATUS);
		map.put("isValid", AdCombo.ADCOMOBO_YES_VALID);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("chlidType", chlidType);
		map.put("sendMode", sendMode);
		map.put("advertiserId", advertiserId);
		List<AdNetwork> list = mybaitisDao.findNetWorkByTypeAndCombo(map);// 查询当前广告类型已经使用的发送器
		Map<String, String> networkMap = AdNetWorkUtils.networkListToMap(list);
		List<SelectAdNetworkDTO> returnList = Lists.newArrayList();
		for (int i = 0; i < allList.size(); i++) {
			AdNetwork n = allList.get(i);
			SelectAdNetworkDTO dto = new SelectAdNetworkDTO();
			if (!networkMap.isEmpty() && networkMap.containsKey(n.getId())) {
				dto.setInvalid(false);
			} else {
				dto.setInvalid(true);
			}
			dto.setId(n.getId());
			dto.setNetworkName(n.getNetworkName());
			dto.setArea(n.getArea());
			returnList.add(dto);
		}
		return returnList;
	}
	/**
	 * 查询某个与频道无关的广告类型对应的所有的发送器
	 * @param typeId
	 * @return
	 */
	public List<AdNetwork> findAllNetworkByTypeId(String typeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("typeId", typeId);
		map.put("status", AdNetwork.NETWORK_YES_STATUS);
		return mybaitisDao.findAllNetworkByTypeId(map);
	}
	public List<AdNetwork> findAllByMabatis() {
		AdNetwork n = new AdNetwork();
		n.setStatus(AdNetwork.NETWORK_YES_STATUS);
		return mybaitisDao.findAll(n);
	}

	/**
	 * 给广告发送器添加与频道无关的广告类型
	 *
	 * @param networkId
	 * @param typeIdList
	 */
	@Transactional(readOnly = false)
	public void addAdTypeToNetwork(String networkId, List<String> typeIdList) {
		List<AdType> queryTypeList = typeService.findAdTypeByIds(typeIdList);
		List<AdType> typeList = Lists.newArrayList();
		// 必须排除上传的广告类型是与频道相关的
		if (null != queryTypeList && queryTypeList.size() > 0) {
			for (int i = 0; i < queryTypeList.size(); i++) {
				AdType type = queryTypeList.get(i);
				int flag = type.getIsFlag();
				if (flag == AdType.TYPE_NOT_CHANNEL) {
					typeList.add(type);
				}
			}
		}
		if (null != typeList && typeIdList.size() > 0) {
			AdNetwork a = thisDao.findByNetworkId(networkId);

			if (a != null) {

				List<AdType> networkTypeList = a.getTypeList();
				Map<String, AdType> map = new HashMap<String, AdType>();
				List<AdType> saveTypeList = Lists.newArrayList();
				if (networkTypeList != null && networkTypeList.size() > 0) {
					for (int i = 0; i < networkTypeList.size(); i++) {
						AdType type = networkTypeList.get(i);
						map.put(type.getId(), type);
						saveTypeList.add(type);
					}
				}

				for (int i = 0; i < typeList.size(); i++) {
					AdType t = typeList.get(i);
					if (!map.containsKey(t.getId())) {
						saveTypeList.add(t);
					}
				}
				a.setTypeList(saveTypeList);
				thisDao.save(a);
			}
		}

	}

	/**
	 * 删除广告发送器添加与频道无关的广告类型
	 * 删除之前必须判断当前发送器的当前广告类型是否有广告套餐，如果有的话，是不允许删除的
	 *
	 * @param networkId
	 * @param typeId
	 */
	@Transactional(readOnly = false)
	public Boolean deleteAdTypeToNetwork(String networkId, String typeId) {
		boolean b = true;
		AdType adType = typeService.findAdTypeByTypeIdAndNotChannel(typeId,AdType.TYPE_NOT_CHANNEL);//必须保证上传的广告类型ID是与频道类型无关的
		if(null != adType){
			int count = comboService.getComboCountByTypeIdAndNetwork(networkId, typeId);
			if(count >0){
				b=false;
			}else{
				//删除对应的广告发送器
				AdNetwork a = thisDao.findByNetworkId(networkId);
				if (a != null) {

					List<AdType> networkTypeList = a.getTypeList();
					if (networkTypeList != null && networkTypeList.size() > 0) {
						for (int i = 0; i < networkTypeList.size(); i++) {
							AdType type = networkTypeList.get(i);
							String id = type.getId();
							if(id.equals(typeId)){
								networkTypeList.remove(type);
								break;
							}
						}
					}

				}
			}
		}
		return b;
	}

	/**
	 * 删除频道
	 * 删除之前必须判断当前发送器的频道是否有广告套餐，如果有的话，是不允许删除的
	 *
	 * @param networkId
	 * @param typeId
	 */
	@Transactional(readOnly = false)
	public Boolean deleteChannelToNetwork(String networkId, String channelId) {
		boolean b = true;
		int count =0;
		try{
			count = comboService.getComboCountChannelIdAndNetwork(networkId, channelId);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count >0){
			b=false;
		}else{
			//删除对应的广告发送器
			AdChannel c = channelService.findAdChannelByChannelId(channelId, networkId);
			if (c!= null) {
				c.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
				channelService.save(c);
			}
		}

		return b;
	}


	/**
	 * 批量删除频道
	 * @param networkId
	 * @param channelIds
	 */
	@Transactional(readOnly = false)
	public void deleteChannels(String networkId, List<String> channelIds) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_DELETE);
		queryMap.put("networkId", networkId);
		queryMap.put("channelIds", channelIds);
		channelService.updateChannel(queryMap);
	}

	/**
	 * 批量删除套餐对应频道数据
	 * @param channelIds
	 */
	@Transactional(readOnly = false)
	public void deleteChannelList(List<String> channelIds) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_DELETE);
		queryMap.put("channelIds", channelIds);
		channelService.deleteChannelList(queryMap);
	}


	public List<String> getCanDeleteAdChannel(String networkId, List<String> channelIds){
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("networkId", networkId);
		queryMap.put("channelIds", channelIds);
		List<AdChannel> list = channelService.getCanDeleteAdChannel(queryMap);
		List<String> deleteList = Lists.newArrayList();
		if(null != list && list.size()>0){
			for (AdChannel channel : list) {
				deleteList.add(channel.getChannelId());
			}
		}
		return deleteList;
	}

	public AdvNetWorkDistrictDTO getNetDisDto(AdNetwork network){
		List<AdNetworkDistrict> netList = this.getNetDis(network.getId());
		AdvNetWorkDistrictDTO disDto = new AdvNetWorkDistrictDTO();
		String childrenRegionId = "";
		if(null != netList && netList.size()>0){
			disDto.setClientId(network.getNetworkId());
			for (AdNetworkDistrict adNetworkDistrict : netList) {
				if(adNetworkDistrict.getDistrict().getId().equals(network.getSelArea())){
					disDto.setRegionId(adNetworkDistrict.getSelfDistrictId());
					AdDistrictCategory dsc = adDistrictCategoryDao.get(adNetworkDistrict.getDistrict().getId());
					disDto.setRegionName(dsc.getCategoryName());
				}else{
					if(null != adNetworkDistrict.getSelfDistrictId()){
						childrenRegionId += adNetworkDistrict.getSelfDistrictId() + ",";
					}
				}
			}
			if(StringUtils.isNotBlank(childrenRegionId)){
				childrenRegionId = childrenRegionId.substring(0, childrenRegionId.lastIndexOf(","));
				disDto.setChildrenRegionId(childrenRegionId);
			}
		}
		return disDto;
	}
}
