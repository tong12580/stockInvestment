package com.stock.controller.users;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.common.util.response.IDefineMsg;
import com.stock.common.util.response.IResult;
import com.stock.controller.admin.RootController;
import com.stock.pojo.user.Users;
import com.stock.service.users.interfaces.UserService;

@Controller
@RequestMapping(value="/register")
public class LoginController extends RootController{
	
	@Autowired
	private UserService userService;
	
	/**
	 * 注册用户
	 * @param phone
	 * @param phoneCode
	 * @param loginPwd
	 * @param pwdtwo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/users" , method =RequestMethod.POST)
	public IResult registerUser(HttpServletResponse response, HttpServletRequest request,
			String phone, String phoneCode, String loginPwd,String pwdtwo){
		
		Map<String, Object> reqMap =new HashMap<String, Object>();
		IResult rs =null ;
		
		reqMap.put("phone"		, phone);
		reqMap.put("phoneCode"	, phoneCode);
		reqMap.put("loginPwd"	, loginPwd);
		reqMap.put("pwdtwo"		, pwdtwo);
		
		rs	= userService.checkParamByUser(reqMap);
		if(!rs.isSuccessful())
			return rs;
		
		rs	= userService.addUsers(reqMap);
		if(!rs.isSuccessful())
			return rs;
		
		Users users =	(Users) rs.getResult();
		try {
			saveLogin(request, response, users);
		} catch (Exception e) {
			return makerErrResults("缓存用户信息失败");
		}
		
		return rs;
	}
	
	/**
	 * 登陆
	 * @param nickName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public IResult login(HttpServletResponse response, HttpServletRequest request,
			String phone,String loginPwd){
		
		Map<String, Object> reqMap =new HashMap<String, Object>();
		reqMap.put("phone"		, phone);
		reqMap.put("loginPwd"	, loginPwd);
		
		IResult rs=userService.checkUserLegal(reqMap);
		
		if(!rs.isSuccessful())
			return rs;
		
		Users users =	(Users) rs.getResult();
		try {
			saveLogin(request, response, users);
		} catch (Exception e) {
			return makerErrResults("缓存用户信息失败");
		}
		return rs;
	}
	
	/**
	 * 修改用户资料
	 * @param nickName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/nickname",method=RequestMethod.POST)
	public IResult updUserInfo(HttpServletResponse response, HttpServletRequest request,
			String nickName,String email,String sex,String provinceID){
		
		IResult rs	=	null;
		Users users =	getUser(request, response);
		
		if(isNull(users))
			return makerErrResults(IDefineMsg.LONGIN_PREASE);
		
		
		
		return rs;
	}
	
	/**
	 * 修改登陆密码
	 * @param nickName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/lpassword",method=RequestMethod.POST)
	public IResult updLoginPWD(String loginPwd){
		
		IResult rs=null;
		
		return rs;
	}
	
	/**
	 * 修改交易密码
	 * @param nickName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/apassword",method=RequestMethod.POST)
	public IResult updAccountPWD(String accountPWD,String phoneCode){
		
		IResult rs=null;
		
		return rs;
	}
	
	
	/**
	 * 修改交易密码
	 * @param nickName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/img",method=RequestMethod.POST)
	public IResult updImg(String img){
		IResult rs =null;
		return rs;
	}
	
	/**
	 * 获取短信验证码
	 * @param phone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/phone",method=RequestMethod.POST)
	public IResult getPhoneCode(String phone){
		
		IResult rs =userService.getPhoneCode(phone);
		return rs;
	}
	
}
