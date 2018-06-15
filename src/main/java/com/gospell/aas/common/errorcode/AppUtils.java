package com.gospell.aas.common.errorcode;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JavaType;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Parameter;

/**
 * @author fanhb 2014年12月30日9:51:12 用枚举，方便获取实例，调用代码更简洁，可读性更强
 */

public enum AppUtils {

	GI;

	/**
	 * 处理single,list返回结果为map,
	 * 
	 */
	public Map<String, Object> handlerResultReturnMap(Object obj) {
		JsonMapper mapper = JsonMapper.getInstance();
		JavaType mapType = mapper.contructMapType(Map.class, String.class,
				Object.class);

		if (obj == null) {
			return getMap(ErrorCode4App.ERROR_NO_204,
					ErrorCode4App.ERROR_NO_204_VALUE);
		}
		String json = mapper.toJson(obj);
		Map<String, Object> maps = mapper.fromJson(json, mapType);
		maps.put(ErrorCode4App.ERROR_CODE, ErrorCode4App.ERROR_NO_200);
		maps.put(ErrorCode4App.ERROR_MSG, ErrorCode4App.ERROR_MSG_VALUE);
		return maps;
	}

	public Map<String, Object> returnErrorNumberReturnMap(Integer errNumber,
			String status) {
		Map<String, Object> map = new HashMap<>();
		map.put(ErrorCode4App.ERROR_CODE, errNumber);
		map.put(ErrorCode4App.ERROR_MSG, errNumber);
		return map;
	}

	public Map<String, Object> errorCodeReturnMap(String errorCode4App,
			String status, String... name) {
		Map<String, Object> map = new HashMap<>();
		if (name == null || name.length == 0) {
			map.put(ErrorCode4App.ERROR_CODE, errorCode4App);
			map.put(ErrorCode4App.ERROR_MSG, status);
			return map;

		} else {

			map.put(ErrorCode4App.ERROR_CODE, errorCode4App);
			map.put(ErrorCode4App.ERROR_MSG, status);
			map.put(name[0], name[1]);
			return map;
		}
	}

	/**
	 * @return 返回一个Map，便与restful层使用，来封装appendixes
	 */
	public Map<String, Object> getMap(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	/**
	 * -----放入（put）手写sql的方法 (Handle returns of findBySql.)
	 */
	public Parameter handleSqlArgs(Object[] val, String... keyVal) {
		Parameter pa = new Parameter();
		if (val != null && keyVal != null) {
			int vals = val.length;
			int keyVals = val.length;
			pa.put("promote", "key: " + keyVals + "个;value: " + vals + "个");
			if (vals != keyVals) {
				return pa;
			}
			for (int i = 0; i < keyVal.length; i++) {
				pa.put(keyVal[i], val[i]);
			}
		}
		return pa;
	}

	/**
	 * 检查从 Map<String, Object> 中获取的参数是否为"",null," ";
	 */
	public boolean isNull(String str) {
		if (org.apache.commons.lang3.StringUtils.isBlank(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("serial")
	public class ParameterSqlArgs extends Parameter {
		/**
		 * -----放入（put）手写sql的方法（findBySql）需要的参数----
		 */
		@Override
		public ParameterSqlArgs put(String key, Object value) {
			if (!isNull(value + "")) {
				super.put(key, value);
			}
			return this;
		}

	}

	// ---------------------

	Client client = ClientBuilder.newClient();
	public  String get(String lat1, String lon1, String lat2, String lon2) {
		// api.map.baidu.com/direction/v1
		// ?origin=39.915285,116.403857&destination=40.056878,116.30815
		// &region=中国&output=json&ak=WluW9bgRlaYOLLAegWgTD3Ln
		WebTarget target = client
				.target("http://api.map.baidu.com/direction/v1");
		Response response = target.queryParam("origin", lat1 + "," + lon1)
				.queryParam("destination", lat2 + "," + lon2)
				.queryParam("region", "中国").queryParam("output", "json")
				.queryParam("ak", "WluW9bgRlaYOLLAegWgTD3Ln")
				.request(MediaType.APPLICATION_JSON).get();
		String distence = response.readEntity(String.class);
		// post(Entity.entity(obj, MediaType.APPLICATION_JSON));
//		System.out.println("-----------response响应信息-------------" + response);
//		System.out.println("-----------客户端返回信息----------------"
//				+ response.readEntity(String.class));
		response.close();
		return distence;
		// 30.564335,104.070223 30.648382,104.076547
	}

}
