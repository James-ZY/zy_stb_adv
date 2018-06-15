/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.controller.sys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.google.common.collect.Maps;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.utils.CacheUtils;
import com.gospell.aas.common.utils.CookieUtils;
import com.gospell.aas.common.utils.InternetProtocolUtil;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.SystemDateDTO;
import com.gospell.aas.entity.sys.User;

/**
 * 登录Controller
 * 
 * @author free lance
 * 
 * @version 2013-5-31
 */

@Controller
public class LoginController extends BaseController {
	public static final String CHINESE = "zh_CN";
	public static final String ENGLISH = "en_US";

	public static final String chineseLocale = Global
			.getConfig("chineseLocale");

	public static final String englishLocale = Global
			.getConfig("englishLocale");

	public static final String defaultLocale = Global
			.getConfig("defaultLocale");
	
	@Autowired
	private  CookieLocaleResolver cookie;
	
	@Resource(name="localeResolver")
	private  CookieLocaleResolver localeResolver;

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login1(@RequestParam(value = "locale", required = false) String locale,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
	 
		User user = UserUtils.getUser();
		// 如果已经登录，则跳转到管理首页
		if (user.getId() != null) {
			return "redirect:/index";
		}
	 
		Locale l = null;
		if (org.apache.commons.lang3.StringUtils.isNotBlank(locale)) {
			if (locale.equals(CHINESE)) {
				String[] s = chineseLocale.split("_");
				l = new Locale(s[0], s[1]);

			} else {
				String[] s = englishLocale.split("_");
				l = new Locale(s[0], s[1]);

			}
			
			localeResolver.setLocale(request, response, l);
		 	
		} else{
			l = LocaleContextHolder.getLocale();
		}
		model.addAttribute("locale", l.getLanguage() + "_" + l.getCountry());

		return "/sys/sysLogin";
	
	}
	
 

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
	 
		User user = UserUtils.getUser();
		// 如果已经登录，则跳转到管理首页
		if (user.getId() != null) {
			return "redirect:/index";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
				username);
		model.addAttribute("isValidateCodeLogin",
				isValidateCodeLogin(username, true, false));
		 
		Locale l = LocaleContextHolder.getLocale();
  
		 
		model.addAttribute("locale", l.getLanguage() + "_" + l.getCountry());
		logger.info(UserUtils.getUser().getLoginName()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
				+"通过Ip："+InternetProtocolUtil.getRemoteAddr(request)+"登录失败");
		return "/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresUser
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		// 未登录，则跳转到登录页
		if (user.getId() == null) {
	 
			return "redirect:/login";
		}
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(user.getLoginName(), false, true);
		// 登录成功后，获取上次登录的当前站点ID
		UserUtils.putCache("siteId", CookieUtils.getCookie(request, "siteId"));
	 
		if (user != null && user.getCompany() != null
				&& org.apache.commons.lang3.StringUtils.isNotBlank(user.getCompany().getId())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			model.addAttribute("orgName", user.getCompany().getName());
		}
		logger.info(UserUtils.getUser().getLoginName()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
				+"通过Ip："+InternetProtocolUtil.getRemoteAddr(request)+"登录成功");
		return "/sys/sysIndex";

	}

	/**
	 * 没有权限
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unauth")
	public String unauth(Model model) throws Exception {
		if (SecurityUtils.getSubject().isAuthenticated() == false) {
			return "redirect:/login";
		}

		return "/unauth";
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme,
			HttpServletRequest request, HttpServletResponse response) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}

	/**
	 * 是否是验证码登录
	 * 
	 * @param useruame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail,
			boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils
				.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}

	@RequestMapping("/download")
	public String download(@RequestParam String filePath,
			HttpServletResponse response) {
		File file = new File(filePath);
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(filePath);
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ file.getName() + "\"");
			OutputStream outputStream = new BufferedOutputStream(
					response.getOutputStream());
			byte data[] = new byte[1024];
			while (inputStream.read(data, 0, 1024) >= 0) {
				outputStream.write(data);
			}
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
		 
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
	 
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "find_system_date", method = RequestMethod.POST)
	public SystemDateDTO findSystemDate(HttpServletRequest request,HttpServletResponse response) {
		 Calendar cal = java.util.Calendar.getInstance();
		long time = cal.getTime().getTime();
    	TimeZone timeZone = cal.getTimeZone();
    	int i = timeZone.getRawOffset();
    	BigDecimal rawOffset = new BigDecimal(i);
    	BigDecimal b2 = new BigDecimal(3600000);
    	BigDecimal timeZone_bigdecimal = rawOffset.divide(b2, 2,BigDecimal.ROUND_HALF_UP);
    	String timeZoneStr = String.valueOf(timeZone_bigdecimal);
    	String zone="UTC";
    	String[] s= timeZoneStr.split("\\.");
    	int ii = Integer.parseInt(s[0]);
    	if(ii>0){
    		zone += "+";
    		if(ii >10){
    			zone += ii;
    		}else{
    			zone += "0"+ii;
    		}
    	}else if(ii==0){
    		zone += "0"+ii;
    	}else{
    	 
    		if(ii <-10){
    			zone += ii;
    		}else{
    			zone="-0"+Math.abs(ii);
    		}
    	}
    	
    	float jj = Float.parseFloat(s[1]);
    	float nn = jj/100 *60;
       	String v1 = String.valueOf(nn);
    	String[] v2= v1.split("\\.");
    	zone += ":";
    	if(nn >0){
    		zone += v2[0];
    	}else{
    		zone += "00";;
    	}
 
    	 SystemDateDTO dto = new SystemDateDTO();
    	 dto.setTime(time);
    	 dto.setTimeZone(timeZoneStr);
    	 dto.setZoneFormat(zone);
    	 return dto;
	}
 
}
