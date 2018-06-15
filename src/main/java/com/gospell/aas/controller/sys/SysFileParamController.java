package com.gospell.aas.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.SysFileParam;
import com.gospell.aas.service.sys.SysFileParamService;
import com.gospell.aas.service.sys.SystemService;

@Controller
@RequestMapping(value = "/sys/fileParam")
public class SysFileParamController extends BaseController {

	@Autowired
	private SysFileParamService thisService;
	@Autowired
	private SystemService systemService;
	
	 
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public SysFileParam get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new SysFileParam();
		}
	}

	@RequiresPermissions("sys:fileParam:view")
	@RequestMapping(value = { "/list", "" })
	public String list(SysFileParam entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<SysFileParam> page = thisService.findAll(new Page<SysFileParam>(request,
				response), entity);
		 
		model.addAttribute("page", page);
		return "/sys/sysFileParamList";
	}

	@RequiresPermissions("sys:fileParam:view")
	@RequestMapping(value = "/form")
	public String form(SysFileParam entity, Model model) {
		model.addAttribute("SysFileParam", entity);
		model.addAttribute("imageformat", JsonMapper.toJsonString(DictUtils.getDictList("adv_resource_image_format")));
		model.addAttribute("vedioFormat", JsonMapper.toJsonString(DictUtils.getDictList("adv_resource_vedio_format")));
		return "/sys/sysFileParamForm";
	}

	@RequiresPermissions("sys:fileParam:edit")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(SysFileParam entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("control.parameter"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存素材参数："+entity.getId()+"成功");
			return "redirect:/sys/fileParam/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存素材参数失败，失败的时间是："+ DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/sys/fileParam/?repage";
		}
	}

	@RequiresPermissions("sys:fileParam:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(id, 0, getMessage("control.parameter"), id);
		try {
			 thisService.delete(id);
			 addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"成功");
			return "redirect:/sys/fileParam/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"失败");
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/sys/fileParam/?repage";
		}
	 
	}
		
	@ResponseBody
	@RequestMapping(value = "checkParamIsExist", method = RequestMethod.POST)
	public String checkParamIsExist(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		SysFileParam model = new SysFileParam();
		List<SysFileParam> list = thisService.getParamList(map);
		if(list!=null && list.size()>0){
			model = list.get(0);
			model.setSpId(model.getId());
		}
		return JsonMapper.toJsonString(model);

	}
	
	@ResponseBody
	@RequestMapping(value = "getParams", method = RequestMethod.POST)
	public String getParams(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<SysFileParam> list = thisService.getParamList(map);
		return JsonMapper.toJsonString(list);

	}
		 
}
