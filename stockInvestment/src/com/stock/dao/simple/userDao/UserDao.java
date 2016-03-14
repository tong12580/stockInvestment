package com.stock.dao.simple.userDao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
	
	/**
	 * 按id查询特定用户注册信息
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getUser(Map<String, Object> params);
	
	/**
	 * 添加一个用户
	 * @param params
	 * @return
	 */
	public int addUser(Map<String, Object> params);
	
	/**
	 * 按id修改一个用户
	 * @param params
	 * @return
	 */
	public int updUser(Map<String, Object> params);
	
	/**
	 * 批量删除一个用户
	 * @param id
	 * @return
	 */
	public int delUser(int...id);
}
