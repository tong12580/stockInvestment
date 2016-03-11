/**
 * Filename:	SessionUtil.java
 * Description:
 * Copyright:   Copyright (c)2012
 * Company:    easy
 * @author:     guosheng.zhu
 * @version:    1.0  
 * Create at:   2012-3-19 上午10:39:04  
 *  
 * Modification History:  
 * Date           Author       Version      Description  
 * ------------------------------------------------------------------  
 * 2012-3-19    guosheng.zhu       1.0        1.0 Version
 */
package com.stock.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stock.common.util.response.ServiceAction;

import redis.clients.jedis.Jedis;


/**
 * @ClassName: SessionUtil
 * @Description: session 统一管理处理
 * @author guosheng.zhu
 * @date 2012-3-19 上午10:39:04
 */
public class SessionUtil extends ServiceAction {
	public static final String COOKIE_USER_KEY = "UserKey";
	public static final String ADMIN_USER_KEY = "AdminUserKey";
	public static final int TIMEOUT = 30 * 24 * 3600; //保存30天
	
	public static final String SESSION_TIMEOUT = "SESSTION_TIMEOUT";
	
	
	/**
	* @Title: setSessionAttributeString 
	* @Description: 保存会话变量 
	* @param @param jedis
	* @param @param request
	* @param @param response
	* @param @param name
	* @param @param value  
	* @return void
	 */
	public static void setSessionAttributeString(HttpServletRequest request, 
			HttpServletResponse response, String name, String value) {
		
		String sessionKey = getSessionKey(request, response);
		String timeout = (String)RedisUtils.get(sessionKey + SESSION_TIMEOUT);
		int iTimeout = TIMEOUT;
		try {
			iTimeout = Integer.parseInt(timeout);
		} catch (Exception e) {
		}
		if (isNotEmpty(sessionKey)) {
			RedisUtils.set(sessionKey + name, value, iTimeout);
		}
	}
	
	
	
	/**
	* @Title: setSessionAttribute 
	* @Description: 保存会话变量 
	* @param @param jedis
	* @param @param request
	* @param @param response
	* @param @param name
	* @param @param value
	* @param @throws Exception  
	* @return void
	 */
	public static void setSessionAttribute(HttpServletRequest request, 
			HttpServletResponse response, String name, Object value) throws Exception {
		
		String sessionKey = getSessionKey(request, response);
		
		String timeout = (String)RedisUtils.get(sessionKey + SESSION_TIMEOUT);
		int iTimeout = TIMEOUT;
		try {
			iTimeout = Integer.parseInt(timeout);
		} catch (Exception e) {
		}
		
		if (isNotEmpty(sessionKey)) {
			RedisUtils.set(sessionKey + name, value,iTimeout);
		}
	}
	
	
	
	/**
	* @Title: getSessionAttributeString 
	* @Description: 保存会话变量 
	* @param @param jedis
	* @param @param request
	* @param @param name
	* @param @return  
	* @return String
	 */
	public static String getSessionAttributeString(HttpServletRequest request, String name) {
		
		String sessionKey = getSessionKey(request);
		return (isNotEmpty(sessionKey) ? (String)RedisUtils.get(sessionKey + name) : null);
	}
	
	

	/**
	* @Title: getSessionAttribute 
	* @Description: 获取会话变量值
	* @param @param <T>
	* @param @param request
	* @param @param name
	* @param @return  
	* @return T
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(HttpServletRequest request, String name) throws Exception {
		String sessionKey = getSessionKey(request);
		return (isNotEmpty(sessionKey) ? (T)RedisUtils.get(sessionKey + name) : null);
	}
	
	/**
	* @Title: removeSessionAttribute 
	* @Description: 移出会话变量 
	* @param @param jedis
	* @param @param request
	* @param @param name  
	* @return void
	 */
	public static void removeSessionAttribute(Jedis jedis, HttpServletRequest request,
			String name) {
		
		String sessionKey = getSessionKey(request);
		if (sessionKey != null) {
			RedisUtils.deleteObjectKey(sessionKey + name);
		}
	}

	
	/**
	* @Title: getSessionKey 
	* @Description: 获取会话Key, 不存在返回null
	* @param @param request
	* @param @return  
	* @return String
	 */
	public static String getSessionKey(HttpServletRequest request) {
		return CookieUtil.getCookieByName(request, COOKIE_USER_KEY);
	}
	
	/**
	* @Title: getSessionKey 
	* @Description: 获取会话Key, 不存在返回新Key 
	* @param @param request
	* @param @param response
	* @param @return  
	* @return String
	 */
	public static String getSessionKey(HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = getSessionKey(request);
		if (sessionKey == null) {
			sessionKey = java.util.UUID.randomUUID().toString();
			CookieUtil.setCookie(request, response, COOKIE_USER_KEY, sessionKey);
		}
		return sessionKey;
	}
	
	/**
	* @Title: getNewSessionKey 
	* @Description: 获取新会话Key
	* @param @param request
	* @param @param response
	* @param @return  
	* @return String
	 */
	public static String getNewSessionKey(HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = java.util.UUID.randomUUID().toString();
		CookieUtil.setCookie(request, response, COOKIE_USER_KEY, sessionKey);
		return sessionKey;
	}
	
	/**
	* @Title: removeSessionKey 
	* @Description: 移出会话Key 
	* @param @param request
	* @param @param response
	* @param @return  
	* @return boolean
	 */
	public static boolean removeSessionKey(HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = CookieUtil.getCookieByName(request, COOKIE_USER_KEY);
		if (isNotEmpty(sessionKey)) {
			CookieUtil.removeCookieByName(request, response, COOKIE_USER_KEY);
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	

	
	
	
}
