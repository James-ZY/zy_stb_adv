package com.gospell.aas.webservice.rest.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.gospell.aas.common.mapper.BeanMapper;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.stb.Constant4Park;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.sys.User;

/**
 * 处理返回给客户端数据工具类
 * 
 * @author longxy
 * @date 2015-3-13 下午3:26:49
 * @version V1.0
 */
public class HandelReturnAppResult {

	/**
	 * 处理服务端返回的集合数据
	 * 
	 * @param pageList  服务端的结果集
	 * @param appNotNeedValue  客户端不需要的参数集
	 * @param stringToTimeValues  字符串转换成long型的参数列集
	 * @param message  错误提示信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String handelList(Object pageList, String[] appNotNeedValues, 
			String[] stringToTimeValues, String message){
		JsonMapper mapper = JsonMapper.getInstance();  // 初始化 
        JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
        String listStr = mapper.toJson(pageList);  // 转为json字符串
        Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象
        
        // 删除map对象中不需要的值
        mapRes.remove("html");  
        mapRes.remove("datas");
        
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) mapRes.get("list");
        
        // 删除集合中不需要的值
        if(appNotNeedValues.length > 0 && null != mapList){
        	for(Map<String, Object> map : mapList){
        		for(String value : appNotNeedValues){
            		map.remove(value);  // 删除集合中不需要的值
            	}
        	}
        }
        
        // 将服务端结果集中的时间字符串成long型
        if(stringToTimeValues.length > 0 && null != mapList){
        	for(String value : stringToTimeValues){
        		for(Map<String, Object> map : mapList){
        			String val = (String)map.get(value);
        			if(StringUtils.isNotBlank(val)){  // 值不为空
            			map.put(value, DateUtils.parseDate(val).getTime());
            		}else{
            			map.put(value, 0);
            		}
        		}
        	}
        }
        
        mapRes.put("list", mapList);
        mapRes.put("status", Constant4Park.OK);  // 状态码
        mapRes.put("message", message);  // 描述信息
        return mapper.toJson(mapRes);
	}
	
	/**
	 * 处理服务端返回的单个数据
	 * 
	 * @param serverResult  服务端的结果集
	 * @param appNotNeedValues  客户端不需要的参数列集
	 * @param stringToTimeValues  字符串转换成long型的参数列集
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String handelSingle(Object serverResult, 
			String[] appNotNeedValues, String[] stringToTimeValues){
		JsonMapper mapper = JsonMapper.getInstance();  // 初始化 
        JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
        String listStr = mapper.toJson(serverResult);  // 转为json字符串
        Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象
        
        Map<String, Object> map = (Map<String, Object>) mapRes.get("content");
        
        // 删除集合中不需要的值
        if(appNotNeedValues.length > 0){
    		for(String value : appNotNeedValues){
        		map.remove(value);  
        	}
        }
        
        // 将服务端结果集中的时间字符串成long型
        if(stringToTimeValues.length > 0){
        	for(String value : stringToTimeValues){
        		String val = (String)map.get(value);
        		if(StringUtils.isNotBlank(val)){  // 值不为空
        			map.put(value, DateUtils.parseDate(map.get(value)).getTime());
        		}else{
        			map.put(value, 0);
        		}
        	}
        }
        
        mapRes.put("content", map);
		return mapper.toJson(mapRes);
	}
	
	/**
	 * 自定义错误提示信息
	 * 
	 * @param status
	 * @param message
	 * @return
	 */
	public static String result(String status, String message){
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("message", message);
		
		JsonMapper mapper = JsonMapper.getInstance();
		return mapper.toJson(map);
	}
	
	/**
	 * 模拟客户端登录
	 * 因为客户端是无状态的 用户每次访问时 必须要登录
	 * 
	 * @param rquest
	 * @param sysService
	 * @param userService
	 */
	/*public static void login(HttpServletRequest request , SystemService sysService,
			UserService userService){
		
		String mobile = "18349397543";
        String userPassword = "admin";

        Subject subject = SecurityUtils.getSubject();

        String host = request.getRemoteHost();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile, userPassword.toCharArray(), false, host, "");

        // Shiro登录
        subject.login(token);

        User user = sysService.getUserByMobile(mobile);
        userService.updateUserLoginInfo(user.getId()); // 更新登陆ip和时间
        UserUtils.putCache(UserUtils.CACHE_USER, user);
	}*/
	
	/**
	 * 判定当前用户登录是否失效
	 * 
	 * @return
	 */
	public static boolean loginAuth(){
		
		User user = UserUtils.getUser();
		
		System.out.println("id  " + user.getId());
		
		if(user.getId() == null){  // 登录失效
			return true;
		}else{  // 已经登录
			return false;
		}		
	}
	
	/**convert 2 DTO format  
	 * @param <T>
	 * @return */
	public static <T> String handel2DTOList(Page<?> pageList, Class<T> DTO, String message){	
		JsonMapper mapper = JsonMapper.getInstance(); 
        JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
        String listStr = mapper.toJson(pageList);  // 转为json字符串
        Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象
             
        mapRes.remove("datas");
        
        
        List<T> dtoList = BeanMapper.mapList(pageList.getList(), DTO); 
         
        mapRes.put("list", dtoList);
        mapRes.put("status", Constant4Park.OK);  // 状态码
        mapRes.put("message", message);  // 描述信息
        return mapper.toJson(mapRes);
	}
	
	@SuppressWarnings("unchecked")
	public static String handelSingleDTO(Object serverResult){
		JsonMapper mapper = JsonMapper.getInstance();  // 初始化 
        JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
        String listStr = mapper.toJson(serverResult);  // 转为json字符串
        Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象
        
        Map<String, Object> map = (Map<String, Object>) mapRes.get("content");
        mapRes.put("content", map);
		return mapper.toJson(mapRes);
	}
	
	/**
	 * 获取发送短信时的返回码
	 * 
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getRespCode(String message){
		JsonMapper mapper = JsonMapper.getInstance();
		JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
		Map<String, Object> mapRes = mapper.fromJson(message, javaType);
		String respCode= ((Map<String, String>)mapRes.get("resp")).get("respCode");
		return respCode;
	}
	
	/**
	 * 计算地球上两点间的直线距离
	 * 
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(String lon_1, String lat_1, String lon_2, String lat_2){
		int radius = 6378140;
		double lon1 = Double.parseDouble(lon_1);
		double lat1 = Double.parseDouble(lat_1);
		double lon2 = Double.parseDouble(lon_2);
		double lat2 = Double.parseDouble(lat_2);
		double d = (2*Math.atan2(Math.sqrt(Math.sin((lat1-lat2)*Math.PI/180/2)  
		        *Math.sin((lat1-lat2)*Math.PI/180/2)+  
		        Math.cos(lat2*Math.PI/180)*Math.cos(lat1*Math.PI/180)  
		        *Math.sin((lon1-lon2)*Math.PI/180/2)  
		        *Math.sin((lon1-lon2)*Math.PI/180/2)),  
		        Math.sqrt(1-Math.sin((lat1-lat2)*Math.PI/180/2)  
		        *Math.sin((lat1-lat2)*Math.PI/180/2)  
		        +Math.cos(lat2*Math.PI/180)*Math.cos(lat1*Math.PI/180)  
		        *Math.sin((lon1-lon2)*Math.PI/180/2)  
		        *Math.sin((lon1-lon2)*Math.PI/180/2))))*radius;
		
		return d;
	}
	
}
