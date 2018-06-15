/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.controller.sys;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.Menu;
import com.gospell.aas.service.sys.SystemService;


/**
 * 菜单Controller
 * @author free lance
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = systemService.findAllMenu();
		Menu.sortList(list, sourcelist, "1");			
        model.addAttribute("list", list);
		return "/sys/menuList";
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu("1"));
		}
		 
		menu.setParent(systemService.getMenuByCache(menu.getParent().getId()));
		model.addAttribute("menu", menu);
		return "/sys/menuForm";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save")
	public String save(Menu menu, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, menu)){
			return form(menu, model);
		}
		String logInfo=logService.getLogInfo(null, 0, getMessage("menu"), menu.getName());
	
		try {
			systemService.saveMenu(menu);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存菜单成功");
			addMessage(redirectAttributes, "msg.save.success");
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存菜单失败",e);
			addMessage(redirectAttributes, "msg.save.fail");
		}

		return "redirect:/sys/menu/";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete")
	public String delete(String id,  HttpServletRequest request,RedirectAttributes redirectAttributes) {
		Menu m = get(id);
		String logInfo=logService.getLogInfo(null, 1, getMessage("menu"), m.getName());
		
		if (Menu.isRoot(id)){
			addMessage(redirectAttributes, "msg.del.fail");
		}else{
			try {
				systemService.deleteMenu(id);
				addMessage(redirectAttributes,  "msg.del.success");
				logService.save(request, logInfo, null);
				logger.error(UserUtils.getUser().getLoginName()+"删除菜单成功");
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.error(UserUtils.getUser().getLoginName()+"删除菜单失败",e);
				addMessage(redirectAttributes,  "msg.del.fail");
			}
		
		}
		return "redirect:/sys/menu/";
	}

	@RequiresUser
	@RequestMapping(value = "tree")
	public String tree() {
		return "/sys/menuTree";
	}
	@RequiresUser
	@RequestMapping(value = "advtree")
	public String advtree() {
		return "/sys/advmenuTree";
	}
	
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Menu[] menus = new Menu[len];
    	for (int i = 0; i < len; i++) {
    		menus[i] = systemService.getMenu(ids[i]);
    		menus[i].setSort(sorts[i]);
    		try {
				systemService.saveMenu(menus[i]);
				addMessage(redirectAttributes,"msg.save.success");
		 
				logger.info(UserUtils.getUser().getLoginName()+"修改菜单："+menus[i]+"排序成功");
			} catch (Exception e) {
				logger.error(UserUtils.getUser().getLoginName()+"修改菜单："+menus[i]+"排序失败");
			}
    	}
    
		return "redirect:/sys/menu/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) Object extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
