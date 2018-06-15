package com.gospell.aas.controller.adv;

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
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.AdResourceOfTypeDTO;
import com.gospell.aas.entity.adv.AdResourceOfType;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.service.adv.AdResourceOfTypeService;
import com.gospell.aas.service.sys.SystemService;

@Controller
@RequestMapping(value = "/adv/resource")
public class AdResourceOfTypeController extends BaseController {

	@Autowired
	private AdResourceOfTypeService thisService;
	@Autowired
	private SystemService systemService;
	
	 
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public AdResourceOfType get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdResourceOfType();
		}
	}

	@RequiresPermissions("adv:adResourceOfType:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdResourceOfType entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdResourceOfType> page = thisService.findAll(new Page<AdResourceOfType>(request,
				response), entity);
		 
		model.addAttribute("page", page);
		return "/adResourceOfType/resourceOfTypeList";
	}

	@RequiresPermissions("adv:adResourceOfType:view")
	@RequestMapping(value = "/form")
	public String form(AdResourceOfType entity, Model model) {
		model.addAttribute("adResourceOfType", entity);
		model.addAttribute("imageformat", JsonMapper.toJsonString(DictUtils.getDictList("adv_resource_image_format")));
		model.addAttribute("vedioFormat", JsonMapper.toJsonString(DictUtils.getDictList("adv_resource_vedio_format")));
		return "/adResourceOfType/resourceOfTypeForm";
	}

	@RequiresPermissions("adv:adResourceOfType:edit")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(AdResourceOfType entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("control.parameter"), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			if(!entity.getAdType().getId().equals(AdType.TYPE_ROLL_ADV_ID)){
				entity.setRollFlag(null);
			}
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存素材参数："+entity.getId()+"成功");
			return "redirect:/adv/resource/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存素材参数失败，失败的时间是："+ DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/resource/?repage";
		}
	}

	@RequiresPermissions("adv:adResourceOfType:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(id, 0, getMessage("control.parameter"), id);
		try {
			 thisService.delete(id);
			 addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"成功");
			return "redirect:/adv/resource/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"删除素材参数："+id+"失败");
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/resource/?repage";
		}
	 
	}

	/**
	 * 获取广告类型Id是视频类型还是图片类型
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_resource_type", method = RequestMethod.POST)
	public 	List<AdResourceOfTypeDTO> findTypestatus(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		String flag = (String)map.get("flag");
		String rollFlag = (String)map.get("rollFlag");
		try{
			
			Integer resolution = Integer.valueOf(flag);
			List<AdResourceOfTypeDTO> list = thisService.findAdResourceOfTypeByTypeId(id,resolution,rollFlag);
			logger.info("通过广告类型id："+id+" 和分辨率"+flag+"查询素材参数成功");
			return list;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("通过广告类型id："+id+" 和分辨率"+flag+"查询素材参数失败");
			return null;
		}
		 

	}
 
	
	
 
 
}
