package com.stock.common.util.response;

import java.io.Serializable;

public abstract interface IDefineMsg extends Serializable{
	
	public static final String KEY_RETURN_CODE 						= "recode"; // 返回代码，map键定义 // 1：返回成功，0：返回失败
	
	public static final String KEY_RETURN_MSG 						= "remsg";// 返回信息，map值定义
	
	/** IResult 错误代码 */
	public static final String CODE_ERROR 							= "0";
	/** IResult 正确代码 */
	public static final String CODE_SUCCESS 						= "1";
	/** 系统异常，请联系管理员 */
	public static final String SYSTEM_EXCEPTION 					= "系统异常，请联系管理员";
	/** redis异常 */
	public static final String REDIS_EXCEPTION						= "redis异常";
	/** 数据库访问异常，请稍后重试 */
	public static final	String SQL_DB_EXCEPTION						= "数据库访问异常，请稍后重试";
	/** 缺少入参 */
	public static final String LACK_PARAM							= "缺少入参";
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
	
	/**请求Url失败 */
	public static final String URL_REQEST_ERR						= "请求url失败";
	/**请求url成功 */
	public static final String URL_REQEST_SUC						= "请求url成功"; 
	
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
}