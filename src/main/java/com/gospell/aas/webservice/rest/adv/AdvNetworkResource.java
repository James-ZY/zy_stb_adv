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
import com.gospell.aas.dto.adv.AdvDeleteDTO;
import com.gospell.aas.dto.adv.AdvDeleteInMiddle;
import com.gospell.aas.dto.adv.AdvNetworkDeleteDTO;
import com.gospell.aas.dto.push.AdvNetWorkDTO;
import com.gospell.aas.dto.push.PushAdelementDTO;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.adv.AdvNetwork;
import com.gospell.aas.repository.mybatis.adv.IAdelementDao;
import com.gospell.aas.repository.mybatis.adv.IAdvNetWorkDao;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;

@Path(value = "/advNetwork")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdvNetworkResource {

	private IAdvNetWorkDao thisDao = ApplicationContextHelper
			.getBean(IAdvNetWorkDao.class);
	private IAdelementDao adelementDao = ApplicationContextHelper
			.getBean(IAdelementDao.class);;
	private AdelementService adelementService = ApplicationContextHelper
			.getBean(AdelementService.class);;
	private Logger logger = LoggerFactory.getLogger(AdvNetworkResource.class);

	/**
	 * 获取当前广告发送器已经停播或者紧急停播的广告
	 * 
	 * @param networkId
	 * @return
	 */

	@POST
	@Path(value = "/getAdvNetwork")
	public RestResult getAdvNetwork(AdvDeleteInMiddle pushCombo) {
		RestResult result = new RestResult();
		try {

			if (null == pushCombo) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			String networkId = pushCombo.getClientId();
			if (org.apache.commons.lang3.StringUtils.isBlank(networkId)) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", networkId);
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("status", pushCombo.getStatus());

			List<AdvNetwork> list = thisDao.selectData(map);
			List<AdvNetWorkDTO> dtoList = Lists.newArrayList();
			if (null != list && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					AdvNetwork a = list.get(j);
					AdvNetWorkDTO dto = new AdvNetWorkDTO();
					dto.setAdvId(a.getAdvId());
					dto.setClientId(a.getClientId());
					dto.setId(a.getId());

					dtoList.add(dto);
				}

			}
			if (null != dtoList && dtoList.size() > 0) {
				result.setStatus(Status.OK.getCode());
				result.setContent(JsonMapper.toJsonString(dtoList));
			} else {
				result.setStatus(Status.NOT_EXIST_ERROR.getCode());
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据广告发送器ID获取删除的广告发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}

	}

	/**
	 * 获取主动推送失败的广告
	 * 
	 * @param pushCombo
	 * @return
	 */
	@POST
	@Path(value = "/getPutFailAdvNetwork")
	public RestResult getPutFailAdvNetwork(AdvDeleteInMiddle pushCombo) {
		RestResult result = new RestResult();
		try {

			if (null == pushCombo) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			String networkId = pushCombo.getClientId();
			if (org.apache.commons.lang3.StringUtils.isBlank(networkId)) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Integer status = pushCombo.getStatus();
			map.put("clientId", networkId);
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("status", status);
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			List<Adelement> channelList = adelementDao
					.getPutFailToClientChannel(map);
			map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
			List<Adelement> networkList = adelementDao
					.getPutFailToClientNotChannel(map);
			List<PushAdelementDTO> pushList = Lists.newArrayList();
			if (null != networkList && networkList.size() > 0) {
				for (int i = 0; i < networkList.size(); i++) {
					Adelement adelement = networkList.get(i);
					PushAdelementDTO dto = new PushAdelementDTO();
					if (null == adelement.getAdCombo()) {
						continue;
					}
					adelementService.setAdelementDto(adelement, dto,networkId,false);
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
					adelementService.setAdelementDto(adelement, dto,networkId,false);
					 adelementService.setPositionToAdelement(adelement, dto);
					pushList.add(dto);
				}
			}
			if (null == pushList || pushList.size() == 0) {
				result.setStatus(Status.NOT_EXIST_ERROR.getCode());
				return result;
			} else {
				result.setStatus(Status.OK.getCode());
				result.setContent(JsonMapper.toJsonString(pushList));
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据客户端ID获取推送失败的广告发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}

	}

 
	@POST
	@Path(value = "/delete")
	public RestResult deleteAdvNetwork(AdvNetworkDeleteDTO dto) {

		RestResult result = new RestResult();
		try {

			if (null == dto) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			String networkId = dto.getClientId();
			if (org.apache.commons.lang3.StringUtils.isBlank(networkId)) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			List<AdvDeleteDTO> list = dto.getDtoList();
			if (null == list || list.size() == 0) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			List<String> advId_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				advId_list.add(list.get(i).getAdId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", networkId);
			map.put("list", advId_list);
			map.put("status", dto.getStatus());

			thisDao.deleteList(map);
			result.setStatus(Status.OK.getCode());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据广告发送器ID获取广告套餐发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}

	}

 
}
