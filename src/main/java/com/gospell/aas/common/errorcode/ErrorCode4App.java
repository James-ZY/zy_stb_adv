package com.gospell.aas.common.errorcode;

/**
 * cdcy 通用常量类
 */
public class ErrorCode4App {
	
	public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";
    public static final String POST = "POST";
    
    
    
    // cdcy 会话标识
    public static final String SESSION = "session";
	
	public static final String ERROR_CODE = "ERROR_CODE";  	// 消息提示的key
	public static final String ERROR_PROMPTE = "ERROR_PROMPTE";  	// 消息提示的key
	public static final String ERROR_PROMPTE_VALUE = "noLengh";  	// 消息提示的key
	public static final String ERROR_MSG = "ERROR_MSG";  	// 消息提示值的key
	public static final String ERROR_MSG_VALUE = "操作成功";  
	public static final String ERROR_NO_200 = "200";  		// 正常
	public static final String ERROR_NO_200_VALUE = "操作成功"; 
	public static final String ERROR_NO_204 = "204";  		// 没有获取到app端需要的信息
	public static final String ERROR_NO_204_VALUE = "亲，没有更多数据了";  	// 没有获取到app端需要的信息
	public static final String ERROR_NO_401 = "401";  		// 未授权
	public static final String ERROR_NO_401_VALUE = "登录失效/未登陆";  	// 未授权
	public static final String ERROR_NO_403 = "403";  		// 没有权限访问
	public static final String ERROR_NO_403_VALUE = "没有权限访问";  
	public static final String ERROR_NO_400 = "400";  		// 400参数错误，请求操作错误（再次审核）
	public static final String ERROR_NO_400_REPEAT_APPROVAL = "请求错误，可能重复审核";  // 400参数错误，请求操作错误（再次审核）
	public static final String ERROR_NO_411 = "411";  		// 400参数错误，请求操作错误（再次审核）
	public static final String ERROR_NO_411_VALUE = "数据不能为空";  // 400参数错误，请求操作错误（再次审核）
	public static final String ERROR_NO_500 = "500";  		// 服务端异常
	public static final String ERROR_NO_500_VALUE = "服务器异常";  	// 错误码500的提示信息
	
	
}
