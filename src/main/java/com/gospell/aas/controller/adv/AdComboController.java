package com.gospell.aas.controller.adv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gospell.aas.dto.adv.*;
import com.gospell.aas.service.adv.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.common.utils.sms.DateUtil;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdComboDistrict;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.sys.SysParamService;

@Controller
@RequestMapping(value = "/adv/combo")
public class AdComboController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AdComboController.class);
	@Autowired
	private AdComboService thisService;
	@Autowired
	private AdNetworkService networkService;

	@Autowired
	private AdOperatorsService operatorsService;

	@Autowired
	private AdChannelService channelService;

	@Autowired
	private AdSellService sellService;

	@Autowired
	private AdRangeService rangeService;

	@Autowired
	private AdTrackService trackService;

	@Autowired
	private SysParamService sysParamService;

	@ModelAttribute
	public AdCombo get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdCombo();
		}
	}

	@RequiresPermissions("sys:combo:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdCombo entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdCombo> page = thisService.find(new Page<AdCombo>(request,
				response), entity);
		model.addAttribute("page", page);
		if(null != entity.getValidStartTime()){
			model.addAttribute("validStartTime",DateUtils.formatDate(entity.getValidStartTime(), "yyyy-MM-dd"));
		}
		if(null != entity.getValidEndTime()){
			model.addAttribute("validEndTime",DateUtils.formatDate(entity.getValidEndTime(), "yyyy-MM-dd"));
		}

		return "/adcombo/comboList";
	}

	@RequiresPermissions("combo:statistics:operation:view")
	@RequestMapping(value = "/comboOperation")
	public String comboStatisticsOperation(AdCombo entity,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (entity.getAdType() == null) {
			model.addAttribute("page", new Page<>(request, response));
			return "/statistics/comboOperation";
		}
		Date startDate = entity.getValidStartTime();
		Date endDate = entity.getValidEndTime();
		Page<AdCombo> page = thisService.finsStatistic(new Page<AdCombo>(
				request, response), entity);
		String jsonStr = thisService.getSellJsonStr(page.getList(), startDate,
				endDate);

		model.addAttribute("startDate",
				DateUtils.formatDate(startDate, "yyyy-MM-dd"));
		model.addAttribute("endDate",
				DateUtils.formatDate(endDate, "yyyy-MM-dd"));
		model.addAttribute("page", page);
		model.addAttribute("json_list", jsonStr);
		return "/statistics/comboOperation";
	}

	@RequiresPermissions("combo:statistics:operation:view")
	@RequestMapping(value = "/comboOperationFrom")
	public String comboStatisticsOperationFrom(AdCombo entity,
			String startDate, String endDate, String sellDay,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {

			String data = thisService.getComboSellDetail(entity.getId(),
					entity.getComboName(), startDate, endDate, sellDay);
			model.addAttribute("detailData", data);
		} catch (Exception e) {
			model.addAttribute("detailData", 0);
			e.printStackTrace();
		}
		return "/statistics/comboOperationFrom";
	}

	@RequiresPermissions("sys:combo:view")
	@RequestMapping(value = "/form")
	public String form(AdCombo entity, Model model) {
		String id = entity.getId();

		if (StringUtils.isNotBlank(id)) {
			entity = thisService.get(entity.getId());			
		}
		entity.setTrackMode(1);
		model.addAttribute("adCombo", entity);

		String startDate = DateUtil.dateToStr(entity.getValidStartTime(),
				"yyyy-MM-dd");
		String endDate = DateUtil.dateToStr(entity.getValidEndTime(),
				"yyyy-MM-dd");
		if(null != entity.getIsFlag()){
			if(entity.getIsFlag().equals(AdCombo.ADCOMOBO_NETWORK_ISFLAG)){
				if(entity.getSendMode() == 2){
					List<AdNetwork> list = entity.getNetworkList();
					String netIds = "";
					String ops = "";
					for (AdNetwork adNetwork : list) {
						netIds += adNetwork.getId()+"-";
						ops += adNetwork.getAdOperators().getId()+"-";
					}
					model.addAttribute("netIds", netIds);
					model.addAttribute("ops", ops);
					
				}
			}else{
				if(entity.getSendMode() == 2){				
					List<AdChannel> list = entity.getChannelList();
					List<String> nets = Lists.newArrayList();
					String ops = "";
					for (AdChannel adChannel : list) {
						if(!nets.contains(adChannel.getAdNetWork().getAdOperators().getId())){
							ops += adChannel.getAdNetWork().getAdOperators().getId()+"-";
							nets.add(adChannel.getAdNetWork().getAdOperators().getId());							
						}
					}
					model.addAttribute("ops", ops);
				}else{
				}
			}
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startTime", entity.getStartTime());
		model.addAttribute("endTime", entity.getEndTime());
		model.addAttribute("operate", "form");
		model.addAttribute("comboId", entity.getId());
		if(entity!=null && entity.getAdType()!=null){
			String typeId = entity.getAdType().getTypeId();
			if(typeId.equals(AdType.TYPE_INSERT_SCREEN_ID) || typeId.equals(AdType.TYPE_ROLL_ADV_ID)){
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				queryMap.put("paramType", SysParam.ADELEMENT_TIME_SET);
				queryMap.put("enable", SysParam.ENABLE_YES);
				 List<AdNetwork> nets =  thisService.findNetWorkByCombo(entity);
				List<String> network_List = Lists.newArrayList();
				 for (AdNetwork adNetwork : nets) {
					 if(adNetwork!= null){
						 network_List.add(adNetwork.getNetworkId());						 
					 }
				}
				 if(network_List!=null &&network_List.size()>0){
					 queryMap.put("netWorkList", network_List);
					 SysParam param = sysParamService.getMinParam(queryMap);
					 if(param!=null){
						 model.addAttribute("paramValue", param.getParamValue());			
					 }else{
						 model.addAttribute("paramValue", 120);	
					 }					 
				 }else{
					 model.addAttribute("paramValue", 120);	
				 }
			}
		}	
		List<AdComboDistrict> adComboCategorys = entity.getAdComboCategorys();
		if(adComboCategorys != null &&adComboCategorys.size() > 0 ){
			String selArea = "";
			for (AdComboDistrict adComboCategory : adComboCategorys) {
				selArea += adComboCategory.getDistrict().getId()+"-";
			}
			if(selArea!=""){
				selArea = selArea.substring(0, selArea.lastIndexOf('-'));
			}
			model.addAttribute("selArea", selArea);
		}
		
		return "/adcombo/comboForm";
	}

	@RequiresPermissions("sys:combo:view")
	@RequestMapping(value = "/copy")
	public String copy(AdCombo entity, Model model) {
		entity.setId(null);
		entity.setStatus(AdCombo.ADCOMOBO_DENY_STATUS);
		model.addAttribute("adCombo", entity);
		List<AdNetwork> list = networkService.findAllByMabatis();

		model.addAttribute("networkList", list);
		model.addAttribute("startDate", "");
		model.addAttribute("endDate", "");
		String startDate = DateUtil.dateToStr(entity.getValidStartTime(),
				"yyyy-MM-dd");
		String endDate = DateUtil.dateToStr(entity.getValidEndTime(),
				"yyyy-MM-dd");
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startTime", entity.getStartTime());
		model.addAttribute("endTime", entity.getEndTime());
		model.addAttribute("operate", "copy");
		return "/adcombo/comboForm";
	}

	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "/save")
	public String save(AdCombo entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo = logService.getLogInfo(entity.getId(), 0,
				getMessage("combo"), entity.getComboName());
		try {
			if (!beanValidator(model, entity)) {
				return form(entity, model);
			}
			if (entity.getIsFlag().equals(AdCombo.ADCOMOBO_CHANNEL_ISFLAG)) {
				if (null == entity.getChannelList()
						|| entity.getChannelList().size() == 0) {
					model.addAttribute("message",
							getMessage("adcombo.channelList"));
					logService.save(request, logInfo, new Exception(
							getMessage("adcombo.channelList")));
					logger.info(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告套餐:" + entity.getComboName()
							+ "失败，失败原因:" + getMessage("adcombo.channelList"));
					entity.setStatus(entity.getOldStatus());
					return form(entity, model);
				}
			} else {
				if (null == entity.getNetworkList()
						|| entity.getNetworkList().size() == 0) {
					model.addAttribute("message",
							getMessage("adcombo.netWorkList"));
					logService.save(request, logInfo, new Exception(
							getMessage("adcombo.netWorkList")));
					logger.info(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告套餐:" + entity.getComboName()
							+ "失败，失败原因:" + getMessage("adcombo.netWorkList"));
					entity.setStatus(entity.getOldStatus());
					return form(entity, model);
				}
			}

			String id = entity.getId();

			if (StringUtils.isNotBlank(id)) {
				boolean b = thisService.isCanUpdate(entity.getId());// 表示改套餐不可以保存为其他状态
				if (!b) {
						addMessage(redirectAttributes, "adcombo.already.sell");
					logger.error(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告套餐:" + entity.getComboName()
							+ "失败,失败原因：" + getMessage("adcombo.already.sell"));
					thisService.clear();
					logService.save(request, logInfo, new Exception(
							getMessage("adcombo.already.sell")));
					return "redirect:/adv/combo/?repage";
				} else {
					thisService.clear();			
					if(thisService.checkIfAddCombo(entity)){					
						thisService.save(entity);
						logger.info(UserUtils.getUser().getLoginName()
								+ "添加或者修改广告套餐:" + entity.getComboName() + "成功");
						logService.save(request, logInfo, null);
						addMessage(redirectAttributes, "msg.save.success");						
					}else{
						logger.info(UserUtils.getUser().getLoginName()
								+ "添加或者修改广告套餐:" + entity.getComboName() + "失败");
						logService.save(request, logInfo, null);
						addMessage(redirectAttributes, "adcombo.already.used");	
						return "redirect:/adv/combo/?repage";
					}
				}
			} else {
				entity.setId(IdGen.uuid());
				if(thisService.checkIfAddCombo(entity)){	
					thisService.save(entity);
					logger.info(UserUtils.getUser().getLoginName() + "添加或者修改广告套餐:"
							+ entity.getComboName() + "成功");
					logService.save(request, logInfo, null);
					addMessage(redirectAttributes, "msg.save.success");					
				}else{
					logger.info(UserUtils.getUser().getLoginName()
							+ "添加或者修改广告套餐:" + entity.getComboName() + "失败");
					logService.save(request, logInfo, null);
					addMessage(redirectAttributes, "adcombo.already.used");	
					return "redirect:/adv/combo/?repage";
				}
			}

			thisService.pushAdcomboToClient(entity);
			return "redirect:/adv/combo/?repage";

		} catch (Exception e) {

			logger.error(UserUtils.getUser().getLoginName() + "添加或者修改广告套餐:"
					+ entity.getComboName() + "失败", e.getMessage());
			logService.save(request, logInfo, e);
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/combo/?repage";
		}
	}

	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		AdCombo combo = get(id);
		String logInfo = logService.getLogInfo(id, 0, getMessage("combo"),
				combo.getComboName());
		try {

			Integer status = combo.getStatus();
			if (AdCombo.ADCOMOBO_ALREADY_STATUS.equals(status)) {
				addMessage(redirectAttributes, "combo.already.delete");
				logger.error(UserUtils.getUser().getLoginName() + "删除广告套餐"
						+ combo.getComboName() + "失败，失败原因："
						+ getMessage("combo.already.delete"));
				thisService.clear();
			} else {
				boolean s = thisService.isCanDelete(id);
				if (s) {
					combo.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
					thisService.save(combo);
					logger.info(UserUtils.getUser().getLoginName() + "删除广告套餐:"
							+ combo.getComboName() + "成功");
					logService.save(request, logInfo, null);
					addMessage(redirectAttributes, "msg.del.success");
				} else {
					addMessage(redirectAttributes, "combo.delete.not");
					logger.error(UserUtils.getUser().getLoginName() + "删除广告套餐"
							+ combo.getComboName() + "失败，失败原因："
							+ getMessage("combo.delete.not"));
					thisService.clear();
				}
			}
			return "redirect:/adv/combo/?repage";

		} catch (Exception e) {
			logger.error(
					UserUtils.getUser().getLoginName() + "删除广告套餐:"
							+ combo.getComboName() + "失败", e.getMessage());
			logService.save(request, logInfo, e);
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/combo/?repage";
		}
	}

	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "checkComboName")
	public String checkcomboId(HttpServletRequest request, String oldComboName,
			String comboName, String operate) {
		try {
			request.setCharacterEncoding("utf-8");
			if (operate != null && operate.trim().equals("copy")) {
				if (comboName != null
						&& thisService.findAdComboByComboName(comboName.trim())
								.size() == 0) {
					return "true";
				}
			} else {
				if (comboName != null && comboName.trim().equals(oldComboName)) {
					return "true";
				} else if (comboName != null
						&& thisService.findAdComboByComboName(comboName.trim())
								.size() == 0) {
					return "true";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "checkTime")
	public String checkTime(String playStartHour, String playEndHour,
			String playStartMinute, String playEndMinute) {
		String flag = "false";
		try {
			int startHour = Integer.parseInt(playStartHour);
			int startMinute = Integer.parseInt(playStartMinute);
			int endHour = Integer.parseInt(playStartHour);
			int endMinute = Integer.parseInt(playStartMinute);
			if (endHour == startHour) {
				if (endMinute > startMinute) {
					flag = "true";
				}
			} else if (endHour > startHour) {
				flag = "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "find_network", method = RequestMethod.POST)
	public String findOperators() {
		List<AdOperatorsDTO> list = operatorsService.getAdOperatorsDTO();
		return JsonMapper.toJsonString(list);
	}

	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "find_channel_type", method = RequestMethod.POST)
	public String findChannelType(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String networkId = (String) map.get("networkId");
		AdChannelTypeDTO dto = channelService.getChannelTypeList(networkId);
		return JsonMapper.toJsonString(dto);

	}

	/**
	 * 套餐添加页面，根据参数获取频道信息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "find_channel", method = RequestMethod.POST)
	public String findChannel(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String networkId = (String) map.get("networkId");
		String typeId = (String) map.get("typeId");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String endDate = (String) map.get("endDate");
		String startDate = (String) map.get("startDate");
		String sendMode = (String) map.get("sendMode");
		List<NetWorkDTO> dtoList = null;
		try {

			dtoList = channelService.getChannelListByNetWorkAndTypeId(
					networkId, typeId, startTime, endTime, startDate, endDate,sendMode);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dtoList);
	}

	@ResponseBody
	@RequiresPermissions("sys:combo:edit")
	@RequestMapping(value = "find_networkby_comboId", method = RequestMethod.POST)
	public String findNetworkByComboId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		List<SelectNetworkDTO> dtoList = thisService.findSelectChByComboId(map);
		return JsonMapper.toJsonString(dtoList);
	}

	@ResponseBody
	@RequiresPermissions("sys:sell:edit")
	@RequestMapping(value = "chekSellTime", method = RequestMethod.POST)
	public String check(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {

		String comboId = (String) map.get("comboId");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Boolean b = false;
		try {
			if (StringUtils.isBlank(startDate)) {
				return "noStart";
			}
			if (StringUtils.isBlank(endDate)) {
				return "noEnd";
			}
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
			if (start.after(end)) {
				return "date";
			}
			b = sellService.checkTime(comboId, start, end);
		} catch (ParseException e) {
			e.printStackTrace();
			return "exception";

		}

		return b.toString();
	}

	/**
	 * 通过广告商ID和广告iD查询套餐
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_combo_by_advId", method = RequestMethod.POST)
	public String getComboByAdvId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String advId = (String) map.get("advId");
		String id = (String) map.get("id");
		String status = (String) map.get("status");
		Integer adv_status = Adelement.ADV_STATUS_WAIT;
		if (StringUtils.isNotBlank(status))
			adv_status = Integer.parseInt(status);
		if (StringUtils.isBlank(advId)) {
			User user = UserUtils.getUser();
			if (user.getAdvertiser() != null
					&& StringUtils.isNotBlank(user.getAdvertiser().getId())) {
				advId = user.getAdvertiser().getId();
			} else {
				return "";
			}
		}
		List<AdComboByUserDTO> dtoList = null;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		dtoList = thisService.getComboByAdvId(id, adv_status, dateStr,
				AdCombo.ADCOMOBO_ALREADY_STATUS, advId);

		return JsonMapper.toJsonString(dtoList);
	}

	/**
	 * 通过广告商ID和广告iD查询套餐
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_range_by_adTypeId", method = RequestMethod.POST)
	public String getRangeByAdvTypeId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String adTypeId = (String) map.get("adTypeId");
		String flag = (String) map.get("flag");
		if (StringUtils.isBlank(adTypeId)) {
			return "";
		}
		Integer flag1 = null;
		if(StringUtils.isNotBlank(flag)){
			flag1 = Integer.parseInt(flag);
		}
		List<AdRangeDTO> rangeList = rangeService.getUseInAdRange(adTypeId,
				flag1);
		return JsonMapper.toJsonString(rangeList);
	}

	/**
	 * 通过广告类型获取轨迹
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_track_by_adTypeId", method = RequestMethod.POST)
	public String getTrackByAdvTypeId(@RequestBody Map<String, Object> map,
									  HttpServletRequest request, HttpServletResponse response) {
		String adTypeId = (String) map.get("adTypeId");
		String flag = (String) map.get("flag");
		if (StringUtils.isBlank(adTypeId)) {
			return "";
		}
		Integer flag1 = null;
		if(StringUtils.isNotBlank(flag)){
			flag1 = Integer.parseInt(flag);
		}
		List<AdTrackDTO> trackList = trackService.getUseInAdTrack(adTypeId,
				flag1);
		return JsonMapper.toJsonString(trackList);
	}

	/**
	 * 套餐添加页面获取广告发送器
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_network_by_typeId", method = RequestMethod.POST)
	public String findNetWorkByTypeId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		String chlidType = (String) map.get("chlidType");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String sendMode = (String) map.get("sendMode");
		String advertiserId = (String) map.get("advertiserId");
		List<SelectAdNetworkDTO> dtoList = null;
		try {
			dtoList = networkService.findNetWorkByTypeAndCombo(typeId,
					chlidType, startDate, endDate,sendMode, advertiserId);
		} catch (Exception e) {
			logger.error("套餐添加或者修改的时候获取网络发送器失败", e);
			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dtoList);
	}

	@ResponseBody
	@RequestMapping(value = "find_network_all", method = RequestMethod.POST)
	public String findNetWorkByTypeId(HttpServletRequest request,
			HttpServletResponse response) {

		List<SelectAdNetworkDTO> dtoList = null;
		try {
			List<AdNetwork> list = networkService.findAllByMabatis();
			if (null != list && list.size() > 0) {
				dtoList = Lists.newArrayList();
				for (int i = 0; i < list.size(); i++) {
					SelectAdNetworkDTO d = new SelectAdNetworkDTO();
					d.setId(list.get(i).getId());
					d.setNetworkName(list.get(i).getNetworkName());
					d.setInvalid(true);
					dtoList.add(d);
				}
			}
		} catch (Exception e) {
			logger.error("获取全部网络发送器失败", e);
			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dtoList);
	}

	@ResponseBody
	@RequestMapping(value = "find_adcombo_by_id", method = RequestMethod.POST)
	public String findAdComboById(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		AdSellDTO dto = thisService.getAdSellDTO(id);
		return JsonMapper.toJsonString(dto);
	}

	@ResponseBody
	@RequestMapping(value = "find_adcombo_is_change", method = RequestMethod.POST)
	public String isoriginalInCombo(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		AdSellDTO dto = thisService.getAdSellDTO(id);
		return JsonMapper.toJsonString(dto);
	}

	/**
	 * 销售记录添加的时候根据用户选择的广告类型和广告发送器查询有效的套餐（频道相关的需要大于当前时间）
	 * 
	 * @param queryDto
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_combo_by_type_net", method = RequestMethod.POST)
	public String findComboByTypeAndNEtwork(
			@RequestBody AdComboCanSellQueryDTO queryDto,
			HttpServletRequest request, HttpServletResponse response) {
		List<AdcomboCanSellDTO> list = null;
		try {
			list = thisService.getCanSellAdcombo(queryDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JsonMapper.toJsonString(list);
	}

	/**
	 * 根据选择的套餐获取相应的频道发送器信息
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findNetWorkByCombo", method = RequestMethod.POST)
	public String findNetWorkByCombo(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		List<AdNetwork> list = null;
		String id = (String) map.get("id");
		AdCombo combo = thisService.get(id);
		try {
			list = thisService.findNetWorkByCombo(combo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JsonMapper.toJsonString(list);
	}

	@RequiresPermissions("sys:combo:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(AdCombo entity, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String name = getMessage("combo.data.export");
			String fileName = name + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<AdCombo> page = thisService.find(new Page<AdCombo>(request,
					response, -1), entity);
			new ExportExcel(name, AdCombo.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/adv/advertiser/?repage";
	}

	/**
	 * 判断套餐是否冲突
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkComboIsConflict", method = RequestMethod.POST)
	public String checkComboIsConflict(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		String networkList = (String) map.get("networkIds");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String sendMode = (String) map.get("sendMode");
		String districts = (String) map.get("districts");
		ComboCountDTO dto = null;
		try {
			dto = thisService.checkComboIsConflict(typeId, networkList,startDate,endDate,sendMode,districts);
		} catch (Exception e) {
			logger.error("判断套餐是否冲突失败", e);
			e.printStackTrace();
		}
		return JsonMapper.toJsonString(dto);
	}

	/**
	 * 获取参数设置
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTimeSet", method = RequestMethod.POST)
	public String getTimeSet(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String networkList = (String) map.get("networkIds");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("paramType", SysParam.ADELEMENT_TIME_SET);
		queryMap.put("enable", SysParam.ENABLE_YES);
		List<String> network_List = Lists.newArrayList();
		String[] ids = networkList.split(",");
		for (String string : ids) {
			AdNetwork network = networkService.get(string);
			network_List.add(network.getNetworkId());
		}
		queryMap.put("netWorkList", network_List);
		SysParam param = sysParamService.getMinParam(queryMap);
		return JsonMapper.toJsonString(param);
	}
	/**
	 * 获取套餐详情
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getComboInfo", method = RequestMethod.POST)
	public String getComboInfo(@RequestBody Map<String, Object> map,
							   HttpServletRequest request, HttpServletResponse response){
		String id = (String) map.get("id");
		AdCombo combo = thisService.get(id);
		AdcomboInfoDTO dto = new AdcomboInfoDTO();
		if(null != combo){
			dto.setId(combo.getId());
			dto.setStartTime(combo.getStartTime());
			dto.setEndTime(combo.getEndTime());
			dto.setShowTime(combo.getShowTime());
			dto.setIntervalTime(combo.getIntervalTime());
			dto.setShowCount(combo.getShowCount());
			dto.setPictureInterval(combo.getPictureInterval());
			dto.setPictureTimes(combo.getPictureTimes());
		}
		return JsonMapper.toJsonString(dto);
	}
}
