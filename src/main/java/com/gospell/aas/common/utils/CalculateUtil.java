package com.gospell.aas.common.utils;

import java.math.BigDecimal;

public class CalculateUtil {

	/**
	 * 两个整数计算百分比
	 * @param divisor除数
	 * @param dividend 被除数
	 * @return
	 */
	public static BigDecimal getPercent(Integer divisor,Integer dividend){
		BigDecimal day = new BigDecimal(divisor);
		BigDecimal allDay = new BigDecimal(dividend);

		BigDecimal timeScale = day.divide(allDay, 4,
				BigDecimal.ROUND_HALF_UP);// 四舍五入，保留两位
		BigDecimal b = new BigDecimal(
				String.valueOf(timeScale));
		BigDecimal percent = b
				.multiply(new BigDecimal(100));
		return percent;
	}
	
	public static String decimalFormat(BigDecimal b){
		String s = String.valueOf(b);
		 if(s.indexOf(".") > 0){
		     //正则表达
		           s = s.replaceAll("0+?$", "");//去掉后面无用的零
		           s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		  }
		 return s;
	}
	
 

}
