package com.gospell.aas.webservice.rest.util;

import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.gospell.aas.common.mapper.BeanMapper;
import com.gospell.aas.common.mapper.JsonMapper;
 

public class ResultUtil {

	/**
	 * 获取到map对象，里面封装了Result的相关信息
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getMap(String jsonStr) {
		JsonMapper jsonMapper = JsonMapper.getInstance();
		JavaType javaType = jsonMapper.contructMapType(Map.class, String.class,
				Object.class);
		return jsonMapper.fromJson(jsonStr, javaType);
	}
	/**
	 * 解析map对象
	 * @param object
	 * @param destinationClass
	 * @return
	 */
	public static <T> T toObject(Object object, Class<T> destinationClass){
		if(null == object)
			return null;
		return BeanMapper.map(object, destinationClass);
	}
}
