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
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdComboPushFailRecord;
import com.gospell.aas.entity.adv.AdvNetwork;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboPushFailRecordDao;
import com.gospell.aas.service.adv.AdComboService;
import com.gospell.aas.webservice.netty.dto.AdComboDeleteDTO;
import com.gospell.aas.webservice.netty.dto.AdComboPutFailDTO;
import com.gospell.aas.webservice.netty.dto.ComboPutFailRecordDeleteDTO;
import com.gospell.aas.webservice.rest.RestResult;
import com.gospell.aas.webservice.rest.Result.Status;

@Path(value = "/comboPutFail")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class AdComboPutFailRecordResource {

	private IAdComboPushFailRecordDao thisDao = ApplicationContextHelper
			.getBean(IAdComboPushFailRecordDao.class);

	private IAdComboDao comboDao = ApplicationContextHelper
			.getBean(IAdComboDao.class);
	private Logger logger = LoggerFactory
			.getLogger(AdComboPutFailRecordResource.class);

	private AdComboService comboService = ApplicationContextHelper
			.getBean(AdComboService.class);

	/**
	 * 获取传入广告发送器当前具备的已运营的广告套餐有哪些
	 * 
	 * @param networkId
	 * @return
	 */

	@POST
	@Path(value = "/getDeleteCombo")
	public RestResult getAdvNetwork(AdComboPutFailDTO pushCombo) {
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
			map.put("status", AdvNetwork.DELETE_CLIENT_ADV_FAIL);

			List<AdComboPushFailRecord> list = thisDao.selectData(map);
			List<AdComboDeleteDTO> dtoList = Lists.newArrayList();
			if (null != list && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					AdComboPushFailRecord a = list.get(j);
					AdComboDeleteDTO dto = new AdComboDeleteDTO();
					dto.setComboId(a.getComboId());

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
			logger.error("根据广告发送器ID获取未删除的广告套餐发生异常", e);
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
	@Path(value = "/getPutFailCombo")
	public RestResult getPutFailAdvNetwork(AdComboPutFailDTO pushCombo) {
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
			map.put("isFlag", AdCombo.ADCOMOBO_NETWORK_ISFLAG);
			// map.put("isPut", AdCombo.ADCOMOBO_NO_PUT);
			List<AdCombo> list = comboDao.getPutFailNotChannelAdCombo(map);// 获取频道无关的套餐
			map.put("isFlag", AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			List<AdCombo> refer_list = comboDao.getPutFailChannelAdCombo(map);// 获取频道有关的套餐
			List<PushAdComboDTO> pushList = Lists.newArrayList();
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					AdCombo combo = list.get(i);
					PushAdComboDTO dto = new PushAdComboDTO();
					comboService.setAdComboDTO(combo, dto,networkId);
					pushList.add(dto);
				}
			}
			if (null != refer_list && refer_list.size() > 0) {
				for (int i = 0; i < refer_list.size(); i++) {
					AdCombo combo = refer_list.get(i);
					PushAdComboDTO dto = new PushAdComboDTO();
					comboService.setAdComboDTO(combo, dto,networkId);
					List<AdChannel> channelList = combo.getChannelList();
					if (null != channelList && channelList.size() > 0) {
						dto.setChannelList(channelList);
					} 
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
			logger.error("根据客户端ID获取推送失败的广告套餐发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}

	}

	@POST
	@Path(value = "/delFailCombo")
	public RestResult deleteAdvNetwork(ComboPutFailRecordDeleteDTO dto) {

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
			List<AdComboDeleteDTO> list = dto.getDtoList();
			if (null == list || list.size() == 0) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			List<String> comId_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				comId_list.add(list.get(i).getComboId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", networkId);
			map.put("list", comId_list);
			map.put("status", AdComboPushFailRecord.DELETE_CLIENT_ADCOMBO_FAIL);

			thisDao.deleteList(map);
			result.setStatus(Status.OK.getCode());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据广告发送器ID删除中间表的广告套餐发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}

	}

	@POST
	@Path(value = "/delPutFailCombo")
	public RestResult delPutFailCombo(ComboPutFailRecordDeleteDTO dto) {
 
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
			List<AdComboDeleteDTO> list = dto.getDtoList();
			if (null == list || list.size() == 0) {
				result.setStatus(Status.BLANK_PARAMETER.getCode());
				return result;
			}
			List<String> comId_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				comId_list.add(list.get(i).getComboId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", networkId);
			map.put("list", comId_list);
			map.put("status", AdComboPushFailRecord.PUSH_CLIENT_ADCOMBO_FAIL);

			thisDao.deleteList(map);
			result.setStatus(Status.OK.getCode());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据广告发送器ID删除中间表的广告套餐发生异常", e);
			result.setStatus(Status.INTERNAL_SERVER_ERROR.getCode());
			return result;
		}
	}

}
