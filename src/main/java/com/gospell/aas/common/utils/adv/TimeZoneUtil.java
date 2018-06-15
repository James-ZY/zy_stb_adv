package com.gospell.aas.common.utils.adv;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneUtil{
/**
     * 取北京时间
     * @return
     */
    public static String getBeijingTime(){
        return getFormatedDateString(8);
    }
    
    /**
     * 取北京时间
     * @return
     */
    public static String getTime(){
        return getFormatedDateString(0);
    }
    
    /**
     * 取班加罗尔时间
     * @return
     */
    public static String getBangaloreTime(){
        return getFormatedDateString(5.5f);
    }
    
    /**
     * 取纽约时间
     * @return
     */
    public static String getNewyorkTime(){
        return getFormatedDateString(-5);
    }
    
    /**
     * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset
     * @return
     */
    public static String getFormatedDateString(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        
        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }
	    /**
	     * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
	     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	     * @param timeZoneOffset
	     * @return
	     */
	    public static String getGreenwichTime(float timeZoneOffset,Date date){
	        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
	            timeZoneOffset = 0;
	        }
	        
	        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
	        TimeZone timeZone;
	        String[] ids = TimeZone.getAvailableIDs(newTime);
	        if (ids.length == 0) {
	            timeZone = TimeZone.getDefault();
	        } else {
	            timeZone = new SimpleTimeZone(newTime, ids[0]);
	        }
	    
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        sdf.setTimeZone(timeZone);
	        return sdf.format(date);
	    }
	    
	    /**
	     * 获取当前系统时区，返回值是字符串，因为如果是整型的话，无法兼顾UTC -3：30或者是UTC +8:30时区
	     * @return
	     */
	    public static String getTimeZone(){
	    	 Calendar cal = java.util.Calendar.getInstance();
			 TimeZone timeZone = cal.getTimeZone();
			 int i = timeZone.getRawOffset();
		
			   BigDecimal rawOffset = new BigDecimal(i);
			   BigDecimal b2 = new BigDecimal(3600000);
			   BigDecimal timeZone_bigdecimal = rawOffset.divide(b2, 2,BigDecimal.ROUND_HALF_UP);
			   String timeZoneStr= String.valueOf(timeZone_bigdecimal);
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
		    	return zone;
	    }
	 
	    
    }