package com.gospell.aas.controller.adv;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.FileUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.common.utils.adv.rollimage.ImageDTO;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdControll;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Advertiser;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.adv.AdControllService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = "/adv/control")
public class AdControllController extends BaseController {

	@Autowired
	private AdControllService thisService;

	@ModelAttribute
	public AdControll get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdControll();
		}
	}

	@RequiresPermissions("adv:material:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdControll entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdControll> page = thisService.find(new Page<AdControll>(request,
				response), entity);
		model.addAttribute("page", page);
		if (AdvertiserUtils.checkIdAdv()) {
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}
		return "/adcontrol/controlList";
	}

	@RequiresPermissions("adv:material:view")
	@RequestMapping(value = "/audit")
	public String audit(AdControll entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdControll> page = thisService.findAudit(new Page<AdControll>(
				request, response), entity);
		model.addAttribute("page", page);
		return "/audit/auditResourceList";
	}

	@RequiresPermissions("adv:material:view")
	@RequestMapping(value = "/form")
	public String form(AdControll entity, Model model,
			RedirectAttributes redirectAttributes) {

		if (AdvertiserUtils.checkIdAdv()) {
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}

		model.addAttribute("adControll", entity);
		entity.setPath(entity.getFilePath() + ",");
		entity.setVedioImagePath(entity.getFileImagePath() + ",");
		return "/adcontrol/controlForm";
	}

	@RequiresPermissions("adv:material:view")
	@RequestMapping(value = "/formLayer")
	public String formLayer(AdControll entity, Model model,
					   RedirectAttributes redirectAttributes,
                            String adTypeId,String advertiserId,Integer flag) {

		if (AdvertiserUtils.checkIdAdv()) {
			model.addAttribute("isNotAdv", false);
		} else {
			model.addAttribute("isNotAdv", true);
		}
        AdType adType = new AdType();
        adType.setId(adTypeId);
        entity.setAdType(adType);
        Advertiser advertiser = new Advertiser();
        advertiser.setId(advertiserId);
        entity.setAdvertiser(advertiser);
        entity.setFlag(flag);
		model.addAttribute("adControll", entity);
		entity.setPath(entity.getFilePath() + ",");
		entity.setVedioImagePath(entity.getFileImagePath() + ",");
		return "/adcontrol/controlFormLayer";
	}

	@RequiresPermissions("adv:material:view")
	@RequestMapping(value = "/auditform")
	public String auditform(AdControll entity, Model model) {
		model.addAttribute("adControll", entity);
		return "/audit/auditResourceFrom";
	}

	@RequiresPermissions("adv:material:edit")
	@RequestMapping(value = "/save")
	public String save(AdControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		
		if (entity.getAdvertiser() == null
				|| StringUtils.isBlank(entity.getAdvertiser().getId())) {
			User user = UserUtils.getUser();
			if (user.getAdvertiser() != null) {
				Advertiser advertiser = user.getAdvertiser();
				entity.setAdvertiser(advertiser);
			}
		}

		if (!beanValidator(model, entity)) {
			return form(entity, model, redirectAttributes);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.control"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			int count = thisService.checkIsUpdate(entity.getId());
			if(count >0){
				addMessage(redirectAttributes, "adcontrol.not.update");
				logService.save(request, logInfo, new Exception(getMessage("adcontrol.not.update")));
				logger.error(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"失败，失败原因:"+getMessage("adcontrol.not.update"));
				return "redirect:/adv/control/?repage";
			}
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/control/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/control/?repage";
		}
	}

	@RequiresPermissions("adv:material:edit")
	@RequestMapping(value = "/saveNoRedirect")
	public String saveNoRedirect(AdControll entity, HttpServletRequest request,
					   Model model, RedirectAttributes redirectAttributes) {

		if (entity.getAdvertiser() == null
				|| StringUtils.isBlank(entity.getAdvertiser().getId())) {
			User user = UserUtils.getUser();
			if (user.getAdvertiser() != null) {
				Advertiser advertiser = user.getAdvertiser();
				entity.setAdvertiser(advertiser);
			}
		}

		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.control"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			int count = thisService.checkIsUpdate(entity.getId());
			if(count >0){
				addMessage(redirectAttributes, "adcontrol.not.update");
				logService.save(request, logInfo, new Exception(getMessage("adcontrol.not.update")));
				logger.error(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"失败，失败原因:"+getMessage("adcontrol.not.update"));
				return "redirect:/adv/control/formLayer?adTypeId="+entity.getAdType().getId()+"&advertiserId="+entity.getAdvertiser().getId()+"&flag="+entity.getFlag();
			}
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/control/formLayer?adTypeId="+entity.getAdType().getId()+"&advertiserId="+entity.getAdvertiser().getId()+"&flag="+entity.getFlag();
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告素材Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/control/formLayer?adTypeId="+entity.getAdType().getId()+"&advertiserId="+entity.getAdvertiser().getId()+"&flag="+entity.getFlag();
		}
	}

	@RequiresPermissions("adv:material:edit")
	@RequestMapping(value = "/saveAudit")
	public String saveAudit(AdControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model, redirectAttributes);
		}
		try {
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/control/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/control/?repage";
		}
	}

	@RequiresPermissions("adv:material:claim")
	@RequestMapping(value = "/claim")
	public String claim(AdControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			addMessage(redirectAttributes, "claim.fail");
			return "redirect:/adv/control/?repage";
		}
		try {
			if (AdControll.STATUS_WAIT == entity.getStatus()) {
				entity.setStatus(AdControll.STATUS_CLAIM);
				thisService.save(entity);
			}

			addMessage(redirectAttributes, "claim.success");
			return "redirect:/adv/control/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes, "claim.fail");
			return "redirect:/adv/control/?repage";
		}
	}

	@RequiresPermissions("adv:material:delete")
	@RequestMapping(value = "/delete")
	public String delete(AdControll entity, HttpServletRequest request,
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
				addMessage(redirectAttributes, "controll.delete.not");
				logService.save(request, logInfo, new Exception(getMessage("controll.delete.not")));
				logger.error(UserUtils.getUser().getLoginName()+"删除广告素材Id："+entity.getId()+"失败，失败原因："+getMessage("controll.delete.not"));
			}

			return "redirect:/adv/control/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告素材Id："+entity.getId()+"失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/control/?repage";
		}
	}

	@RequiresPermissions("adv:material:delete")
	@RequestMapping(value = "/roll/save")
	public String roll(AdControll entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		 
		try {
			Boolean b = thisService.isCanDelete(entity.getId());
			if (b) {
				thisService.delete(entity);
				addMessage(redirectAttributes, "msg.del.success");
			} else {
				addMessage(redirectAttributes, "controll.delete.not");
			}

			return "redirect:/adv/control/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/control/?repage";
		}
	}

	/**
	 * 滚动广告生成图片
	 * @param dto
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get_roll_backgroud", method = RequestMethod.POST)
	public String roll(@RequestBody ImageDTO dto, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			String[] root = FileUtils.fileUploadAdr(request.getSession()
					.getServletContext().getRealPath(""));
			String realPath = root[0];
			if (dto.getIsPurity() == 1) {
				String imagePah = dto.getBackImagePath();// 相对路径
															// /advs/upload/test.jpg
				String[] s = realPath.split("/");
				StringBuffer backGroundPath = new StringBuffer();
				for (int i = 0; i < s.length - 2; i++) {
					backGroundPath.append(s[i]);
					if (i < s.length - 3) {
						backGroundPath.append("/");
					}
				}
				backGroundPath.append(imagePah);
				dto.setBackImagePath(backGroundPath.toString());
			}
			Date date = new Date();
			String upload = realPath + "/roll/"
					+ UserUtils.getUser().getLoginName() + "_" + date.getTime();
			thisService.rollImageUpload(dto, upload);
			String type=".jpg";
			if(dto.getIsPurity() == ImageDTO.IMAGE_PURITY_TSP){
				type = ".png";
			}
			return "0`" + "/upload/" + root[1] + "/roll/"
					+ UserUtils.getUser().getLoginName() + "_" + date.getTime()
					+ type;

		} catch (Exception e) {
			//e.printStackTrace();
			return "1`" + getMessage("file.upload.fail");
		}
	}

}
