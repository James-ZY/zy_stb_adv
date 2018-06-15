package com.gospell.aas.common.utils;

import com.gospell.aas.common.config.Global;

public class PropertiesReadUtil {
	
	/**
	 * 滚动广告中文的字体
	 */
	public static final String ROLL_CHINESE_FONT = Global.getConfig("roll.chinese.text.font");
	
	/**
	 * 滚动广告其他语言的字体
	 */
	public static final String ROLL_OTHER_LANAGUAGE_FONT = Global.getConfig("roll.other.text.font");

}
