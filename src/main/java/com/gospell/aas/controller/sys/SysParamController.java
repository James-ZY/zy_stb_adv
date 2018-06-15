package com.gospell.aas.controller.sys;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
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

import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.SysParamDTO;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.service.sys.SysParamService;
import com.gospell.aas.service.sys.SystemService;

@Controller
@RequestMapping(value = "/sys/param")
public class SysParamController extends BaseController {

	@Autowired
	private SysParamService thisService;
	@Autowired
	private SystemService systemService;
	
	 
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public SysParam get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new SysParam();
		}
	}

	@RequiresPermissions("sys:param:view")
	@RequestMapping(value = { "/list", "" })
	public String list(SysParam entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<SysParam> page = thisService.findAll(new Page<SysParam>(request,
				response), entity);
		 
		model.addAttribute("page", page);
		return "/sys/sysParamList";
	}

	@RequiresPermissions("sys:param:view")
	@RequestMapping(value = "/form")
	public String form(SysParam entity, Model model) {
		model.addAttribute("sysParam", entity);
		return "/sys/sysParamForm";
	}

	@RequiresPermissions("sys:param:edit")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(SysParam entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("control.parameter"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("paramType", entity.getParamType());
			map.put("paramKey", entity.getParamKey());
			SysParam param = thisService.getParam(map);
			if (null != param) {
				entity.setId(param.getId());
			}
			entity.setEnable(SysParam.ENABLE_YES);
			entity.setCanUpdate(entity.getParamType().startsWith("CU_")?SysParam.CANUPDATE_YES:SysParam.CANUPDATE_NO);
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存系统参数："+entity.getId()+"成功");
			return "redirect:/sys/param/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存系统参数失败，失败的时间是："+ DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/sys/param/?repage";
		}
	}

	@RequiresPermissions("sys:param:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(id, 0, getMessage("control.parameter"), id);
		try {
			 thisService.delete(id);
			 addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"成功");
			return "redirect:/sys/param/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"失败");
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/sys/param/?repage";
		}
	 
	}
		
	@ResponseBody
	@RequestMapping(value = "checkParamIsExist", method = RequestMethod.POST)
	public String checkParamIsExist(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		SysParam model = new SysParam();
		List<SysParam> list = thisService.getParamList(map);
		if(list!=null && list.size()>0){
			model = list.get(0);
		}
		return JsonMapper.toJsonString(model);

	}
	
	@ResponseBody
	@RequestMapping(value = "getParams", method = RequestMethod.POST)
	public String getParams(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<SysParam> list = thisService.getParamList(map);
		return JsonMapper.toJsonString(list);

	}
	
	@ResponseBody
	@RequestMapping(value = "getParamByType", method = RequestMethod.POST)
	public String getParamByType(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		String typeId = map.get("typeId").toString();
		SysParamDTO dto = new SysParamDTO();

		if(typeId.equals(AdType.Type_OPEN_IMGAE)){
			map.put("paramType", SysParam.BOOT_PICTURE_TIME_SET);
			SysParam param = thisService.getParam(map);
			dto.setBpTS(param.getParamValue());
			map.put("paramType", SysParam.BOOT_PICTURE_TOTAL_TIME);
			param = thisService.getParam(map);
			dto.setBpTT(param.getParamValue());
		}

		if(typeId.equals(AdType.TYPE_CORNER_ID)){
			map.put("paramType", SysParam.CORNER_PICTURE_TIME_SET);
			SysParam param = thisService.getParam(map);
			dto.setBpTS(param.getParamValue());
			map.put("paramType", SysParam.CORNER_PICTURE_TOTAL_TIME);
			param = thisService.getParam(map);
			dto.setBpTT(param.getParamValue());
		}

		if(typeId.equals(AdType.TYPE_CORNER_ID) || typeId.equals(AdType.TYPE_INSERT_SCREEN_ID) ||typeId.equals(AdType.TYPE_ROLL_ADV_ID)){
			map.put("paramType", SysParam.PICTURE_SHOW_TIME_SET);
			SysParam param = thisService.getParam(map);
			dto.setPsTs(param.getParamValue());
		}
		return JsonMapper.toJsonString(dto);
	}

	@ResponseBody
	@RequestMapping(value = "getFontParams", method = RequestMethod.POST)
	public String getFontParams(@RequestBody Map<String, Object> map,
								HttpServletRequest request, HttpServletResponse response) {
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		List<String> list = Lists.newArrayList();
		for (int i = 0; i < fontName.length; i++) {
			list.add(fontName[i]);
			System.out.println(fontName[i]);
		}
		return JsonMapper.toJsonString(list);

	}
		 
}
