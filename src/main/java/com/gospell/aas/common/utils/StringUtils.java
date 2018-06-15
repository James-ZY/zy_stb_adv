/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.google.common.collect.Lists;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 * @author free lance
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 首字母小写
     * 
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    /**
     * 首字母大写
     * 
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * 
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     * 
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
    public static String getMessage(String code, Object[] args) {
        LocaleResolver localLocaleResolver = ApplicationContextHelper.getBean(LocaleResolver.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Locale localLocale = localLocaleResolver.resolveLocale(request);
        return ApplicationContextHelper.getApplicationContext().getMessage(code, args, localLocale);
    }
    
    public static String getIntegerStr(Integer value){
    	if(value == null){
    		return "";
    	}else{
    		return String.valueOf(value);
    	}
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        	String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
    
    public static String getTime(int hour,int minutes,int second){
    	String s="";
		if(hour <10){
			s += "0"+hour;
		}else{
			s += hour;
		}
		s+=":";
		if(minutes <10){
			s += "0"+minutes;
		}else{
			s += minutes;
		}
		s+=":";
		if(second <10){
			s += "0"+second;
		}else{
			s += second;
		}
		return s;
    }
    
	/**
	 * 十进制转换为16进制后字符串，不足四位前面补0
	 * @return
	 */
	public static String tencentToHex(String hex){
		if(isBlank(hex)){
			return "";
		}
		StringBuffer s =new StringBuffer();
		int length = hex.length();
		if(length <4){
			int size = 4-length;
			for (int i = 0; i < size; i++) {
				s.append("0");
			}
		}
		s.append(hex);
		return s.toString();
	}
	
	public static String stringToLower(String sourceString){
		if(isNotBlank(sourceString)){
			return sourceString.toLowerCase();
		}
		return sourceString;
	}
	
	/**
	 * 将字符串数组全部转换为小写
	 */
	public static 	List<String>  stringListToLower(List<String> list){
		List<String> lowerList = Lists.newArrayList();
		if(null != list && list.size() >0){
		
			for (int i = 0; i < list.size(); i++) {
				lowerList.add(stringToLower(list.get(i)));
			}
		
		}
		return lowerList;
	}
	public static void main(String[] args) {
		String source = "0001FFFF";
		String source1 = "0001AAAA";
		List<String> list = Lists.newArrayList();
		list.add(source);
		list.add(source1);
		List<String> list1 = stringListToLower(list);
		for (int i = 0; i < list1.size(); i++) {
			System.out.println(list1.get(i));
		}
		
	}
	
}
