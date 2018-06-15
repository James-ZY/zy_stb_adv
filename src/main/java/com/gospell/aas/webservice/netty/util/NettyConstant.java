package com.gospell.aas.webservice.netty.util;

/**
 * 用于设置netty所需的常量
 * @author Administrator
 *
 */
public class NettyConstant {
	
	public static final String REQ_HEART="100";//心跳的命令
	
	 
	public static final String REP_DELETE_ADV="201";//删除广告
	
	public static final String REP_DELETE_ADCOMBO_FROM_SERVER = "601";// 播控服务器请求把删除的广告套餐下放到客户端

	public static final String RESP_DELETE_ADCOMBO_FROM_SERVER = "R601";// 返回播控服务器请求把删除的套餐命令
	
	public static final String REP_PLAY_NOW_FROM_SERVER = "501";// 播控服务器紧急插播广告的命令

	public static final String RESP_PLAY_NOW_FROM_SERVER  = "R501";// 播控服务器紧急插播服务器的命令返回
	
	public static final String REP_DELETE_NOW_FROM_SERVER = "401";// 播控服务器紧急停播广告的命令

	public static final String RESP_DELETE_NOW_FROM_SERVER  = "R401";// 播控服务器紧急停播服务器的命令返回
	
	
	public static final String REP_PUSH_ADCOMBO_FROM_SERVER = "701";// 播控主动推送单条套餐到netty
																	// server的命令
	public static final String RESP_PUSH_ADCOMBO_FROM_SERVER = "R701";// netty server返回播控主动推送单条套餐的命令
	public static final String REP_DELETE_ADV__FROM_SERVER = "801";// 播控服务器请求把删除的广告下放到客户端

	public static final String RESP_DELETE_ADV_FROM_SERVER = "R801";// 播控服务器请求把删除的广告下放到客户端

	public static final String REP_PUSH_ADV_FROM_SERVER = "901";// 播控主动推送单条广告到netty
																// server的命令

	public static final String RESP_PUSH_ADV_FROM_SERVER = "R901";// netty
	 
	public static final String REP_PUSH_DEFAULTADV_FROM_SERVER = "902";// 播控主动推送单条默认开机画面广告到nettyserver的命令

    public static final String RESP_PUSH_DEFAULTADV_FROM_SERVER = "R902";// netty
    
    public static final String REQ_ADELEMENT_DISTRICT_INFO = "1401";//  请求该发送器的区域关系信息
	
	public static final String RESP_ADELEMENT_DISTRICT_INFO = "R1401";// 反馈指定发送器的区域关系信息
	
	public static final String RESP_BAD_REQUEST="R404";//请求错误
	
	public static final String BAD_REQUEST = "400";
 
	public static final String NOT_FOUND = "404";//服务器请求失败
	
	public static final String OK = "200";//服务器请求成功
	
	public static final String INTERNAL_SERVER_ERROR = "500";// 服务器内部错误,如果没有更精确的状态表示,使用此状态.
	
	public static final String UNKOWN_ERROR = "600";//未知错误
	
	public static final String NOT_EXIST_ERROR = "700";//数据不存在
	
	public static final String EXIST_ERROR = "800";//数据已经存在
	
	public static final String BLANK_PARAMETER = "900";//参数值为空
	
	public static final Integer PUT_NUMBER = 20;
	 
 
}
