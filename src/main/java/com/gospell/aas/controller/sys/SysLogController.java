/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.Log;
import com.gospell.aas.service.sys.SysLogService;

/**
 * 日志Controller
 * @author free lance
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "/sys/log")
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, Model model,Log log) {
        Page<Log> page = logService.find(new Page<Log>(request, response), paramMap); 
       
        model.addAttribute("page", page);
        model.addAllAttributes(paramMap);
		return "/sys/logList";
	}
	/**
     * 导出电视运营商
     * @param entity
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("sys:log:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(@RequestParam Map<String, Object> paramMap,Log log, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(null, 3, getMessage("log"), "");
		try {
			String name  =getMessage("log.data.export") ;
			String fileName = name + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<Log> page = logService.find(new Page<Log>(request,
					response,-1), paramMap);
			new ExportExcel(name, Log.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"导出日志信息成功");
			return null;
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"导出日志信息失败",e.getMessage());
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/sys/log/?repage";
	}
}
