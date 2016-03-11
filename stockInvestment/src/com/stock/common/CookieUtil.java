/**  
 * Filename:    CookieUtil.java  
 * Description:   
 * Copyright:   Copyright (c)2011  
 * Company:    easy 
 * @author:     guosheng.zhu  
 * @version:    1.0  
 * Create at:   2011-12-23 下午07:30:48  
 *  
 * Modification History:  
 * Date           Author       Version      Description  
 * ------------------------------------------------------------------  
 * 2011-12-23    guosheng.zhu       1.0        1.0 Version  
 * 2013-10-14    guosheng.zhu    1.1        1.1 Version 
 */
package com.stock.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stock.common.util.response.ServiceAction;

public class CookieUtil extends ServiceAction{

//	private final static String webKey = "rongyilicai";
	private final static String domain = "rongyilicai.com";


	// 设置cookie有效期
	private final static int cookieMaxAge = 60 * 60 * 24 * 1;

	// Cookie中，历史浏览的Cookie的路径
	private final static String HISTORY_PATH = "/";

	// 清除所有历史浏览记录
	public static void removeAllHistoryCookie(String cookieName, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length == 0) {
			return;
		}
		for (Cookie thisCookie : cookies) {
			if (thisCookie.getName().startsWith(cookieName)) {
				thisCookie.setMaxAge(0); // 删除这个cookie
				thisCookie.setPath(HISTORY_PATH);
				if(checkDomain(request)){
					thisCookie.setDomain(domain);
				}
				response.addCookie(thisCookie);
			}
		}
	}

	// 清除单个的浏览记录
	public static void removeCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		if (cookieName == null) {
			return;
		}
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length == 0) {
			return;
		}
		for (Cookie thisCookie : cookies) {
			if (thisCookie.getName().equals(cookieName)) {
				thisCookie.setMaxAge(0); // 删除这个cookie
				thisCookie.setPath(HISTORY_PATH);
				if(checkDomain(request)){
					thisCookie.setDomain(domain);
				}
				response.addCookie(thisCookie);
			}
		}
	}

	/**
	* @Title: saveCookie 
	* @Description: 添加cookie,默认过期时间是2周
	* @param @param request
	* @param @param response
	* @param @param cookieName
	* @param @param cookieValue  
	* @return void
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if(checkDomain(request)){
			cookie.setDomain(domain);
		}
		cookie.setMaxAge(cookieMaxAge);
		cookie.setPath(HISTORY_PATH);
		response.addCookie(cookie);
	}

	/**
	* @Title: setCookie 
	* @Description: 添加cookie,没有设置过期时间 
	* @param @param request
	* @param @param response
	* @param @param cookieName
	* @param @param cookieValue  
	* @return void
	 */
	public static void setCookieNoAge(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		if(checkDomain(request)){
			cookie.setDomain(domain);
		}
		cookie.setPath(HISTORY_PATH);
		response.addCookie(cookie);
	}

	


	/**
	* @Title: getCookieByName 
	* @Description: 根据cookieName来获取cookieValue 
	* @param @param request
	* @param @param cookieName
	* @param @return  
	* @return String
	 */
	public static String getCookieByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (isNotEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	private static boolean checkDomain(HttpServletRequest request){
		boolean flag=false;
		String basePath = PropertyFactory.getBasePath(request);
		if (isNotEmpty(basePath)&& basePath.contains(domain)) {
			flag=true;
		}
		return flag;
	}

}
