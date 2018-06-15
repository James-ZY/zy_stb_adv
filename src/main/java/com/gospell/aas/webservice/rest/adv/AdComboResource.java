package com.gospell.aas.webservice.rest.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import jersey.repackaged.com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.web.MediaTypes;
import com.gospell.aas.dto.push.PushAdComboDTO;
import com.gospell.aas.dto.push.PushCombo;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.service.adv.AdComboService;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;

@Path(value = "/adcombo")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdComboResource {

	private IAdComboDao adComboDao = ApplicationContextHelper.getBean(IAdComboDao.class);
	private Logger logger = LoggerFactory.getLogger(AdCombo.class);
	private AdComboService comboService = ApplicationContextHelper.getBean(AdComboService.class);
	
	/**
	 * 获取传入广告发送器当前具备的已运营的广告套餐有哪些
	 * @param networkId
	 * @return
	 */
	
	@POST
	@Path(value = "/getCombo")
	public RestResult getAdelement1(PushCombo pushCombo) {
		//System.out.println("进套餐接口了");
		RestResult result = new RestResult();
		try{
			 
		if(null == pushCombo){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
		String networkId = pushCombo.getClientId();
		if(org.apache.commons.lang3.StringUtils.isBlank(networkId)){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
	 
		 
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("networkId", networkId);
		 map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		 map.put("status", AdCombo.ADCOMOBO_ALREADY_STATUS);
		 map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
		// map.put("isPut", AdCombo.ADCOMOBO_NO_PUT);
		 List<AdCombo> list = adComboDao.getAdComboNotByNetworkId(map);//获取频道无关的套餐
		 map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
		 List<AdCombo> refer_list = adComboDao.getAdComboByNetworkId(map);//获取频道有关的套餐
		 map.put("delFlag", BaseEntity.DEL_FLAG_DELETE);
		// map.put("isPut", AdCombo.ADCOMOBO_YES_PUT);
		// List<AdCombo> delete_list = adComboDao.getAdComboByDelete(map);
		 List<PushAdComboDTO> pushList = Lists.newArrayList();
		 if(null != list && list.size() >0){
			 for (int i = 0; i < list.size(); i++) {
				AdCombo combo = list.get(i);
				PushAdComboDTO dto = new PushAdComboDTO();
				comboService.setAdComboDTO(combo, dto);
				 List<AdChannel> channelList = Lists.newArrayList();
				dto.setChannelList(channelList);
				pushList.add(dto);
			}
		 }
		 if(null != refer_list && refer_list.size() >0){
			 for (int i = 0; i < refer_list.size(); i++) {
				AdCombo combo = refer_list.get(i);
				PushAdComboDTO dto = new PushAdComboDTO();
				comboService.setAdComboDTO(combo, dto);
				 List<AdChannel> channelList = combo.getChannelList();
				if(null != channelList && channelList.size() >0){
					 dto.setChannelList(channelList);
				 }
				pushList.add(dto);
			}
		 }
//		 if(null != delete_list && delete_list.size() >0){
//			 for (int j = 0; j < delete_list.size(); j++) {
//				 AdCombo combo = delete_list.get(j);
//					PushAdComboDTO dto = new PushAdComboDTO();
//					setAdComboDTO(combo, dto);
//					pushList.add(dto);
//			}
//				
//		 }
		 comboService.updateIsPut(pushList);
		 if(null == pushList || pushList.size() == 0){
				result.setStatus(Status.NOT_EXIST_ERROR.getCode());
				return result;
			}else{
				result.setStatus(Status.OK.getCode());
				result.setContent(JsonMapper.toJsonString(pushList));
				return result;
			}
		 }catch(Exception e){
			 
			 logger.error("根据广告发送器ID获取广告套餐发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		 }
		
	}
	
	
	 
}
