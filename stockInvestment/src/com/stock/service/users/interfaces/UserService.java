package com.stock.service.users.interfaces;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.stock.common.util.response.IResult;

/**
 * 用户注册登录业务处理
 * @author yongtong
 * @since 2016/03/10
 */
@Service
public interface UserService {
	
	
	/**
	 * 获取短信验证码
	 * @param reqMap
	 * @return
	 */
	public IResult getPhoneCode(String phone);

	
	/**
	 * 校验注册入餐参数
	 * @param reqMap
	 * @return
	 */
	public IResult checkParamByUser(Map<String, Object> reqMap);
	
	/**
	 * 添加用户
	 * @param reqMap
	 * @return
	 */
	public IResult addUsers(Map<String, Object> reqMap);
	
	/**
	 * 修改昵称
	 * @param nickName
	 * @return
	 */
	public IResult updUserInfo(Map<String, Object> reqMap);
	
	/**
	 * 修改登录密码
	 * @param loginpwd
	 * @return
	 */
	public IResult updLoginPwd(Map<String, Object> reqMap);
	
	/**
	 * 修改交易密码
	 * @param reqMap
	 * @return
	 */
	public IResult updTradersPassword(Map<String, Object> reqMap);
	
	/**
	 * 校验用户合法性
	 * @param reqMap
	 * @return
	 */
	public IResult checkUserLegal(Map<String, Object> reqMap);
}
