package com.gospell.aas.webservice.rest.util;

import java.util.Map;

 

public class ClientRespond <T>{
	public static String getStatus(String jsonstr){
		Map<String, Object> maplsit = ResultUtil.getMap(jsonstr);
		return (String)maplsit.get("status");
	}
	
	public static String getMessage(String jsonstr){
		Map<String, Object> maplsit = ResultUtil.getMap(jsonstr);
		return (String)maplsit.get("message");
	}
	
	public static <T> T getRespondObj(String jsonstr, Class<T> destinationClass){
		Map<String, Object> maplsit = ResultUtil.getMap(jsonstr);
		return ResultUtil.toObject(maplsit.get("content"), destinationClass);
	}
}