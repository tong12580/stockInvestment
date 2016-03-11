package com.stock.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stock.common.RedisUtils;
import com.stock.common.SessionUtil;
import com.stock.common.util.response.IDefineMsg;
import com.stock.common.util.response.ServiceAction;
import com.stock.pojo.user.Users;

public class RootController extends ServiceAction{
	
	/**
	 * 
	 * @Title: getBasePath
	 * @Description: 获取根路径
	 * @author xiao.he
	 * @date 2015-9-13 下午09:33:55
	 * @param  request
	 * @return String
	 */
	protected String getBasePath(HttpServletRequest request){
		return "http://" + request.getHeader("Host") + request.getContextPath()+"/";
	}
	
	/**
	 * 缓存用户信息
	 * @param request
	 * @param response
	 * @param users
	 * @throws Exception
	 */
	protected void saveLogin(HttpServletRequest request,HttpServletResponse response,Users users)
			throws Exception {
		
		int timeout = 1 * 24 * 60 * 60;//一天
			
		//用户登录信息存放至缓存中
		String sessionKey = SessionUtil.getNewSessionKey(request, response);
		RedisUtils.set(sessionKey+IDefineMsg.ADMIN_USER_KEY, users, timeout);
	}
	
	/**
	 * 
	 * @Title: getUser
	 * @Description: 获取用户信息
	 * @author xiao.he
	 * @date 2015-9-13 下午09:39:00
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return User
	 */
	protected Users getUser(HttpServletRequest request,HttpServletResponse response){
		Users user = null;
		try {
			user = SessionUtil.getSessionAttribute(request,IDefineMsg.ADMIN_USER_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
