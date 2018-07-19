package com.gospell.aas.service.adv;

import java.text.SimpleDateFormat;
import java.util.*;

import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.webservice.netty.dto.DeleteChannelDTO;
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
	 * @param channelId
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

	/**
	* @Description:  A. 当节目名称一样，节目号变为另一个ID时(无论该ID原来是否存在)，将包含该节目名称的套餐的相关节目修改为新的节目号；
	B. 当出现新的节目名称，该节目名称对应的频道号原来就存在时，将包含该频道号的套餐的相关节目修改为新节目名称
	C. 当出现新的节目名称，并且相关联的频道号也为新的，那么套餐无变化
	D: 删除发送器传来需要删除的数据并删除相应套餐对应的频道数据
	E: 如果与频道相关的套餐里面的频道数据为空了 删除对应的套餐、销售记录、和广告数据。
	* @Param:
	* @return:
	* @Author: Mr.Zuo
	* @Date: 2018/7/6 **
	*/
	@Transactional(readOnly = false)
	public String saveChannelByNetwork(List<ChannelDTO> dtoList, AdNetwork network) throws ServiceException {
		List<String> channelNameList = Lists.newArrayList();
		for (int i = 0; i < dtoList.size(); i++) {
			String channelId = dtoList.get(i).getChannelName();
			if (StringUtils.isBlank(channelId)) {
				continue;
			}
			channelNameList.add(dtoList.get(i).getChannelName());
		}
		if (null == channelNameList || channelNameList.size() == 0) {
			return null;
		}
		List<AdChannel> addList = Lists.newArrayList();
		List<String> deletechannel = Lists.newArrayList();
		List<String> deletechannelIds = Lists.newArrayList();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("networkId", network.getId());
		List<AdChannel> networkList = channelDao.getAdChannelByNetWorkId(queryMap);//查询出该发送器下的所有频道
		Map<String, List<AdType>> adTypemap = getAdType(dtoList);

		Map<String, AdChannel> idMap = new HashMap<String, AdChannel>();
		Map<String, AdChannel> nameMap = new HashMap<String, AdChannel>();
		Map<String, String> deleteMap = new HashMap<String, String>();
		for (int i = 0; i < networkList.size(); i++) {
			idMap.put(networkList.get(i).getChannelId(), networkList.get(i));
			nameMap.put(networkList.get(i).getChannelName(), networkList.get(i));
		}
		for (int i = 0; i < dtoList.size(); i++) {
			AdChannel channel = null;
			String channelName = dtoList.get(i).getChannelName();
			String channelId = dtoList.get(i).getChannelId();
			if (dtoList.get(i).getDeleteFlag() != null && dtoList.get(i).getDeleteFlag().equals("0")) {
				if (nameMap.containsKey(channelName)) {//判断数据库是否存在该值
					channel = nameMap.get(channelName);
					channel.setChannelId(channelId);
					deleteMap.put(channelId,channelName);
				} else {
					if (idMap.containsKey(channelId)) {
						channel = idMap.get(channelId);
						channel.setChannelName(channelName);
						deleteMap.put(channelId,channelName);
					} else {
						channel = new AdChannel();
						channel.setId(IdGen.uuid());
						channel.setChannelId(channelId);
						channel.setChannelName(channelName);
					}
				}
				channel.setAdNetWork(network);
				channel.setChannelType(dtoList.get(i).getChannelType());
				channel.setServiceId(dtoList.get(i).getServiceId());
				channel.setServiceName(dtoList.get(i).getServiceName());
				channel.setIsMainChannel(dtoList.get(i).getIsMainChannel());
				channel.setIsVideoChannel(dtoList.get(i).getIsVideoChannel());
				channel.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
				if (null != adTypemap && adTypemap.containsKey(channelId)) {
					channel.setTypeList(adTypemap.get(channelId));
				}
				addList.add(channel);
			} else {
				deletechannel.add(channelId);
			}
		}


		thisDao.clear();

		String returnStr = null;
		List<AdChannel> dellist = Lists.newArrayList();
		Map<String,Object> updateFlagmap = new HashMap<String,Object>();
		updateFlagmap.put("networkId", network.getId());
		List<String> delList = Lists.newArrayList();
		for (Map.Entry<String,String> entry : deleteMap.entrySet()){
			updateFlagmap.put("channelId",entry.getKey());
			updateFlagmap.put("channelName",entry.getValue());
			List<AdChannel> l1 = channelDao.getAdChannels(updateFlagmap);
			if(null != l1 && l1.size()>0){
				for (AdChannel ch:l1) {
					delList.add(ch.getChannelId());
				}
			}
		}
		if(null != delList && delList.size()>0){
			Map<String,Object> deleteFlagmap = new HashMap<String,Object>();
			deleteFlagmap.put("networkId", network.getId());
			deleteFlagmap.put("deletechannelIds",delList);
			channelDao.deleteByChannelIds(deleteFlagmap);
		}
		thisDao.flush();
		thisDao.save(addList);


		if (deletechannel != null && deletechannel.size() > 0) {
			adNetworkService.deleteChannels(network.getId(), deletechannel);//删除频道并删除相应套餐关联的频道
			queryMap.put("channelIds", deletechannel);
			channelDao.deleteChannelList(queryMap);
			List<SelectChannelDTO> channelList = new ArrayList<SelectChannelDTO>();

			for (String s : deletechannel) {
				SelectChannelDTO sd = new SelectChannelDTO();
				sd.setChannelId(s);
				channelList.add(sd);
			}
			DeleteChannelDTO deletedto = new DeleteChannelDTO();
			deletedto.setIsDelete(String.valueOf(true));
			deletedto.setChannelList(channelList);
			returnStr = JsonMapper.toJsonString(deletedto);
		}
		channelDao.checkCombo();//如果与频道相关的套餐里面的频道数据为空了 删除对应的套餐、销售记录、和广告数据。
		return returnStr;

	}


	/**
	 * //根据上传的频道信息获取数据库已经存在的数据,并且删除多余的数据，比如原来存在cctv-1.现在上传的频道不存在该频道
	 * 
	 * @param dtoList
	 * @param network
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
	 * @param dtoList
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
	public List<NetWorkDTO> getChannelListByNetWorkAndTypeId(String networkId,String comboId,String typeId,String startTime,String endTime,String startDate,String endDate,String sendMode,String channelIds) throws Exception {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("comboId", comboId);
		queryMap.put("typeId", typeId);
		queryMap.put("networkId", networkId);
		List<AdChannelDTO> list = channelDao.getChannelByNetworkIdAndTypeId(queryMap);
		List<NetWorkDTO> allList = Lists.newArrayList();
		List<AdChannelDTO> allList1 = Lists.newArrayList();
		List<String> typeList = channelDao.getChannelType(networkId);
		if (null != list && list.size() > 0) {
			Map<String,Object> queryMap1 =getChannelByCombo(networkId,comboId, typeId, startTime, endTime,startDate,endDate,sendMode);
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
						List<AdChannelDTO> sellist = Lists.newArrayList();
						List<AdChannelDTO> dislist = Lists.newArrayList();
						List<AdChannelDTO> othlist = Lists.newArrayList();
						for (int j = 0; j < channelDtoList.size(); j++) {
							String id = channelDtoList.get(j).getChannelId();
                            if(null != channelIds && channelIds.contains(id)){
								sellist.add(channelDtoList.get(j));
							}else if(channelMap.containsKey(id)){
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
								dislist.add(channelDtoList.get(j));
							}else{
								othlist.add(channelDtoList.get(j));
							}
						}
						allList1.addAll(sellist);
						allList1.addAll(dislist);
						allList1.addAll(othlist);
						allList.get(i).setChannelList(allList1);
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
	public Map<String,Object> getChannelByCombo(String networkId,String comboId,String typeId,String startTime,String endTime,String startDate,String endDate,String sendMode) throws Exception {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("comboId", comboId);
		queryMap.put("typeId", typeId);
		queryMap.put("networkId", networkId);
		queryMap.put("sendMode", sendMode);
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

	public void deleteChannelList(Map<String,Object> map){
		channelDao.updateChannel(map);
	}

	List<AdChannel> getCanDeleteAdChannel(Map<String,Object> map){
		return channelDao.getCanDeleteAdChannel(map);
	}

}
