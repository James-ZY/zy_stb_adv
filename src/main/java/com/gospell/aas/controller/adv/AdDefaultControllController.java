package com.gospell.aas.controller.adv;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdDefaultControll;
import com.gospell.aas.service.adv.AdDefaultControllService;

@Controller
@RequestMapping(value = "/adv/defaultControl")
public class AdDefaultControllController extends BaseController {

	@Autowired
	private AdDefaultControllService thisService;

	@ModelAttribute
	public AdDefaultControll get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdDefaultControll();
		}
	}

	@RequiresPermissions("adv:adSet:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdDefaultControll entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdDefaultControll> page = thisService.find(new Page<AdDefaultControll>(request,
				response), entity);
		model.addAttribute("page", page);
		if (AdvertiserUtils.checkIdAdv()) {
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}
		return "/adcontrol/defaultControlList";
	}

	@RequiresPermissions("adv:adSet:view")
	@RequestMapping(value = "/form")
	public String form(AdDefaultControll entity, Model model,
			RedirectAttributes redirectAttributes) {

		if (AdvertiserUtils.checkIdAdv()) {
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}

		model.addAttribute("adDefaultControll", entity);
		entity.setPath(entity.getFilePath() + ",");
		entity.setVedioImagePath(entity.getFileImagePath() + ",");
		return "/adcontrol/defaultControlForm";
	}

	@RequiresPermissions("adv:adSet:edit")
	@RequestMapping(value = "/save")
	public String save(AdDefaultControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model, redirectAttributes);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.control"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/defaultControl/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/defaultControl/?repage";
		}
	}

	@RequiresPermissions("adv:adSet:delete")
	@RequestMapping(value = "/delete")
	public String delete(AdDefaultControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("adv.control"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			Boolean b = thisService.isCanDelete(entity.getId());
			if (b) {
				thisService.delete(entity);
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除广告素材Id："+entity.getId()+"成功");
				addMessage(redirectAttributes, "msg.del.success");
			} else {
				addMessage(redirectAttributes, "defaultControll.delete.not");
				logService.save(request, logInfo, new Exception(getMessage("defaultControll.delete.not")));
				logger.error(UserUtils.getUser().getLoginName()+"删除广告素材Id："+entity.getId()+"失败，失败原因："+getMessage("defaultControll.delete.not"));
			}

			return "redirect:/adv/defaultControl/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告素材Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/defaultControl/?repage";
		}
	}
	
	@ResponseBody
	@RequiresPermissions("adv:adSet:edit")
	@RequestMapping(value = "checkIsRepeat", method = RequestMethod.POST)
	public String checkIsRepeat(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		AdDefaultControll model = thisService.checkIsRepeat(map);
		return JsonMapper.toJsonString(model);
	}

}
