package com.stock.service.users.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stock.cache.CacheBankCard;
import com.stock.common.RedisUtils;
import com.stock.common.SendSMSUtil;
import com.stock.common.util.Encryption;
import com.stock.common.util.response.IDefineMsg;
import com.stock.common.util.response.IResult;
import com.stock.common.util.response.ServiceAction;
import com.stock.dao.simple.userDao.UserDao;
import com.stock.dao.simple.userDao.UserInfoDao;
import com.stock.pojo.BankCard;
import com.stock.pojo.user.Users;
import com.stock.service.users.interfaces.UserService;

@Service
public class UserServiceImpl extends ServiceAction implements UserService{
	
	@Autowired
	private	UserDao userDao;
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public IResult getPhoneCode(String phone) {
		
		if(isEmpty(phone))
			return makerErrResults("手机号码"+IDefineMsg.LACK_PARAM);
		if(!isPhone(phone))
			return makerErrResults("手机号码"+IDefineMsg.FORMAT_ERR);
		
		String random =valueOf(getRandomCode6());
		RedisUtils.set(IDefineMsg.PHONE_CODE_INFO+phone,random,60*60*60);
		
		String msg	="您的手机验证码为："+random;
		if(SendSMSUtil.sendMessage(phone, msg))
			return makerSusResults(IDefineMsg.PHONE_CODE_SUC);
		
		return makerErrResults(IDefineMsg.PHONE_CODE_ERR);
	}

	@Override
	public IResult checkParamByUser(Map<String, Object> reqMap) {
		
		IResult rs 	=	null;
		rs			=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful()) return rs;
		
		String loginPwd =	valueOf(reqMap.get("loginPwd"));
		String pwdtwo	=	valueOf(reqMap.get("pwdtwo"));
		String phone	=	valueOf(reqMap.get("phone"));
		Object redisCode=	RedisUtils.get(IDefineMsg.PHONE_CODE_INFO+phone);
		
		if(!isNumChar(loginPwd))
			return makerErrResults(IDefineMsg.PASSWORD_BY_FORMAT+"登录密码不能为纯数字或字母");
		if(!loginPwd.equals(pwdtwo))
			return makerErrResults(IDefineMsg.PASSWORD_IS_DIFFRENT);
		if(!reqMap.get("phoneCode").equals(redisCode))
			return makerErrResults(IDefineMsg.PHONE_CODE_IS_FAIL);
		
		String salt	= getShortUUid(); //密串
		loginPwd	= Encryption.md5s(loginPwd+salt);
		
		String nickName = "GP_"+getShortUUid();
		
		reqMap.put("loginPwd"	, loginPwd);
		reqMap.put("salt"		, salt);
		reqMap.put("status"		, 1);
		reqMap.put("createtime"	, getCurDatetime());
		reqMap.put("nickName"	, nickName);
		
		return makerSusResults(IDefineMsg.CHEACK_SUC);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public IResult addUsers(Map<String, Object> reqMap) {
		
		userDao.addUser(reqMap);
		List<Map<String, Object>> userMap =userDao.getUser(reqMap);
		
		if(isEmpty(userMap))
			return makerErrResults(IDefineMsg.ADD_FAIL);
		
		String userId =getListMapValue(userMap, "id");
		reqMap.put("userId", userId);
		reqMap.put("userType", 1);
		int userInfo = userInfoDao.addUserInfo(reqMap);
		if(userInfo<0)
			return makerErrResults(IDefineMsg.ADD_FAIL);
		
		Users user =userInfoDao.findUserInfo(userId).get(0);
		
		return makerSusResults(IDefineMsg.ADD_SUCCESS,user);
	}


	@Override
	public IResult updLoginPwd(Map<String, Object> reqMap) {
		
		IResult rs	=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful()) return rs;
		
		List<Map<String, Object>> usersList =userDao.getUser(reqMap); 
		String oldLoginpwd		= getListMapValue(usersList, "loginpwd");
		String oldSalt			= getListMapValue(usersList, "salt");
		String id				= getListMapValue(usersList, "id");
		
		String loginPwd			= valueOf(reqMap.get("loginPwd"));
		String newPassword		= valueOf(reqMap.get("newpassword"));
		String passwordtwo		= valueOf(reqMap.get("passwordtwo"));
		loginPwd				= Encryption.md5s(loginPwd+oldSalt);
		
		if(!loginPwd.equals(oldLoginpwd))
			return makerErrResults(IDefineMsg.PASSWORD_FAIL);
		if(!isNumChar(newPassword))
			return makerErrResults(IDefineMsg.PASSWORD_BY_FORMAT+"登录密码不能为纯数字或字母");
		if(!newPassword.equals(passwordtwo))
			return makerErrResults(IDefineMsg.PASSWORD_IS_DIFFRENT);
		
		String salt	= getShortUUid();
		loginPwd	= Encryption.md5s(newPassword+salt);
		
		Map<String, Object> rsMap =new HashMap<String, Object>(); 
		
		rsMap.put("loginPwd"	, loginPwd);
		rsMap.put("salt"		, salt);
		rsMap.put("id"			, id);
		rsMap.put("updatetime"	, getCurDatetime());
		
		int flag	=	userDao.updUser(rsMap);
		if(flag<0)
			return makerErrResults(IDefineMsg.UPD_FAIL);
		return makerSusResults(IDefineMsg.UPD_SUCCESS);
	}

	@Override
	public IResult updTradersPassword(Map<String, Object> reqMap) {
		
		IResult rs 			=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful()) return rs;
		
		String phone			=	valueOf(reqMap.get("phone"));
		String tradePassword	=	valueOf(reqMap.get("tradePassword"));
		String tradePasswordtwo	=	valueOf(reqMap.get("tradePasswordtwo"));
		String phoneCode		=	valueOf(reqMap.get("phoneCode"));
		String userId			=	valueOf(reqMap.get("userId"));
		Object redisCode		=	RedisUtils.get(IDefineMsg.PHONE_CODE_INFO+phone);
		
		if(!phoneCode.equals(redisCode))
			return makerErrResults(IDefineMsg.PHONE_CODE_IS_FAIL);
		if(!tradePassword.equals(tradePasswordtwo))
			return makerErrResults(IDefineMsg.PASSWORD_IS_DIFFRENT);
		
		String	tradeSalt	=	getShortUUid();
		tradePassword		=	Encryption.md5s(tradePassword+tradeSalt);
		
		Map<String, Object> rsMap	=	new	HashMap<String, Object>();
		rsMap.put("tradeSalt"		, tradeSalt);
		rsMap.put("tradePassword"	, tradePassword);
		rsMap.put("id"				, userId);
		rsMap.put("updatetime"		, getCurDatetime());
		int flag	=	userDao.updUser(rsMap);
		
		if(flag<0)
			return makerErrResults(IDefineMsg.UPD_FAIL);
		return makerSusResults(IDefineMsg.UPD_SUCCESS);
	}

	@Override
	public IResult checkUserLegal(Map<String, Object> reqMap) {
		
		IResult rs		=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful()) return rs;
		
		List<Map<String, Object>> user=userDao.getUser(reqMap);
		if(isNull(user))
			return makerErrResults(IDefineMsg.LOGIN_ERR);
		
		String userPwd  =getListMapValue(user, "loginpwd");
		String salt		=getListMapValue(user, "salt");
		
		String loginPwd =valueOf(reqMap.get("loginPwd"));
		String salt_pwd	=Encryption.md5s(loginPwd+salt);
		
		if(!salt_pwd.equals(userPwd))
			return makerErrResults(IDefineMsg.LOGIN_ERR);
		
		String status	=getListMapValue(user, "status");
		String userId	=getListMapValue(user, "id");
		if(!status.equals(IDefineMsg.STATUS))
			return makerErrResults(IDefineMsg.ACCOUNT_FREEZED);
		
		Users users =userInfoDao.findUserInfo(userId).get(0);
		
		return makerSusResults(IDefineMsg.LOGIN_SUC, users);
		
	}

	@Override
	public IResult updUserInfo(Map<String, Object> reqMap) {
		
		int flg		=	userInfoDao.updUserInfo(reqMap);
		if(flg<0)
			return makerErrResults(IDefineMsg.UPD_FAIL);
		
		return makerSusResults(IDefineMsg.UPD_SUCCESS);
	}

	@Override
	public IResult updNickName(Map<String, Object> reqMap) {

		IResult rs			=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful())
			return rs;
		
		int flg		= userDao.updUser(reqMap);
		if(flg<0)
			return makerErrResults(IDefineMsg.UPD_FAIL);
		
		return makerSusResults(IDefineMsg.UPD_SUCCESS);
	}

	@Override
	public IResult forgetPassWord(Map<String, Object> reqMap) {
		
		IResult rs			=	checkParamsEmpty(reqMap);
		if(!rs.isSuccessful()) return rs;
		
		List<Map<String, Object>> userMap =userDao.getUser(reqMap);
		if(isEmpty(userMap))
			return makerErrResults(IDefineMsg.GET_FAIL+"无该用户");
		
		String id 			=	getListMapValue(userMap, "id");
		String phone		=	valueOf(reqMap.get("phone"));
		String phoneCode	=	valueOf(reqMap.get("phoneCode"));
		String loginPwd		=	valueOf(reqMap.get("loginPwd"));
		String pwdtwo		=	valueOf(reqMap.get("pwdtwo"));
		Object redisCode=	RedisUtils.get(IDefineMsg.PHONE_CODE_INFO+phone);
		
		
		
		if(!isNumChar(loginPwd))
			return makerErrResults(IDefineMsg.PASSWORD_BY_FORMAT+"登录密码不能为纯数字或字母");
		if(!loginPwd.equals(pwdtwo))
			return makerErrResults(IDefineMsg.PASSWORD_IS_DIFFRENT);
		if(!phoneCode.equals(redisCode))
			return makerErrResults(IDefineMsg.PHONE_CODE_IS_FAIL);		
		
		String salt	= getShortUUid(); 
		loginPwd	= Encryption.md5s(loginPwd+salt);
		
		Map<String, Object> rsMap =new HashMap<String, Object>(); 
		
		rsMap.put("loginPwd"	, loginPwd);
		rsMap.put("salt"		, salt);
		rsMap.put("id"			, id);
		rsMap.put("updatetime"	, getCurDatetime());
		
		int flag	=	userDao.updUser(rsMap);
		if(flag<0)
			return makerErrResults(IDefineMsg.UPD_FAIL);
		return makerSusResults(IDefineMsg.UPD_SUCCESS);
	}

	@Override
	public IResult getProvince() {
		
		Map<String, Object> reqMap		=	new HashMap<String, Object>();
		List<Map<String, Object>>	provinceList	=	userInfoDao.getProvince(reqMap);
		
		if(isNull(provinceList))
			return makerErrResults(IDefineMsg.SQL_DB_EXCEPTION);
		
		return makerSusResults(IDefineMsg.GET_SUCCESS, provinceList);
	}

	@Override
	public IResult getCity(Map<String, Object> reqMap) {
		
		String provinceID =	valueOf(reqMap.get("provinceID"));
		if(isEmpty(provinceID))
			return makerErrResults("省份"+IDefineMsg.LACK_PARAM);
		
		List<Map<String, Object>> cityList =	userInfoDao.getCity(reqMap);	
		if(isNull(cityList))
			return makerErrResults(IDefineMsg.SQL_DB_EXCEPTION);
		
		return makerSusResults(IDefineMsg.GET_SUCCESS, cityList);
	}

	@Override
	public IResult getAreas(Map<String, Object> reqMap) {
		
		String cityID	=	valueOf(reqMap.get("cityID"));
		if(isEmpty(cityID))
			return makerErrResults("城市"+IDefineMsg.LACK_PARAM);
		
		List<Map<String, Object>> areasList	=	 userInfoDao.getAreas(reqMap);
		if(isNull(areasList))
			return makerErrResults(IDefineMsg.SQL_DB_EXCEPTION);
		
		return makerSusResults(IDefineMsg.GET_SUCCESS,areasList);
	}

	@Override
	public IResult getBankNo() {
		
		List<BankCard> bankList =	CacheBankCard.getInstance().getBankcardList();
		if(!isEmpty(bankList))
			return makerSusResults(IDefineMsg.GET_SUCCESS, bankList);
		
		bankList	=	userInfoDao.findBank();
		if(isEmpty(bankList))
			return makerErrResults(IDefineMsg.SQL_DB_EXCEPTION);
		
		return makerSusResults(IDefineMsg.GET_SUCCESS, bankList);
	}
	
}
