package com.gospell.aas.controller.adv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.google.common.collect.Maps;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.service.adv.AdTypeService;
@Controller
@RequestMapping(value = "/adv/type")
public class AdTypeController extends BaseController {

	@Autowired
	private AdTypeService thisService;

	@ModelAttribute
	public AdType get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdType();
		}
	}

	@RequiresPermissions("sys:type:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdType entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
 
		List<AdType> list = Lists.newArrayList();
		List<AdType> sourcelist = thisService.findAllAdType();
		AdType.sortList(list, sourcelist, AdType.getzeroAdTypeId());
		model.addAttribute("list", list);
		return "/adtype/typeList";
	}

	@RequiresPermissions("sys:type:view")
	@RequestMapping(value = "/form")
	public String form(AdType entity, Model model) {
		if (entity.getParent()==null||entity.getParent().getId()==null){
			entity.setParent(new AdType(AdType.getzeroAdTypeId()));
		}
		
		entity.setParent(thisService.getAdType(entity.getParent().getId()));
	 
		model.addAttribute("adType", entity);
		return "/adtype/typeForm";
	}

	 
	@RequiresPermissions("sys:type:edit")
	@RequestMapping(value = "/save")
	public String save(AdType entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("adv.type"), entity.getTypeId());
		try {
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改广告类型："+entity.getTypeId()+"成功！");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/type/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存或者修改广告类型："+entity.getTypeId()+"失败！",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/type/?repage";
		}
	}

	@RequiresPermissions("sys:type:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdType entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {

		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("adv.type"), entity.getTypeId());
		try {
			thisService.delete(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除广告类型："+entity.getTypeId()+"成功！");
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/type/?repage";
		} catch (ServiceException e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告类型："+entity.getTypeId()+"失败！",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/type/?repage";
		}
	}

	/**
	 * 现在广告类型为树形结构，判断同一级的TypeID不能重复
	 * @param oldTypeId
	 * @param typeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checktypeId", method = RequestMethod.POST)
	public boolean checktypeId(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		boolean b =false;
		try {
		  b = thisService.checkTypeIdIsRepeat(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	 

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) Object extId,
			HttpServletResponse response) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			List<Map<String, Object>> mapList = Lists.newArrayList();
			System.out.println("thiService:"+thisService);
			List<AdType> list = thisService.findAllAdType();
			for (int i = 0; i < list.size(); i++) {
				AdType e = list.get(i);
				if (extId == null|| (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParent() != null ? e.getParent()
							.getId() : 0);
					map.put("name", e.getTypeName());
					mapList.add(map);
				}
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
	@RequestMapping(value = "find_type_status", method = RequestMethod.POST)
	public String findTypestatus(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String id = (String) map.get("id");
		AdType t = AdTypeUtils.get(id);
		return String.valueOf(t.getStatus());

	}

}
