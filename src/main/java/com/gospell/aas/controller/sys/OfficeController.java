/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
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
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.sys.OfficeService;
 

/**
 * 机构Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "/sys/office")
public class OfficeController extends BaseController {

    @Autowired
    private OfficeService officeService;

    @ModelAttribute("office")
    public Office get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return officeService.get(id);
        } else {
            return new Office();
        }
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = { "list", "" })
    public String list(Office office, Model model) {
        User user = UserUtils.getUser();
        // office id 应该为company_id，这个才是  机构的ID
        office.setId(user.getCompany().getId());
        model.addAttribute("office", office);
        List<Office> list = Lists.newArrayList();
        List<Office> sourcelist = officeService.findAll();
        Office.sortList(list, sourcelist, office.getId());
        model.addAttribute("list", list);
        model.addAttribute("user", user);
        return "/sys/officeList";
    }

    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "form")
    public String form(Office office, Model model) {
        User user = UserUtils.getUser();
        if (office.getParent() == null || office.getParent().getId() == null) {
        	Office company = UserUtils.getOffice(user.getCompany().getId());
            office.setParent(company);
        }
        office.setParent( UserUtils.getOffice(office.getParent().getId()));
        if (office.getArea() == null) {
            office.setArea(office.getParent().getArea());
        }
        model.addAttribute("office", office);
 
        return "/sys/officeForm";
    }

    @ResponseBody
    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "checkOfficeName")
    public String checkOfficeName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && UserUtils.getOfficeByName(name) == null) {
            return "true";
        }
        return "false";
    }
    
    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "save")
    public String save(Office office, Model model, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, office)) {
            return form(office, model);
        }
        //1,Office.GRADE_3 下不能再添加机构
        //2,机构新增的grade 小于父grade
        if (office.getParent() == null) {
        	addMessage(model, "office.parent.null.error");
        	return form(office, model);
		}
        Office parent = officeService.get(office.getParent().getId());
        if (parent == null || Office.GRADE_3.equals(parent.getGrade()) 
        		||Integer.valueOf(parent.getGrade()) >= Integer.valueOf(office.getGrade())) {
        	addMessage(model, "office.grade.error");
        	return form(office, model);
		}
    	String logInfo=logService.getLogInfo(office.getId(), 0, getMessage("officeform.grade"), office.getCode());
        try {
			officeService.save(office);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存用户级别："+office.getCode()+"成功！");
		     addMessage(redirectAttributes, "msg.save.success");
		} catch (Exception e) {
		    addMessage(redirectAttributes, "msg.save.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存用户级别："+office.getCode()+"失败！",e.getMessage());
		}
   
        return "redirect:/sys/office/";
    }

    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	Office office = get(id);
    	String logInfo=logService.getLogInfo(office.getId(), 1, getMessage("officeform.grade"), office.getCode());
        if (Office.isRoot(id)) {
            addMessage(redirectAttributes, "msg.del.fail");
        } else {
            try {
				officeService.delete(id);
				addMessage(redirectAttributes, "msg.del.success");
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除用户级别："+office.getCode()+"成功！");
			} catch (Exception e) {
				addMessage(redirectAttributes, "msg.del.success");
				logService.save(request, logInfo, e);
				logger.info(UserUtils.getUser().getLoginName()+"删除用户级别："+office.getCode()+"失败！",e);
			}
            
        }
        return "redirect:/sys/office/";
    }
    @ResponseBody
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "checkCode")
	public String checkCode(String oldCode, String code) {

		if (oldCode != null && code.equals(oldCode)) {
			return "true";
		} else if (code != null
				&& officeService.getOfficeByCode(code) == null) {
			return "true";
		}
		return "false";
	}
    
    @ResponseBody
   	@RequiresPermissions("sys:office:edit")
   	@RequestMapping(value = "checkName")
   	public String checkName(String oldName, String name) {

   		if (oldName != null && name.equals(oldName.trim())) {
   			return "true";
   		} else if (name != null
   				&& officeService.getOfficeByName(name) == null) {
   			return "true";
   		}
   		return "false";
   	}
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
            @RequestParam(required = false) Long type, @RequestParam(required = false) Long grade,
            HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        // User user = UserUtils.getUser();
        List<Office> list = officeService.findAll();
        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            if ((extId == null || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf(
                    "," + extId + ",") == -1))
                    && (type == null || (type != null && Integer.parseInt(e.getType()) <= type.intValue()))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                // map.put("pId", !user.isAdmin() &&
                // e.getId().equals(user.getOffice().getId())?0:e.getParent()!=null?e.getParent().getId():0);
                map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
     
        
        return mapList;
    }
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "roletreeData")
    public List<Map<String, Object>> roletreeData(@RequestParam(required = false) String extId,
            @RequestParam(required = false) Long type, @RequestParam(required = false) Long grade,
            HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        // User user = UserUtils.getUser();
        List<Office> list = officeService.findOfficeInRoleFrom();
        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            if ((extId == null || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf(
                    "," + extId + ",") == -1))
                    && (type == null || (type != null && Integer.parseInt(e.getType()) <= type.intValue()))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                // map.put("pId", !user.isAdmin() &&
                // e.getId().equals(user.getOffice().getId())?0:e.getParent()!=null?e.getParent().getId():0);
                map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
     
        
        return mapList;
    }
    
}
