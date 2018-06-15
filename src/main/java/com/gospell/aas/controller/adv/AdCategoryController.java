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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdCategory;
import com.gospell.aas.service.adv.AdCategoryService;

@Controller
@RequestMapping(value = "/adv/category")
public class AdCategoryController extends BaseController {

	@Autowired
	private AdCategoryService thisService;

	@ModelAttribute
	public AdCategory get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdCategory();
		}
	}

	@RequiresPermissions("sys:category:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdCategory entity, HttpServletRequest request,
					   HttpServletResponse response, Model model) {

		List<AdCategory> list = Lists.newArrayList();
		List<AdCategory> sourcelist = thisService.findAllAdCategory();
		AdCategory.sortList(list, sourcelist, AdCategory.getzeroAdCategoryId());
		model.addAttribute("list", list);
		return "/adCategory/categoryList";
	}

	@RequiresPermissions("sys:category:view")
	@RequestMapping(value = "/form")
	public String form(AdCategory entity, Model model) {
		if (entity.getParent() == null || entity.getParent().getId() == null) {
			entity.setParent(new AdCategory(AdCategory.getzeroAdCategoryId()));
		}
		entity.setParent(thisService.get(entity.getParent().getId()));

		model.addAttribute("adCategory", entity);
		model.addAttribute("categroryNameSelect",
				getMessage("select.category.parent"));
		return "/adCategory/categoryForm";
	}

	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "/save")
	public String save(AdCategory entity, HttpServletRequest request,
					   Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(entity.getId()) || StringUtils.isBlank(entity.getCategoryId())) {
			entity.setId(thisService.getNewCategoryId());
			entity.setCategoryId(thisService.getNewCategoryId());
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("category"), entity.getCategoryId());
		try {
			if (!beanValidator(model, entity)) {
				return form(entity, model);
			}

			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改广告分类ID:"+entity.getCategoryId()+"成功");
			return "redirect:/adv/category/?repage";
		} catch (NumberFormatException e) {
			model.addAttribute("categoryIdError",
					getMessage("categoryId.error"));
			logService.save(request, logInfo, new Exception(getMessage("categoryId.error")));
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改广告分类ID:"+entity.getCategoryId()+"失败",new Exception(getMessage("categoryId.error")));
			return form(entity, model);
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改广告分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/category/?repage";
		}
	}

	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdCategory entity, HttpServletRequest request,
						 Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("category"), entity.getCategoryId());
		try {
			thisService.delete(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除广告分类ID:"+entity.getCategoryId()+"成功");
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/category/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改广告分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/category/?repage";
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

			if (categoryId != null && categoryId.trim().equals(oldCategoryId)) {
				return "true";
			} else if (categoryId != null
					&& thisService
					.findAdCategoryByCateGoryId(categoryId.trim()) == null) {
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
			List<AdCategory> list = thisService.findAllAdCategory();
			for (int i = 0; i < list.size(); i++) {
				AdCategory e = list.get(i);
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
			List<Map<String, Object>> mapList = Lists.newArrayList();
			List<AdCategory> list = thisService.findAllAdCategory();
			for (int i = 0; i < list.size(); i++) {
				AdCategory e = list.get(i);
				if (e.getId().equals(AdCategory.getzeroAdCategoryId())) {
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

}
