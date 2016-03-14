package com.stock.common.util.response;

import java.io.Serializable;

public abstract interface IDefineMsg extends Serializable{
	
	/** IResult 错误代码 */
	public static final String CODE_ERROR 							= "0";
	/** IResult 正确代码 */
	public static final String CODE_SUCCESS 						= "1";
	
	/**状态标签  1可用*/
	public static final String STATUS								= "1";
	/**男*/
	public static final String MAN									= "男";
	
	/** 系统异常，请联系管理员 */
	public static final String SYSTEM_EXCEPTION 					= "系统异常，请联系管理员";
	/** redis异常 */
	public static final String REDIS_EXCEPTION						= "redis异常";
	/** 数据库访问异常，请稍后重试 */
	public static final	String SQL_DB_EXCEPTION						= "数据库访问异常，请稍后重试";
	/**您的账号已冻结，请联系管理员*/
	public static final String ACCOUNT_FREEZED						= "您的账号已冻结，请联系管理员";
	
	/** xx不能为空 */
	public static final String LACK_PARAM							= "不能为空";
	/** 查询成功 */
	public static final String GET_SUCCESS							= "查询成功";
	/** 删除成功*/
	public static final String DEL_SUCCESS							= "删除成功";
	/** 添加成功 */
	public static final String ADD_SUCCESS							= "添加成功";
	/** 修改成功 */
	public static final String UPD_SUCCESS							= "修改成功";
	
	/** 删除失败*/
	public static final String DEL_FAIL								= "删除失败";
	/** 添加失败 */
	public static final String ADD_FAIL								= "添加失败";
	/** 修改失败 */
	public static final String UPD_FAIL								= "修改失败";
	
	/** 查询失败 */
	public static final String GET_FAIL								= "查询失败";
	/** 操作失败*/
	public static final String USE_FAIL								= "操作失败";
	/** 参数异常*/
	public static final String PARAM_EXCEPTION						= "参数异常";
	/** 操作成功*/
	public static final String USE_SUCCESS							= "操作成功";
	/** 校验成功*/
	public static final String CHEACK_SUC							= "校验成功";
	
	/**请求Url失败 */
	public static final String URL_REQEST_ERR						= "请求url失败";
	/**请求Url成功 */
	public static final String URL_REQEST_SUC						= "请求url成功"; 
	
	/**发送短信验证码成功*/
	public static final String PHONE_CODE_SUC						= "短信验证码";
	/**发送验证码失败*/
	public static final String PHONE_CODE_ERR						= "发送验证码失败";
	/**请重新获取验证码*/
	public static final String PHONE_CODE_GET						= "请重新获取验证码";
	/**手机验证码*/
	public static final String PHONE_CODE_INFO						= "手机验证码";
	/**手机验证码错误*/
	public static final String PHONE_CODE_IS_FAIL					= "手机验证码错误";
	
	/**手机号错误*/
	public static final String PHONE_ERR							= "手机号码错误";
	/** xx格式错误*/
	public static final String FORMAT_ERR							= "格式错误";
	
	/**登陆帐号或密码错误*/
	public static final String LOGIN_ERR							= "登陆帐号或密码错误";
	/**交易密码错误*/
	public static final String TRANSACTION_PASSWORD_ERR				= "交易密码错误";
	
	/**登陆成功*/
	public static final String LOGIN_SUC							= "登陆成功";
	/**交易成功*/
	public static final String TRANSACTION_SUC						= "交易成功";
	/**请登录*/
	public static final String LONGIN_PREASE						= "请登录";
	
	/**密码格式错误*/
	public static final String PASSWORD_BY_FORMAT					= "密码格式错误";
	/**密码错误*/
	public static final String PASSWORD_FAIL						= "密码错误";
	/**两次密码输入不一致*/
	public static final String PASSWORD_IS_DIFFRENT					= "两次密码输入不一致";
	
	/** 用户缓存信息*/
	public static final String ADMIN_USER_KEY						= "ADMIN_USER_KEY";
	/** 用户缓存信息key*/
	public static final String USER_KEY 							= "user_key";
	
	
	
	
	/************返回参数类型*************/
	public static final int WS_TYPE_NULL 							= 0;
	public static final int WS_TYPE_INT 							= 1;
	public static final int WS_TYPE_STRING 							= 2;
	public static final int WS_TYPE_OBJECT 							= 3;
	public static final int WS_TYPE_LISTMAP 						= 4;
	public static final int WS_TYPE_MAPMAP 							= 5;
	public static final int WS_TYPE_MULLIST 						= 6;
	public static final int WS_TYPE_LISTOBJECT 						= 7;
	public static final int WS_TYPE_STREAM 							= 8;
	public static final int WS_TYPE_JSON 							= 9;	
	
	
	public static final String KEY_RETURN_CODE 						= "recode"; // 返回代码，map键定义 // 1：返回成功，0：返回失败
	
	public static final String KEY_RETURN_MSG 						= "remsg";// 返回信息，map值定义
}