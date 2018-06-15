package com.gospell.aas.common.stb;

public class Constant4Park {
	   public static final String USER_TYPE_USER = "3";// 消费者

	    public static final String USER_COMPANY_ID = "1";// 用户公司id
	    public static final String USER_OFFICE_ID = "2";// 用户部门id

	    // 角色定义
	    public static final String ROLE_ID_PARK = "park";// 停车场
	    public static final String ROLE_ID_CONSUMER = "consumer";// 消费者
	    public static final String ROLE_ID_ADMIN = "admin";// 超级管理员

	    // 角色定义名字
	    public static final String ROLE_ID_PARK_NAME = "停车场";// 停车场
	    public static final String ROLE_ID_CONSUMER_NAME = "消费者";// 消费者
	    public static final String ROLE_ID_ADMIN_NAME = "超级管理员";// 超级管理员

	    // 定义图片默认路径
	    public static final String PATH_USER_PHOTO = "/mobile/images/user/photo/";  // 存放用户头像的目录
	    public static final String DEFAULT_USER_PHOTO = "/park/mobile/images/user/photo/user.png";  // 用户 默认头像
	    public static final String PATH_CAR_PHOTO = "/mobile/images/car/";  // 存放车辆照片的目录
	    public static final String PATH_ORDERS_PHOTO = "/mobile/images/orders/";  // 存放进出场车辆照片的目录
	    
	     //订单状态 by ph
	    public static final int  ORDER_BOOK = 1;
	    public static final int  ORDER_ENTER_ENTRANCE = 2;
	    public static final int  ORDER_ENTER_FINISH = 3;
	    public static final int  ORDER_NOFEE_FINISH = 4;
	    
	    public final static int REQ_REGISTER = 0x01;
		public final static int REQ_PASSWAY = 0x02;
		public final static int REQ_HEARTBEAT = 0x03;
		
		public final static int REQ_LOCKER_REGISTER = 50;
		public final static int REQ_LOCKER_ACTION = 51;
		public final static int REQ_LOCKER_HEARTGBEAT = 52;
	    
	    //道闸类型
	    public static final int  PASSAGEWAY_ENTRANCE = 1;
	    public static final int  PASSAGEWAY_EXIT = 0;

	    // 常用错误提示
	    /**
	     * 成功
	     */
	    public static final String OK = "200"; 
	    /**
	     * 错误的请求
	     */
	    public static final String BAD_REQUEST = "400"; 
	    /**
	     * 错误状态
	     */
	    public static final String ERROR = "404"; 
	    /**
	     * 服务器内部错误
	     */
	    public static final String INTERNAL_SERVER_ERROR = "500"; 
	    /**
	     * 服务器内部错误提示信息
	     */
	    public static final String INTERNAL_SERVER_ERROR_MSG = "服务器内部错误"; 
	    /**
	     * 未知错误
	     */
	    public static final String UNKOWN_ERROR = "600"; 
	    /**
	     * 数据不存在
	     */
	    public static final String NOT_EXIST_ERROR = "700";
	    /**
	     * 登录失效
	     */
	    public static final String NOT_EXIST_ERROR_MSG = "您的登录已失效，请重新登录！";
	    /**
	     * 数据已经存在
	     */
	    public static final String EXIST_ERROR = "800"; 
	    /**
	     * 数据值为空
	     */
	    public static final String BLANK_PARAMETER = "900"; 
	    
	    
	    /**
	     *  邀请奖励，默认为20积分
	     */
	    public static final int INVITE_REWARD = 20; 
	    /**
	     * 邀请码长度，默认为6
	     */
	    public static final int CODE_LENGTH = 6; 
	    /**
	     * 签到的最小人品值，默认为1
	     */
	    public static final int SIGNATURE_MIN_PERSONALITY = 1;
	    /**
	     * 签到的最大人品值，默认为50
	     */
	    public static final int SIGNATURE_MAX_PERSONALITY = 50;
	    
	    
	  //道闸状态
	    public static final int  STATE_OK = 0;
	    public static final int  STATE_IPC = 1;
	    public static final int  STATE_PASSAGEWAY = 2;
}
