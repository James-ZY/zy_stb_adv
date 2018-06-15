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
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdDistrictCategory;
import com.gospell.aas.service.adv.AdDistrictCategoryService;

@Controller
@RequestMapping(value = "/adv/districtCategory")
public class AdDistrictCategoryController extends BaseController {

	@Autowired
	private AdDistrictCategoryService thisService;

	@ModelAttribute
	public AdDistrictCategory get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdDistrictCategory();
		}
	}

	@RequiresPermissions("sys:districtCategory:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdDistrictCategory entity, HttpServletRequest request,
					   HttpServletResponse response, Model model) {

		List<AdDistrictCategory> list = Lists.newArrayList();
		List<AdDistrictCategory> sourcelist = thisService.findAllAdDistrictCategory();
		String type = thisService.getCurrentSysAreaType();
		AdDistrictCategory.sortList(list, sourcelist, AdDistrictCategory.getzeroAdCategoryId(type));
		model.addAttribute("list", list);
		String key = AdDistrictCategory.TypeEnum.getByName(type).getKey();
		model.addAttribute("key", key);
		return "/adDistrictCategory/districtCategoryList";
	}

	@RequiresPermissions("sys:districtCategory:view")
	@RequestMapping(value = "/form")
	public String form(AdDistrictCategory entity, Model model) {
		if (entity.getParent() == null || entity.getParent().getId() == null) {
			String type = thisService.getCurrentSysAreaType();
			entity.setParent(new AdDistrictCategory(AdDistrictCategory.getzeroAdCategoryId(type)));
		}
		AdDistrictCategory category = thisService.get(entity.getParent().getId());
		entity.setParent(category);
		model.addAttribute("adCategory", entity);
		model.addAttribute("categroryNameSelect",
				getMessage("select.category.parent"));
		return "/adDistrictCategory/districtCategoryForm";
	}

	@RequiresPermissions("sys:districtCategory:edit")
	@RequestMapping(value = "/save")
	public String save(AdDistrictCategory entity, HttpServletRequest request,
					   Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(entity.getId()) || StringUtils.isBlank(entity.getCategoryId())) {
			AdDistrictCategory parent = thisService.get(entity.getParent().getId());
			entity.setId(thisService.getId(parent,true));
			entity.setCategoryId(thisService.getId(parent,false));
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("category"), entity.getCategoryId());
		try {
			if (!beanValidator(model, entity)) {
				return form(entity, model);
			}
			String type = thisService.getCurrentSysAreaType();
			entity.setType(type);
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改区域分类ID:"+entity.getCategoryId()+"成功");
			return "redirect:/adv/districtCategory/?repage";
		} catch (NumberFormatException e) {
			model.addAttribute("categoryIdError",
					getMessage("categoryId.error"));
			logService.save(request, logInfo, new Exception(getMessage("categoryId.error")));
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改区域分类ID:"+entity.getCategoryId()+"失败",new Exception(getMessage("categoryId.error")));
			return form(entity, model);
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改区域分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/districtCategory/?repage";
		}
	}

	@RequiresPermissions("sys:districtCategory:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdDistrictCategory entity, HttpServletRequest request,
						 Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("category"), entity.getCategoryId());
		try {
			thisService.delete(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除区域分类ID:"+entity.getCategoryId()+"成功");
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/districtCategory/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改区域分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/districtCategory/?repage";
		}
	}

	/**
	 * 现在广告类型为树形结构，判断同一级的TypeID不能重复
	 *
	 * @param oldCategoryId
	 * @param categoryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkcategoryId", method = RequestMethod.GET)
	public String checktypeId(HttpServletRequest request, String oldCategoryId,
							  String categoryId) {
		try {
			request.setCharacterEncoding("utf-8");
			String type = thisService.getCurrentSysAreaType();
			if (categoryId != null && categoryId.trim().equals(oldCategoryId)) {
				return "true";
			} else if (categoryId != null
					&& thisService
					.findAdDistrictCategoryByCateGoryId(categoryId.trim(),type) == null) {
				return "true";
			}
			logger.info(UserUtils.getUser().getLoginName()+"现在广告类型为树形结构，判断同一级的TypeID不能重复成功，传入的新ID是:"+categoryId);
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"现在广告类型为树形结构，判断同一级的TypeID不能重复，传入的新ID是:"+categoryId+"失败",e);
		}
		return "false";
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) Object extId,
			HttpServletResponse response) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			List<Map<String, Object>> mapList = Lists.newArrayList();
			List<AdDistrictCategory> list = thisService.findAllAdDistrictCategory();
			for (int i = 0; i < list.size(); i++) {
				AdDistrictCategory e = list.get(i);
				if (extId == null
						|| (extId != null && !extId.equals(e.getId()) && e
						.getParentIds().indexOf("," + extId + ",") == -1)) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParent() != null ? e.getParent()
							.getId() : 0);
					map.put("name", e.getCategoryName());
					mapList.add(map);
				}
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeDataNoFirst")
	public List<Map<String, Object>> treeDataNoFirst(
			@RequestParam(required = false) Object extId,
			HttpServletResponse response) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			String type = thisService.getCurrentSysAreaType();
			List<Map<String, Object>> mapList = Lists.newArrayList();
			List<AdDistrictCategory> list = thisService.findAllAdDistrictCategory();
			for (int i = 0; i < list.size(); i++) {
				AdDistrictCategory e = list.get(i);
				if (e.getId().equals(AdDistrictCategory.getzeroAdCategoryId(type))) {
					e.setCategoryName(getMessage("top.category"));
				}
				if (extId == null
						|| (extId != null && !extId.equals(e.getId()) && e
						.getParentIds().indexOf("," + extId + ",") == -1)) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParent() != null ? e.getParent()
							.getId() : 0);
					map.put("name", e.getCategoryName());
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
	 * 获取区域设置信息
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDistrictCategory", method = RequestMethod.POST)
	public String getDistrictCategory(@RequestBody Map<String, Object> map,
									  HttpServletRequest request, HttpServletResponse response) {
		return thisService.getDistrictJson(map);
	}

	/**
	 * 获取运营商/发送器区域设置信息
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOperatorDistrict", method = RequestMethod.POST)
	public String getOperatorDistrict(@RequestBody Map<String, Object> map,
									  HttpServletRequest request, HttpServletResponse response) {
		String disId = (String) map.get("districts");
		String operatorsId = (String) map.get("operatorsId");
		if(StringUtils.isNotBlank(disId)){
			return thisService.getOperatorDistrict(disId.split(":")[0],operatorsId);
		}else{
			return null;
		}
	}

	/**
	 * 通过选择区域获取运营商信息
	 *
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOperatorsByDis", method = RequestMethod.POST)
	public String getOperatorsByDis(@RequestBody Map<String, Object> map,
									HttpServletRequest request, HttpServletResponse response) {
		String disId = (String) map.get("districts");
		if(StringUtils.isNotBlank(disId)){
			return thisService.getOperatorsByDis(disId);
		}else{
			return null;
		}
	}



	/**
	 * 通过选择区域和运营商获取相关发送器(与频道无关)
	 *
	 * @param selectMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNetworkByDis", method = RequestMethod.POST)
	public String getNetworkByDis(@RequestBody Map<String, Object> selectMap,
								  HttpServletRequest request, HttpServletResponse response) {
		String type = thisService.getCurrentSysAreaType();
		String disIds = (String) selectMap.get("districts");
		String operatorsId = (String) selectMap.get("operatorsId");
		String typeId = (String) selectMap.get("typeId");
		String chlidType = (String) selectMap.get("chlidType");
		String startDate = (String) selectMap.get("startDate");
		String endDate = (String) selectMap.get("endDate");
		String sendMode = (String) selectMap.get("sendMode");
		String advertiserId = (String) selectMap.get("advertiserId");
		String dto = "";
		if(StringUtils.isNotBlank(disIds)){
			try {
				dto =  thisService.getNetworkByDis(disIds,operatorsId,typeId,chlidType,startDate,endDate,sendMode,advertiserId,type);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dto;
	}

	/**
	 * 通过选择区域和运营商获取相关发送器(与频道相关)
	 *
	 * @param selectMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNetworkChannelByDis", method = RequestMethod.POST)
	public String getNetworkChannelByDis(@RequestBody Map<String, Object> selectMap,
										 HttpServletRequest request, HttpServletResponse response) {
		String disIds = (String) selectMap.get("districts");
		String operatorsId = (String) selectMap.get("operatorsId");
		String typeId = (String) selectMap.get("typeId");
		String chlidType = (String) selectMap.get("chlidType");
		String startDate = (String) selectMap.get("startDate");
		String endDate = (String) selectMap.get("endDate");
		String sendMode = (String) selectMap.get("sendMode");
		String advertiserId = (String) selectMap.get("advertiserId");
		String dto = "";
		if(StringUtils.isNotBlank(disIds)){
			try {
				dto =  thisService.getNetworkChannelByDis(disIds,operatorsId,typeId,chlidType,startDate,endDate,sendMode,advertiserId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dto;
	}
}
