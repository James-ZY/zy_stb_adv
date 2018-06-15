package com.gospell.aas.common.utils;


/**
 * <p> 毫秒时间转字符串 </p>
 * 
 */
public class MsecToTimeStr {
	
	public static String msecToStr(long timeMsec) {

		String type = "";
		String hour = " 时";
		String min = " 分";
		String sec = " 秒";

		long time = (long) Math.ceil(timeMsec / 1000);

		if (time < 3600 && time >= 60) {
			if (time % 60 != 0) {
				type = (long)Math.floor(time / 60) + min + time % 60 + sec;
			} else {
				type = time / 60 + min;
			}
		} else if (time >= 3600) {
			if (time % 3600 != 0 && time % 60 == 0) {
				type = (long)Math.floor(time / 3600) + hour
						+ (long)Math.floor((time % 3600) / 60) + min;
			} else if (time % 3600 != 0 && time % 60 != 0) {
				type = (long)Math.floor(time / 3600) + hour
						+ (long)Math.floor((time % 3600) / 60) + min + (time % 3600)
						% 60 + sec;
			} else {
				type = (long)Math.floor(time / 3600) + hour;
			}
		} else if (time < 0) {
			type = 0 + sec;
		} else {
			type = time + sec;
		}

		return type;
	}
}
