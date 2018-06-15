/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gospell.aas.common.beanvalidator.BeanValidators;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.service.sys.SysLogService;


/**
 * 控制器支持类
 * @author free lance
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	protected long filesize = Global.getLongConfig("import.file.max");//导入文件的最大大小
	
	protected long excelNumber = Global.getIntConfig("import.excel.number");//导入excel的最大大小
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;
	
	@Autowired
	protected  CookieLocaleResolver cookieLocaleResolver;
	
	@Autowired
	protected SysLogService logService;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, getMessage("data.check.fail"));
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, getMessage("data.check.fail"));
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	  /**
     * 添加Model消息
     * 
     * @param messages
     *            消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Model消息。消息中可带参数
     * 多个参数以逗号分隔
     * @param model
     * @param argus 参数，以逗号分隔
     * @param messages
     */
    protected void  addArgumentMessage(Model model,String argus, String...messages ) {
    	StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("arguments", argus);
        model.addAttribute("message", sb.toString());
	}
    
    /**
     * 添加Flash消息
     * 
     * @param messages
     *            消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * message中带参数。通过argumentMsg.tag处理
     * spring:message 中传递参数
     * 参数以逗号分隔
     * @param redirectAttributes
     * @param argus messages中的参数
     * @param messages
     */
    protected void  addArgumentMessage(RedirectAttributes redirectAttributes, String argus, String... messages) {
    	StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("arguments", argus);
        redirectAttributes.addFlashAttribute("message", sb.toString());
	}
    
    /**
     * 获取国际化数据
     * @param key
     */
    protected String getMessage(String key) {
    	 return ApplicationContextHelper.getMessage(key);
	}
    protected String getMessage(String key,Object[] args) {
    	return ApplicationContextHelper.getMessage(key,args);
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    // 设置需要包裹的元素个数，默认为256
	    //binder.setAutoGrowCollectionLimit(5000);

		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.unescapeHtml4(StringEscapeUtils.escapeHtml4(text.trim())));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}
	
}
