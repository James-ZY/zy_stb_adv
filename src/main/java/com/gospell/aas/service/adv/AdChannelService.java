package com.gospell.aas.service.adv;

import java.text.SimpleDateFormat;
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
import com.gospell.aas.common.utils.adv.AdChannelUtils;
import com.gospell.aas.common.utils.adv.AdComboUtils;
import com.gospell.aas.dto.adv.AdChannelDTO;
import com.gospell.aas.dto.adv.AdChannelTypeDTO;
import com.gospell.aas.dto.adv.AdcomboUsedDTO;
import com.gospell.aas.dto.adv.ChannelDTO;
import com.gospell.aas.dto.adv.NetWorkDTO;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdChannelDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IChannelDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdChannelService extends BaseService {

	@Autowired
	private AdChannelDao thisDao;

	@Autowired
	private AdTypeService adTypeService;

	@Autowired
	private IChannelDao channelDao;
	
	@Autowired
	private IAdComboDao comboDao;
	
	@Autowired
	private AdNetworkService adNetworkService;

	public AdChannel get(String id) {
		return thisDao.get(id);
	}
	
	public void clear(){
		  thisDao.clear();
	}

	/**
	 * 根据条件查询签约频道
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdChannel> find(Page<AdChannel> page, AdChannel entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		dc.createAlias("adNetWork", "adNetWork");
		if (null != entity.getAdNetWork()) {
			if (StringUtils.isNotBlank(entity.getAdNetWork().getNetworkId())) {
				dc.add(Restrictions.like("adNetWork.networkId", "%" + entity.getAdNetWork().getNetworkId() + "%"));
			}
		}
		if (StringUtils.isNotBlank(entity.getChannelId())) {
			dc.add(Restrictions.like("channelId", "%" + entity.getChannelId() + "%"));
		}
		if (StringUtils.isNotBlank(entity.getChannelName())) {
			dc.add(Restrictions.like("channelName", "%" + entity.getChannelName() + "%"));
		}
		if (null != entity.getTypeList() && entity.getTypeList().size() > 0) {
			String typeId = entity.getTypeList().get(0).getId();
			dc.add(Restrictions
					.sqlRestriction(typeId + "in (select ad_type_id from ad_type_channel where ad_channel_id )"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("channelId"));
		return thisDao.find(page, dc);
	}

	/**
	 * 用sql进行分页查询频道
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<AdChannel> findBysql(Page<AdChannel> page, AdChannel entity) {
 

		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("n.ad_network_id asc,CAST(ca.ad_channel_id AS DECIMAL) asc");
		}
		entity.setPage(page);
		List<AdChannel> list = channelDao.findAll(entity);
		page.setList(list);
		return page;
	}

	/**
	 * 保存频道
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdChannel entity) throws ServiceException {
		thisDao.clear();
		thisDao.save(entity);

	}

	/**
	 * 获取
	 * 
	 * @param AdChannelIds
	 *            广告类型ID
	 * @return
	 */
//	public List<AdChannel> findAdChannelByIds(List<String> adChannelIds, String networkId) {
//		return thisDao.findAdChannelByIds(adChannelIds, networkId);
//	}

	public AdChannel findAdChannelByChannelId(String channelId, String networkId) {
		return thisDao.findAdChannelByChannelId(channelId, networkId);
	}
	public List<AdChannel> findAdChannelByNetworkId(String networkId) {
		return thisDao.findAdChannelByNetworkId(networkId);
	}

	@Transactional(readOnly = false)
	public void removeChannnelList(List<AdChannel> channelList) {
		if (null != channelList && channelList.size() > 0) {
			for (int i = 0; i < channelList.size(); i++) {
				thisDao.deleteById(channelList.get(i).getId());
			}
		}
	}

	@Transactional(readOnly = false)
	public void saveChannelByNetwork(List<ChannelDTO> dtoList, AdNetwork network) throws ServiceException {
		List<String> channelIdList = Lists.newArrayList();
		for (int i = 0; i < dtoList.size(); i++) {
			String channelId = dtoList.get(i).getChannelId();
			if(StringUtils.isBlank(channelId)){
				continue;
			}
			channelIdList.add(dtoList.get(i).getChannelId());
		}
		if(null == channelIdList || channelIdList.size() ==0){
			return;
		}
		List<AdChannel> addList = Lists.newArrayList();
		List<String> deletechannelIds = Lists.newArrayList();
	    Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			queryMap.put("networkId", network.getId());
			List<AdChannel> networkList =   channelDao.getAdChannelByNetWorkId(queryMap);
		Map<String, List<AdType>> adTypemap = getAdType(dtoList);
			Map<String, AdChannel> map = new HashMap<String,AdChannel>();
			for (int i = 0; i < networkList.size(); i++) {
				map.put(networkList.get(i).getChannelId(), networkList.get(i));
			}
			for (int i = 0; i < dtoList.size(); i++) {
				AdChannel channel = null;
				String channelId = dtoList.get(i).getChannelId();
				if(dtoList.get(i).getDeleteFlag() != null && dtoList.get(i).getDeleteFlag().equals("0")){
					if (map.containsKey(channelId)) {//判断数据库是否存在该值
						channel = map.get(channelId);
					}else{
						channel = new AdChannel();
						channel.setId(IdGen.uuid());
					}
					channel.setAdNetWork(network);
					channel.setChannelId(dtoList.get(i).getChannelId());
					channel.setChannelName(dtoList.get(i).getChannelName());
					channel.setChannelType(dtoList.get(i).getChannelType());
					channel.setServiceId(dtoList.get(i).getServiceId());
					channel.setServiceName(dtoList.get(i).getServiceName());
					channel.setIsMainChannel(dtoList.get(i).getIsMainChannel());
					channel.setIsVideoChannel(dtoList.get(i).getIsVideoChannel());
					channel.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
					if (null != adTypemap && adTypemap.containsKey(dtoList.get(i).getChannelId())) {
						channel.setTypeList(adTypemap.get(dtoList.get(i).getChannelId()));
					}
					addList.add(channel);
				}else{
					deletechannelIds.add(channelId);
				}
			}
			thisDao.clear();
			thisDao.save(addList);
			if(deletechannelIds!=null && deletechannelIds.size()>0){
				adNetworkService.deleteChannels(network.getId(), deletechannelIds);				
			}

	}


	/**
	 * //根据上传的频道信息获取数据库已经存在的数据,并且删除多余的数据，比如原来存在cctv-1.现在上传的频道不存在该频道
	 * 
	 * @param channelIdList
	 * @param networkId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<AdChannel> getExistChannel(List<ChannelDTO> dtoList, AdNetwork network) {
		List<AdChannel> addList = Lists.newArrayList();
		List<String> deletechannelIds = Lists.newArrayList();
	    Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			queryMap.put("networkId", network.getId());
			List<AdChannel> networkList =   channelDao.getAdChannelByNetWorkId(queryMap);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  "+networkList.size());
		Map<String, List<AdType>> adTypemap = getAdType(dtoList);
		if (null != networkList && networkList.size() > 0) {
			Map<String, AdChannel> map = new HashMap<String,AdChannel>();
			for (int i = 0; i < networkList.size(); i++) {
				map.put(networkList.get(i).getChannelId(), networkList.get(i));
			}
			for (int i = 0; i < dtoList.size(); i++) {
				AdChannel channel = null;
				String channelId = dtoList.get(i).getChannelId();
				if (map.containsKey(channelId)) {//判断数据库是否存在该值
					if(dtoList.get(i).getDeleteFlag() != null && dtoList.get(i).getDeleteFlag().equals("0")){
						channel = map.get(channelId);
					}else{
						deletechannelIds.add(channelId);
					}
				}else{
					channel = new AdChannel();
					channel.setId(IdGen.uuid());
				}
				channel.setAdNetWork(network);
				channel.setChannelId(dtoList.get(i).getChannelId());
				channel.setChannelName(dtoList.get(i).getChannelName());
				channel.setChannelType(dtoList.get(i).getChannelType());
				channel.setServiceId(dtoList.get(i).getServiceId());
				channel.setServiceName(dtoList.get(i).getServiceName());
				channel.setIsMainChannel(dtoList.get(i).getIsMainChannel());
				channel.setIsVideoChannel(dtoList.get(i).getIsVideoChannel());
				channel.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
				if (null != adTypemap && adTypemap.containsKey(dtoList.get(i).getChannelId())) {
					channel.setTypeList(adTypemap.get(dtoList.get(i).getChannelId()));
				}
				addList.add(channel);
			}
			adNetworkService.deleteChannels(network.getId(), deletechannelIds);
			thisDao.save(addList);
		}
/*		
		if (null != networkList && networkList.size() > 0) {
			Map<String, ChannelDTO> map = new HashMap<String,ChannelDTO>();
			for (int i = 0; i < dtoList.size(); i++) {
				map.put(dtoList.get(i).getChannelId(), dtoList.get(i));
			}
			for (int i = 0; i < networkList.size(); i++) {
				String channelId = networkList.get(i).getChannelId();
				if (map.containsKey(channelId)) {//判断改判断是否被删除
					if(map.get(channelId).getDeleteFlag() != null && map.get(channelId).getDeleteFlag().equals("0")){
						addList.add(networkList.get(i));						
					}else{
						System.out.println(channelId);
						channelIds.add(channelId);
					}
				}
			}
			adNetworkService.deleteChannels(network.getId(), channelIds);
		}*/
 
		return addList;
	}

	/**
	 * 把频道list转换为map，因为频道数据比较多，防止循环嵌套循环
	 * 
	 * @param list
	 * @return
	 */
	public Map<String, AdChannel> channelListToMap(List<AdChannel> list) {
		Map<String, AdChannel> map = new HashMap<String, AdChannel>();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getChannelId(), list.get(i));
			}
		}
		return map;
	}

	/**
	 * 获取每个频道的广告类型
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, List<AdType>> getAdType(List<ChannelDTO> dtoList) {
		Map<String, List<AdType>> channelMap = new HashMap<String, List<AdType>>();
		Map<String, String> adTypeMap = new HashMap<String, String>();
		List<String> adTypeIds = Lists.newArrayList();// 广告发送器上传的广告类型字符串格式暂定为
														// （1,2,3），需要进行逗号处理
		if (null != dtoList && dtoList.size() > 0) {
			for (int i = 0; i < dtoList.size(); i++) {
				ChannelDTO channelDto = dtoList.get(i);
				String adtypeId = channelDto.getAdTypeId();

				if (StringUtils.isNotBlank(adtypeId)) {
					String[] adtypeList = adtypeId.split(",");
					for (int j = 0; j < adtypeList.length; j++) {
						if (!adTypeIds.contains(adtypeList[j])) {
							adTypeIds.add(adtypeList[j]);
						}
					}
					adTypeMap.put(channelDto.getChannelId(), adtypeId);
				}
			}
		}
		// 获取某个频道对应的广告类型
		if (adTypeIds.size() > 0) {
			List<AdType> list = adTypeService.findAdTypeByIds(adTypeIds);
		 
			if (null != list && list.size() > 0) {
				Map<String, AdType> map = new HashMap<String, AdType>();
				for (int i = 0; i < list.size(); i++) {
					map.put(list.get(i).getTypeId(), list.get(i));
				}
				for (String key : adTypeMap.keySet()) {
					List<AdType> typeList = Lists.newArrayList();
					String adTypeId = adTypeMap.get(key);
					String[] adtypeList = adTypeId.split(",");
					for (int j = 0; j < adtypeList.length; j++) {
						typeList.add(map.get(adtypeList[j]));
					}
					channelMap.put(key, typeList);
				}
			}
		}
		return channelMap;
	}

	@Transactional(readOnly = false)
	public void deleteList(List<AdChannel> list) throws ServiceException {
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDelFlag(BaseEntity.DEL_FLAG_DELETE);
			}
			thisDao.save(list);
		}

	}

	/**
	 * 根据广告发送器id获取所有的频道类型(比如体育)
	 * 
	 * @param networkId
	 * @return
	 */
	public AdChannelTypeDTO getChannelTypeList(String networkId) {
		List<String> typeList = channelDao.getChannelType(networkId);
		AdChannelTypeDTO dto = null;
		if (null != typeList && typeList.size() > 0) {
			dto = new AdChannelTypeDTO();
			dto.setNetworkId(networkId);
			dto.setChannelTypeList(typeList);
		}
		return dto;
	}

	/**
	 * 根据广告发送器id获取所有的频道
	 * 
	 * @param networkId
	 * @return
	 */
	public List<NetWorkDTO> getChannelList(String networkId) {
		List<AdChannelDTO> list = channelDao.getChannel(networkId);
		List<NetWorkDTO> allList = Lists.newArrayList();
		List<String> typeList = channelDao.getChannelType(networkId);
		if (null != list && list.size() > 0) {
			Map<String, List<AdChannelDTO>> map = AdChannelUtils.listToMap(list);
			if (null != typeList && typeList.size() > 0) {
				for (int i = 0; i < typeList.size(); i++) {
					String type = typeList.get(i);
					NetWorkDTO dto = new NetWorkDTO();
					dto.setNetworkId(networkId);
					dto.setNetworkName(list.get(0).getNetworkName());
					dto.setTypeName(type);
					if(map.containsKey(type)){
						dto.setChannelList(map.get(type));
					}
					allList.add(dto);
				}
			}

		}
		return allList;
	}
	
//	/**
//	 * 根据广告发送器id获取所有的频道
//	 * 
//	 * @param networkId
//	 * @return
//	 * @throws Exception 
//	 */
//	public List<NetWorkDTO> getChannelListByNetWorkAndTypeId(String networkId,String typeId,String startTime,String endTime) throws Exception {
//		Map<String,Object> queryMap = new HashMap<String,Object>();
//		queryMap.put("delFlag", AdChannel.DEL_FLAG_NORMAL);
//		queryMap.put("typeId", typeId);
//		queryMap.put("networkId", networkId);
//		List<AdChannelDTO> list = channelDao.getChannelByNetworkIdAndTypeId(queryMap);
//		List<NetWorkDTO> allList = Lists.newArrayList();
//		List<String> typeList = channelDao.getChannelType(networkId);
//		if (null != list && list.size() > 0) {
//			List<AdChannel> channel_list = getChannelByCombo(networkId, typeId, startTime, endTime);
//			Map<String,String> channelMap = AdChannelUtils.channeListToMap(channel_list);
//			Map<String, List<AdChannelDTO>> map = AdChannelUtils.listToMap(list);
//			if (null != typeList && typeList.size() > 0) {
//				for (int i = 0; i < typeList.size(); i++) {
//					String type = typeList.get(i);
//					NetWorkDTO dto = new NetWorkDTO();
//					dto.setNetworkId(networkId);
//					dto.setNetworkName(list.get(0).getNetworkName());
//					dto.setTypeName(type);
//					if(map.containsKey(type)){
//						dto.setChannelList(map.get(type));
//					}
//					allList.add(dto);
//					
//				}
//			}
//			if(allList != null && allList.size() >0){
//				for (int i = 0; i < allList.size(); i++) {
//					List<AdChannelDTO> channelDtoList = allList.get(i).getChannelList();
//					if(null != channelDtoList && channelDtoList.size() >0){
//						for (int j = 0; j < channelDtoList.size(); j++) {
//							String id = channelDtoList.get(j).getChannelId();
//							if(channelMap.containsKey(id)){
//								channelDtoList.get(j).setInvalid(false);
//							}
//						}
//					}
//				}
//			}
//
//		}
//		return allList;
//	}
	
	/**
	 * 根据广告发送器id获取所有的频道
	 * 
	 * @param networkId
	 * @return
	 * @throws Exception 
	 */
	public List<NetWorkDTO> getChannelListByNetWorkAndTypeId(String networkId,String typeId,String startTime,String endTime,String startDate,String endDate) throws Exception {
		if(networkId.equals("33d59aa7e35148bea7bc48ace0f7a309")){
			System.out.println(111);
		}
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("typeId", typeId);
		queryMap.put("networkId", networkId);
		List<AdChannelDTO> list = channelDao.getChannelByNetworkIdAndTypeId(queryMap);
		List<NetWorkDTO> allList = Lists.newArrayList();
		List<String> typeList = channelDao.getChannelType(networkId);
		if (null != list && list.size() > 0) {
			Map<String,Object> queryMap1 =getChannelByCombo(networkId, typeId, startTime, endTime,startDate,endDate);
			List<AdChannel> channel_list = null;
			if(queryMap1 != null){
				queryMap.put("ids", queryMap1.get("ids"));
				channel_list = channelDao.getAdChannelByAdComboId(queryMap);				
			}
/*			List<AdChannel> channel_list = getChannelByCombo(networkId, typeId, startTime, endTime,startDate,endDate);
*/			//List<AdChannel> channel_list = getChannelByCombo(networkId, typeId, startTime, endTime);
			Map<String,String> channelMap = AdChannelUtils.channeListToMap(channel_list);
			Map<String, List<AdChannelDTO>> map = AdChannelUtils.listToMap(list);
			if (null != typeList && typeList.size() > 0) {
				for (int i = 0; i < typeList.size(); i++) {
					String type = typeList.get(i);
					NetWorkDTO dto = new NetWorkDTO();
					dto.setNetworkId(networkId);
					dto.setNetworkName(list.get(0).getNetworkName());
					dto.setTypeName(type);
					if(map.containsKey(type)){
						dto.setChannelList(map.get(type));
					}
					allList.add(dto);
					
				}
			}
			if(allList != null && allList.size() >0){
				for (int i = 0; i < allList.size(); i++) {
					List<AdChannelDTO> channelDtoList = allList.get(i).getChannelList();
					if(null != channelDtoList && channelDtoList.size() >0){
						for (int j = 0; j < channelDtoList.size(); j++) {
							String id = channelDtoList.get(j).getChannelId();
							if(channelMap.containsKey(id)){
								channelDtoList.get(j).setInvalid(false);
								List<AdcomboUsedDTO> dtol = (List<AdcomboUsedDTO>) queryMap1.get(networkId);
								List<AdcomboUsedDTO> dto2 = Lists.newArrayList();								
								for (AdcomboUsedDTO adcomboUsedDTO : dtol) {
									List<String> channelList = adcomboUsedDTO.getChannelList();
									if(channelList.contains(id)){
										dto2.add(adcomboUsedDTO);
									}
								}
								channelDtoList.get(j).setAdcomboUsedList(dto2);
							}
						}
					}
				}
			}

		}
		return allList;
	}
	
 
	
	/**
	 * 根据广告发送器id和广告类型以及时间段获取所有的频道（即在同一个广告类型和时间段内频道Id不能重用）
	 * 
	 * @param networkId
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getChannelByCombo(String networkId,String typeId,String startTime,String endTime,String startDate,String endDate) throws Exception {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("typeId", typeId);
		queryMap.put("networkId", networkId);
		queryMap.put("startDate", format.parse(startDate));
		queryMap.put("endDate", format.parse(endDate));
		queryMap.put("isValid", AdCombo.ADCOMOBO_YES_VALID);
		List<AdCombo> list = comboDao.getComboByNetworkIdAndTypeId(queryMap);
		if(null == list){
			return null;
		}
		List<String> id_list = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			List<AdcomboUsedDTO> usedList = Lists.newArrayList();
			String start = list.get(i).getStartTime();
			String end = list.get(i).getEndTime();
			boolean b = AdComboUtils.isIntime(start, end, startTime, endTime);
			if(!b){
				AdcomboUsedDTO dto = new AdcomboUsedDTO();
				id_list.add(list.get(i).getId());
				dto.setId(list.get(i).getId());
				dto.setComboName(list.get(i).getComboName());
				dto.setStartTime(start);
				dto.setEndTime(end);
				List<AdChannel> clist = list.get(i).getChannelList();
				List<String> channelList = Lists.newArrayList();
				for (AdChannel adChannel : clist) {
					channelList.add(adChannel.getId());
				}
				dto.setChannelList(channelList);
				usedList.add(dto);
				if(queryMap.containsKey(networkId)){
					List<AdcomboUsedDTO> dtol = (List<AdcomboUsedDTO>) queryMap.get(networkId);
					dtol.add(dto);
					queryMap.put(networkId, dtol);					
				}else{
					queryMap.put(networkId, usedList);					
				}
					
			}
		}
		if(null == id_list || id_list.size() == 0){
			return null;
		}else{
			queryMap.put("ids", id_list);
			return queryMap;
		}
		 
	}	
	
	public void updateChannel(Map<String,Object> map){
		channelDao.updateChannel(map);
		
	}
	
	List<AdChannel> getCanDeleteAdChannel(Map<String,Object> map){
		return channelDao.getCanDeleteAdChannel(map);
	}

}
