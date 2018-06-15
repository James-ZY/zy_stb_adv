package com.gospell.aas.controller.adv;

import java.util.List;
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
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdRangeDTO;
import com.gospell.aas.entity.adv.AdRange;
import com.gospell.aas.service.adv.AdRangeService;

@Controller
@RequestMapping(value = "/adv/range")
public class AdRangeController extends BaseController {

	@Autowired
	private AdRangeService thisService;

	@ModelAttribute
	public AdRange get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdRange();
		}
	}

	@RequiresPermissions("sys:range:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdRange entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdRange> page = thisService.find(new Page<AdRange>(request, response), entity);
		model.addAttribute("page", page);
		return "/adrange/rangeList";
	}

	@RequiresPermissions("sys:range:view")
	@RequestMapping(value = "/form")
	public String form(AdRange entity, Model model) {
		model.addAttribute("adRange", entity);
		return "/adrange/rangeForm";
	}

	@RequiresPermissions("sys:range:edit")
	@RequestMapping(value = "/save")
	public String save(AdRange entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("range"), entity.getRangeName());
		try {
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存广告坐标范围："+entity.getRangeName()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/range/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存广告坐标范围："+entity.getRangeName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/range/?repage";
		}
	}

	@RequiresPermissions("sys:range:edit")
	@RequestMapping(value = "/update")
	public String update(AdRange entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("range"), entity.getRangeName());
		try {
			thisService.update(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"修改广告坐标范围："+entity.getRangeName()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/range/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"修改广告坐标范围："+entity.getRangeName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/range/?repage";
		}
	}

	@RequiresPermissions("sys:range:edit")
	@RequestMapping(value = "/start")
	public String start(AdRange entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "range.msg.start.fail");
			return "redirect:/adv/range/?repage";
		}
		String logInfo=getMessage("network.start.log",new Object[]{getMessage("range"),entity.getRangeName()});
		try {
			entity.setStatus(AdRange.RANGE_START_STAUTS);
			thisService.saveStatus(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"启用广告坐标范围名称："+entity.getRangeName()+"成功");
			addMessage(redirectAttributes, "range.msg.start.success");
			return "redirect:/adv/range/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"启用广告坐标范围名称："+entity.getRangeName()+"失败");
			addMessage(redirectAttributes, "range.msg.start.fail");
			return "redirect:/adv/range/?repage";
		}
	}

	@RequiresPermissions("sys:range:edit")
	@RequestMapping(value = "/end")
	public String end(AdRange entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "range.msg.end.fail");
			return "redirect:/adv/range/?repage";
		}
		String logInfo=getMessage("network.stop.log",new Object[]{getMessage("range"),entity.getRangeName()});
		try {
			entity.setStatus(AdRange.RANGE_END_STATUS);
			thisService.saveStatus(entity);
			addMessage(redirectAttributes, "range.msg.end.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"停用广告坐标范围名称："+entity.getRangeName()+"成功");
			return "redirect:/adv/range/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"停用广告坐标范围名称："+entity.getRangeName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "range.msg.end.fail");
			return "redirect:/adv/range/?repage";
		}
	}

	@RequiresPermissions("sys:range:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdRange entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("range"), entity.getRangeName());
		try {
			
			thisService.delete(entity.getId());
			addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除广告坐标范围名称："+entity.getRangeName()+"成功");
			return "redirect:/adv/range/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告坐标范围名称："+entity.getRangeName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/range/?repage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "find_range_type_id", method = RequestMethod.POST)
	public String isoriginalInCombo(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		Integer flag = (Integer) map.get("flag");
		List<AdRangeDTO> dto = thisService.getUseInAdRange(typeId, flag);
		return JsonMapper.toJsonString(dto);
	}


}
