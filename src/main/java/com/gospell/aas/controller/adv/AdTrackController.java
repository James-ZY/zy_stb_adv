package com.gospell.aas.controller.adv;

import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdTrackDTO;
import com.gospell.aas.entity.adv.AdTrack;
import com.gospell.aas.service.adv.AdTrackService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/adv/track")
public class AdTrackController extends BaseController {

	@Autowired
	private AdTrackService thisService;

	@ModelAttribute
	public AdTrack get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdTrack();
		}
	}

	@RequiresPermissions("sys:track:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdTrack entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdTrack> page = thisService.find(new Page<AdTrack>(request, response), entity);
		model.addAttribute("page", page);
		return "/adtrack/trackList";
	}

	@RequiresPermissions("sys:track:view")
	@RequestMapping(value = "/form")
	public String form(AdTrack entity, Model model) {
		model.addAttribute("adTrack", entity);
		return "/adtrack/trackForm";
	}

	@RequiresPermissions("sys:track:edit")
	@RequestMapping(value = "/save")
	public String save(AdTrack entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("track"), entity.getTrackName());
		try {
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存广告轨迹范围："+entity.getTrackName()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/track/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存广告轨迹范围："+entity.getTrackName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/track/?repage";
		}
	}

	@RequiresPermissions("sys:track:edit")
	@RequestMapping(value = "/update")
	public String update(AdTrack entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("track"), entity.getTrackName());
		try {
			thisService.update(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"修改广告轨迹范围："+entity.getTrackName()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/track/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"修改广告轨迹范围："+entity.getTrackName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/track/?repage";
		}
	}

	@RequiresPermissions("sys:track:edit")
	@RequestMapping(value = "/start")
	public String start(AdTrack entity, HttpServletRequest request, Model model,
                        RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "track.msg.start.fail");
			return "redirect:/adv/track/?repage";
		}
		String logInfo=getMessage("network.start.log",new Object[]{getMessage("track"),entity.getTrackName()});
		try {
			entity.setStatus(AdTrack.TRACK_START_STAUTS);
			thisService.saveStatus(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"启用广告轨迹范围名称："+entity.getTrackName()+"成功");
			addMessage(redirectAttributes, "track.msg.start.success");
			return "redirect:/adv/track/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"启用广告轨迹范围名称："+entity.getTrackName()+"失败");
			addMessage(redirectAttributes, "track.msg.start.fail");
			return "redirect:/adv/track/?repage";
		}
	}

	@RequiresPermissions("sys:track:edit")
	@RequestMapping(value = "/end")
	public String end(AdTrack entity, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "track.msg.end.fail");
			return "redirect:/adv/track/?repage";
		}
		String logInfo=getMessage("network.stop.log",new Object[]{getMessage("track"),entity.getTrackName()});
		try {
			entity.setStatus(AdTrack.TRACK_END_STATUS);
			thisService.saveStatus(entity);
			addMessage(redirectAttributes, "track.msg.end.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"停用广告轨迹范围名称："+entity.getTrackName()+"成功");
			return "redirect:/adv/track/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"停用广告轨迹范围名称："+entity.getTrackName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "track.msg.end.fail");
			return "redirect:/adv/track/?repage";
		}
	}

	@RequiresPermissions("sys:track:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdTrack entity, HttpServletRequest request, Model model,
                         RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("track"), entity.getTrackName());
		try {
			
			thisService.delete(entity.getId());
			addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除广告轨迹范围名称："+entity.getTrackName()+"成功");
			return "redirect:/adv/track/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告轨迹范围名称："+entity.getTrackName()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/track/?repage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "find_track_type_id", method = RequestMethod.POST)
	public String isoriginalInCombo(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String typeId = (String) map.get("typeId");
		Integer flag = (Integer) map.get("flag");
		List<AdTrackDTO> dto = thisService.getUseInAdTrack(typeId, flag);
		return JsonMapper.toJsonString(dto);
	}


}
