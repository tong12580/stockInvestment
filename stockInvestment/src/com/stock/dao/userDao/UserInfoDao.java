package com.stock.dao.userDao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.stock.common.mybatis.Page;
import com.stock.pojo.user.Users;

/**
 * @ClassName: UserInfoDao
 * @author 余童
 * @Description:用户信息表，城市表操作
 * @since 2016/03/10
 * @version 1.0
 */
@Repository
public interface UserInfoDao {
	
	/**
	 * 分页条件查询
	 * 所有用户信息 
	 * (查询视图)
	 * @param params
	 * @return
	 */
	public  List<Map<String, Object>> findAllUserInfo(Page<Map<String, Object>> params);
	
	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public	List<Users> findUserInfo(String userId);
	
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
	 * 查询省
	 * @param params
	 * @return
	 */
	public	List<Map<String, Object>> getProvince(Map<String, Object> params);
	
	/**
	 * 查询市
	 * @param params
	 * @return
	 */
	public	List<Map<String, Object>> getCity(Map<String, Object> params);
	
	/**
	 * 查询县
	 * @param params
	 * @return
	 */
	public	List<Map<String, Object>> getAreas(Map<String, Object> params);
}
