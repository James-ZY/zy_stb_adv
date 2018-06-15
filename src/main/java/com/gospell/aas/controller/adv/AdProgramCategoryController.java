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
import com.gospell.aas.entity.adv.AdProgramCategory;
import com.gospell.aas.service.adv.AdProgramCategoryService;

@Controller
@RequestMapping(value = "/adv/programCategory")
public class AdProgramCategoryController extends BaseController {

	@Autowired
	private AdProgramCategoryService thisService;

	@ModelAttribute
	public AdProgramCategory get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdProgramCategory();
		}
	}

	@RequiresPermissions("sys:programCategory:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdProgramCategory entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		List<AdProgramCategory> list = Lists.newArrayList();
		List<AdProgramCategory> sourcelist = thisService.findAllAdProgramCategory();
		AdProgramCategory.sortList(list, sourcelist, AdProgramCategory.getzeroAdProgramCategoryId());
		model.addAttribute("list", list);
		return "/adProgramCategory/programCategoryList";
	}

	@RequiresPermissions("sys:programCategory:view")
	@RequestMapping(value = "/form")
	public String form(AdProgramCategory entity, Model model) {
		if (entity.getParent() == null || entity.getParent().getId() == null) {
			entity.setParent(new AdProgramCategory(AdProgramCategory.getzeroAdProgramCategoryId()));
		}
		entity.setParent(thisService.get(entity.getParent().getId()));

		model.addAttribute("adProgramCategory", entity);
		model.addAttribute("categroryNameSelect",
				getMessage("select.programCategory.parent"));
		return "/adProgramCategory/programCategoryForm";
	}

	@RequiresPermissions("sys:programCategory:edit")
	@RequestMapping(value = "/save")
	public String save(AdProgramCategory entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("programCategory"), entity.getCategoryId());
		try {
			if (!beanValidator(model, entity)) {
				return form(entity, model);
			}
			String programCategoryId = entity.getCategoryId();
			Integer programCategory = Integer.parseInt(programCategoryId);
			if (programCategory <= 0) {
				model.addAttribute("programCategoryIdError",
						getMessage("programCategoryId.error"));
				return form(entity, model);
			}
 

			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改节目分类ID:"+entity.getCategoryId()+"成功");
			return "redirect:/adv/programCategory/?repage";
		} catch (NumberFormatException e) {
			model.addAttribute("programCategoryIdError",
					getMessage("programCategoryId.error"));
			logService.save(request, logInfo, new Exception(getMessage("programCategoryId.error")));
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改节目分类ID:"+entity.getCategoryId()+"失败",new Exception(getMessage("programCategoryId.error")));
			return form(entity, model);
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改节目分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/programCategory/?repage";
		}
	}

	@RequiresPermissions("sys:programCategory:edit")
	@RequestMapping(value = "/delete")
	public String delete(AdProgramCategory entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("programCategory"), entity.getCategoryId());
		try {
			thisService.delete(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除节目分类ID:"+entity.getCategoryId()+"成功");
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/programCategory/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改节目分类ID:"+entity.getCategoryId()+"失败",e);
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/programCategory/?repage";
		}
	}

	/**
	 * 现在节目类型为树形结构，判断同一级的TypeID不能重复
	 * 
	 * @param oldTypeId
	 * @param typeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkProgramCategoryId", method = RequestMethod.GET)
	public String checktypeId(HttpServletRequest request, String oldCategoryId,
			String programCategoryId) {
		try {
			request.setCharacterEncoding("utf-8");

			if (programCategoryId != null && programCategoryId.trim().equals(oldCategoryId)) {
				return "true";
			} else if (programCategoryId != null
					&& thisService
							.findAdProgramCategoryByCateGoryId(programCategoryId.trim()) == null) {
				return "true";
			}
			logger.info(UserUtils.getUser().getLoginName()+"现在节目类型为树形结构，判断同一级的TypeID不能重复成功，传入的新ID是:"+programCategoryId);
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"现在节目类型为树形结构，判断同一级的TypeID不能重复，传入的新ID是:"+programCategoryId+"失败",e);
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
			List<AdProgramCategory> list = thisService.findAllAdProgramCategory();
			for (int i = 0; i < list.size(); i++) {
				AdProgramCategory e = list.get(i);
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
			List<AdProgramCategory> list = thisService.findAllAdProgramCategory();
			for (int i = 0; i < list.size(); i++) {
				AdProgramCategory e = list.get(i);
				if (e.getId().equals(AdProgramCategory.getzeroAdProgramCategoryId())) {
					e.setCategoryName(getMessage("top.programCategory"));
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
