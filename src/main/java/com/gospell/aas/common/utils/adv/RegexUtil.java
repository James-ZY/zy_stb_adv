package com.gospell.aas.common.utils.adv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * 验证广告商手机(暂时验证10位或者11位数字，搞清楚印度的格式之后重新验证)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobilePhone(String str) {
		String regex = "^\\d{1,15}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}
	

	/**
	 * 验证广告商手机(暂时验证10位或者11位数字，搞清楚印度的格式之后重新验证)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTelPhone(String str) {
		String regex = "^\\d{1,15}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}
	
	/**
	 * 验证广告商手机(暂时验证10位或者11位数字，搞清楚印度的格式之后重新验证)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkOperatorNumber(String str) {
		String regex = "^\\d{1,11}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}
	
	public static boolean check(String regex,String str){
	
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}
	
	

//	/**
//	 * 验证广告商电话
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static boolean isPhone(String str) {
//		String regex = "/^(\\d{3,5}-?)?\\d{5,10}$/g";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(str);
//		boolean b = m.matches();
//		if (b)
//			return true;
//		else
//			return false;
//	}
	public static void main(String[] args) {
		System.out.println(checkOperatorNumber("012345678910"));
	}
}
