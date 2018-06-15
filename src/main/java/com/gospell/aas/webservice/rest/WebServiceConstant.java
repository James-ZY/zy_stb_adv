package com.gospell.aas.webservice.rest;

import com.gospell.aas.common.config.Global;

public class WebServiceConstant {
	
	public static String ADV_AREA = Global.getConfig("equ.rest.addr")+"adv/area";

	public static String ADV_ORG = Global.getConfig("equ.rest.addr")+"adv/org";
	
	public static String ADV_EQU = Global.getConfig("equ.rest.addr")+"adv/equ";
	
	public static String ADV_OTT = Global.getConfig("adv.push.ott")+"ottserver/adPlatform/saveAds";
	
	public static String SUCCESS = "200";
	
	
}
