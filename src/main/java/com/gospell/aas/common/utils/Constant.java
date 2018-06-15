package com.gospell.aas.common.utils;

public class Constant {

	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String SUCCESS_ZH = "成功";
	public static final String FAILURE_ZH = "失败";
	// 默认分页数
	public static final String PAGE_SIZE = "20";
	// 默认日期格式
	public final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 默认排序
	public static final String SORT_TYPE = "auto";

	// 认证
	// public static final String reloginsession="shiro-reloginsession";
	// 认证
	public static final String authenticationCacheName = "shiro-authenticationCacheName";
	// 授权
	public static final String authorizationCacheName = "shiro-authorizationCacheName";

	// HTTP 方法
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";
	public static final String POST = "POST";

	// 广告类型
	public static final Integer TEXT = 0;

	// 时间默认值
	public static final String START_TIME = "00:00";

	public static final String END_TIME = "24:00";

	public static final Integer ZERO = 0;

	public static final Integer ONE = 1;

	public static final Integer TWO = 2;

	public static final Integer THREE = 3;
	
	
	/**
	 * 广告类型
	 */
	public static final Integer ADV_TEXT_TYPE = 1;

	public static final Integer ADV_IMAGE_TYPE = 2;

	public static final Integer ADV_VEDIO_TYPE = 3;
	/**
	 * 区域限制
	 */

	public static final Integer AREA_TYPE_NO_LIMIT = 0;

	public static final Integer AREA_TYPE_LIMIT = 1;
	
	/**
	 * 部门限制
	 */

	public static final Integer ORG_TYPE_NO_LIMIT = 0;

	public static final Integer ORG_TYPE_LIMIT = 1;
	/**
	 * 设备限制
	 */

	public static final Integer EQU_TYPE_NO_LIMIT = 0;

	public static final Integer EQU_TYPE_LIMIT = 1;
	
	/**
	 * 广告平台
	 */
	public static final Integer ADV_ADPLATFORM_WIFI = 1;

	public static final Integer ADV_ADPLATFORM_OTT = 2;

	/**
	 * 起始时间
	 */
	public static final Integer TIME_START = 0;

	public static final Integer TIME_END = 23;
	
	
	/**
	 * 区域类型
	 */
	public static final int AREA_COUNTYR = 1;

	public static final int AREA_PROVINCE = 2;

	public static final int AREA_CITY = 3;

	public static final int AREA_DISTRICT = 4;
	
	
	/**
	 * 广告投放设置
	 */
	public static final Integer ADV_PUT_SET_DAY = 0;

	public static final Integer ADV_PUT_SET_WEEK = 1;

	public static final Integer ADV_PUT_SET_MONTH = 2;
	
	/**
	 * 定向投放
	 */
	public static final Integer DRIECT_TYPE_AREA = 0;

	public static final Integer DRIECT_TYPE_ORG = 1;

	public static final Integer DRIECT_TYPE_EQU = 2;
	/**
	 * 广告状态
	 */
	public static final Integer ADV_STATUS_ISSUE = 0;
	public static final Integer ADV_STATUS_PASS = 1;
	public static final Integer ADV_STATUS_NO_PASS = -1;
	public static final Integer ADV_STATUS_SUSPEND= 2;
	public static final Integer ADV_STATUS_PUT = 3;
	public static final Integer ADV_STATUS_END = 4;
	
	/**
	 * 广告播放的时间
	 */
	public static final Integer ADV_PLAY_TYPE_BEFORE=0;
	public static final Integer ADV_PLAY_TYPE_MIDDLE = 1;
	
	//下传广告类型
	public static final Integer ADV_TYPE_RSS = 1;
	public static final Integer ADV_TYPE_VEDIO = 2;
	
	/**
	 * 下传广告的接口地址
	 */
	public static final String ADV_PUSH_AREA = "/advpush/byarea";
	public static final String ADV_PUSH_EQU = "/advpush/byeqp";
	public static final String ADV_PUSH_ORG = "/advpush/byorg";
	public static final String ADV_PUSH_NO_LIMIT = "/advpush/bynothing";
	
	/**
	 * 下传接口返回状态
	 */
	public static final String ADV_PUSH_SUCCESS = "OK";
	public static final String ADV_PUSH_FAIL = "FAIL";


	//接口状态
	
	public static final String STATUS_OK = "200";//成功状态,创建成功结果的时候自动设置
	public static final String STATUS_BAD_REQUEST = "400";//错误的请求,参数不正确,如果没有更精确的状态表示,使用此状态.
	public static final String STATUS_ERROR = "404";//错误状态
	public static final String STATUS_INTERNAL_SERVER_ERROR = "500";//服务器内部错误,如果没有更精确的状态表示,使用此状态
			public static final String UNKOWN_ERROR = "600";//未知错误
	public static final String STATUS_NOT_EXIST_ERROR = "700";//数据不存在
	public static final String STATUS_EXIST_ERROR = "800";//数据已经存在
	public static final String STATUS_BLANK_PARAMETER = "900";//参数值为空
	
	
	//换台广告位置
 
    // 缓存用户最后有效的登陆sessionId
    public static final String keeponeCacheName = "shiro-keepone-session";
    
    // 踢出用户后的错误提示
    public static final String keeponeError = "keeponeError";
    
    // 踢出用户的提示信息是否存在标识
    public static final String kperrorFlag = "kperrorFlag";
    
    public static final Integer AFTER_WEEK=6;//广告一周以后的时间
	 
}
