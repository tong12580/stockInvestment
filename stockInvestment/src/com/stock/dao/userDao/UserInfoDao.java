package com.stock.dao.userDao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.stock.common.mybatis.Page;

@Repository
public interface UserInfoDao {
	
	/**
	 * 分页查询所有用户信息(查询视图)
	 * @param params
	 * @return
	 */
	public  List<Map<String, Object>> findAllUserInfo(Page<Map<String, Object>> params);
	
	/**
	 * 添加用户资料
	 * @param params
	 * @return
	 */
	public	int  addUserInfo(Map<String, Object> params);
	
	/**
	 * 修改用户资料
	 * @param params
	 * @return
	 */
	public	int  updUserInfo(Map<String, Object> params);
	
	/**
	 * 删除用户资料
	 * @param id
	 * @return
	 */
	public	int  delUserInfo(int...id);
	
	/**
	 * 条件查询用户资料
	 * @param params
	 * @return
	 */
	public	int  getUserInfo(Map<String, Object> params);
	
}
