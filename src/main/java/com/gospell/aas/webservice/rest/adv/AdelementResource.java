package com.gospell.aas.webservice.rest.adv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.Constant;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.web.MediaTypes;
import com.gospell.aas.dto.push.PushAdelementDTO;
import com.gospell.aas.dto.push.PushAdvDTO;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.repository.hibernate.adv.AdNetworkDao;
import com.gospell.aas.repository.mybatis.adv.IAdelementDao;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;


@Path(value = "/advelement")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdelementResource {

	private IAdelementDao adelementDao = ApplicationContextHelper
			.getBean(IAdelementDao.class);
	private Logger logger = LoggerFactory.getLogger(AdelementResource.class);
	private AdelementService adelementService = ApplicationContextHelper
			.getBean(AdelementService.class);
	private AdNetworkDao networkDao = ApplicationContextHelper
			.getBean(AdNetworkDao.class);

	/**
	 * 通过广告发送器ID获取当前一周的广告
	 * 
	 * @param clientId
	 * @return
	 */
	@POST
	@Path(value = "/getAdvByComId")
	public RestResult getAdelement1(PushAdvDTO pushdto) {
		RestResult result = new RestResult(); 
		if(null == pushdto){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(pushdto.getClientId())){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
		 
		Date currentDate = new Date();
		Date afterWeekDate = DateUtils.getDateAfter(currentDate, Constant.AFTER_WEEK);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", currentDate);
		map.put("endDate", afterWeekDate);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		//map.put("status", Adelement.ADV_STATUS_PASS);
		List<Integer> statusList = Lists.newArrayList();
		statusList.add( Adelement.ADV_STATUS_PASS);
		statusList.add( Adelement.ADV_STATUS_SHOW);
		map.put("statusList", statusList);
		map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
		String networkId = pushdto.getClientId();
		map.put("networkId", networkId);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(pushdto.getComboId())) {
			map.put("comboId", pushdto.getComboId());
		}
		List<Adelement> networkList = null;
		List<Adelement> channelList = null;
		try {
			networkList = adelementDao.getAdelementNotChannel(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("广告发送器请求与频道无关的广告mybatis查询异常 ", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}
		try {
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			channelList = adelementDao.getAdelementChannel(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("广告发送器请求与频道有关的广告mybatis查询异常 ", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		 

		}
		boolean def = false;
		List<PushAdelementDTO> pushList = Lists.newArrayList();
		if (null != networkList && networkList.size() > 0) {
			for (int i = 0; i < networkList.size(); i++) {
				Adelement adelement = networkList.get(i);
				PushAdelementDTO dto = new PushAdelementDTO();
				if (null == adelement.getAdCombo()) {
					continue;
				}
				adelementService.setAdelementDto(adelement, dto, networkId, def);
				pushList.add(dto);
			}
		}
		if (null != channelList && channelList.size() > 0) {
			for (int i = 0; i < channelList.size(); i++) {
				Adelement adelement = channelList.get(i);
				PushAdelementDTO dto = new PushAdelementDTO();
				if (null == adelement.getAdCombo()) {
					continue;
				}
			 adelementService.setAdelementDto(adelement, dto,networkId, def);
			 adelementService.setPositionToAdelement(adelement, dto);
				pushList.add(dto);
			}
		}
		try {
			adelementService.updateIsPut(pushList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("广告发送器请求与频道有关的广告mybatis更新广告为投放中异常 ", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;

		}
		if(null == pushList || pushList.size() == 0){
			result.setStatus(Status.NOT_EXIST_ERROR.getCode());
			return result;
		}else{
			result.setStatus(Status.OK.getCode());
			result.setContent(JsonMapper.toJsonString(pushList));
			return result;
		}
		
		 
	}

	/**
	 * 通过广告发送器ID获取当前一周的默认开机画面广告（停播状态）
	 * 
	 * @param clientId
	 * @return
	 */
	@POST
	@Path(value = "/getDefaultAdvByComId")
	public RestResult getDefaultAdvByComId(PushAdvDTO pushdto) {
		RestResult result = new RestResult(); 
		if(null == pushdto){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(pushdto.getClientId())){
			result.setStatus(Status.BLANK_PARAMETER.getCode());
			return result;
		}
		 
		Date currentDate = new Date();
		Date afterWeekDate = DateUtils.getDateAfter(currentDate, Constant.AFTER_WEEK);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", currentDate);
		map.put("endDate", afterWeekDate);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<Integer> statusList = Lists.newArrayList();
		statusList.add( Adelement.ADV_STATUS_END);
		map.put("statusList", statusList);
		map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
		String networkId = pushdto.getClientId();
		map.put("networkId", networkId);
		List<String> typeIds = Lists.newArrayList();
		typeIds.add(AdType.Type_OPEN_IMGAE);
		typeIds.add(AdType.Type_BROCAST);
		map.put("typeIds", typeIds);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(pushdto.getComboId())) {
			map.put("comboId", pushdto.getComboId());
		}
		List<Adelement> networkList = null;
		try {
			networkList = adelementDao.getAdelementNotChannel(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("广告发送器请求与频道无关的广告mybatis查询异常 ", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}
		List<PushAdelementDTO> pushList = Lists.newArrayList();
		boolean def = true;
		if (null != networkList && networkList.size() > 0) {
			for (int i = 0; i < networkList.size(); i++) {
				Adelement adelement = networkList.get(i);
				PushAdelementDTO dto = new PushAdelementDTO();
				if (null == adelement.getAdCombo()) {
					continue;
				}
				AdNetwork work = networkDao.findByNetworkId(networkId);
				adelementService.setAdelementDto(adelement, dto,work.getId(),def);
				pushList.add(dto);
			}
		}
		if(null == pushList || pushList.size() == 0){
			result.setStatus(Status.NOT_EXIST_ERROR.getCode());
			return result;
		}else{
			result.setStatus(Status.OK.getCode());
			result.setContent(JsonMapper.toJsonString(pushList));
			return result;
		}
		
		 
	}

}
