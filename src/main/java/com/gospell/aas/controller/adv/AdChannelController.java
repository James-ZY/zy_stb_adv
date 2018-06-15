package com.gospell.aas.controller.adv;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdProgramCategory;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.service.adv.AdChannelService;
import com.gospell.aas.service.adv.AdTypeService;

@Controller
@RequestMapping(value = "/adv/channel")
public class AdChannelController extends BaseController {

	@Autowired
	private AdChannelService thisService;
	@Autowired
	private AdTypeService typeService;

	@ModelAttribute
	public AdChannel get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdChannel();
		}
	}

	@RequiresPermissions("sys:channel:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdChannel entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AdChannel> page = thisService.findBysql(new Page<AdChannel>(
				request, response), entity);
		model.addAttribute("page", page);
		return "/channel/channelList";
	}

	@RequiresPermissions("sys:channel:view")
	@RequestMapping(value = "/form")
	public String form(AdChannel entity, Model model) {
		model.addAttribute("adChannel", entity);
		thisService.clear();
		List<AdType> source_list = entity.getTypeList();
		if (source_list != null) {
			List<AdType> locale_list = Lists.newArrayList();
			for (int i = 0; i < source_list.size(); i++) {
				AdType a = AdTypeUtils.get(source_list.get(i).getId());
				locale_list.add(a);
			}
			entity.setTypeList(locale_list);
		}
		List<AdType> list = AdTypeUtils.getAdTypeByIsFlag(AdType.TYPE_CHANNEL);
		model.addAttribute("allTypeList", list);
		return "/channel/channelForm";
	}

	@RequiresPermissions("sys:channel:edit")
	@RequestMapping(value = "/save")
	public String save(AdChannel entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		String categoryId = request.getParameter("adCategory.id");
		if (categoryId != null
				&& categoryId.equals(AdProgramCategory
						.getzeroAdProgramCategoryId())) {
			addMessage(redirectAttributes, "category.id.select.error");
			return "redirect:/adv/channel/?repage";
		}
		entity.setAdCategory(new AdProgramCategory(request
				.getParameter("adCategory.id")));
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		try {
			thisService.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/adv/channel/?repage";
	}
}
