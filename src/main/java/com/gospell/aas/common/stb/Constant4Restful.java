package com.gospell.aas.common.stb;

import com.gospell.aas.common.config.Global;

public class Constant4Restful {
	/**
	 * 服务器地址
	 */
	public static final String  SERVER_ADDR = Global.getConfig("rest.addr");
	
	/**
	 * 区域rest接口地址
	 */
	public static final String  AREA_REST_ADDR = Global.getConfig("rest.addr")+"/area";
	
	/**
	 * 获取所有区域路径
	 */
	public static final String FIND_ALL_AREAS = "findAll";
	
}
