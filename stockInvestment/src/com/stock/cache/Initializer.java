package com.stock.cache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.stock.common.PropertyFactory;
import com.stock.common.RedisUtils;


/**
 * @ClassName: Initializer
 * @Description: tomcat 启动初始化类
 * @author yutong
 * @date 2016-03-08 下午06:50:35
 */
public class Initializer extends HttpServlet {

	private static final long serialVersionUID = 2691464828213962073L;
	
	private Logger logger 	= Logger.getLogger(Initializer.class);

	
	/**
	 * tomcat启动初始化处理
	 */
	public void init() throws ServletException {
		// 初始化连接池
		try {
			//初始化redis设置
			RedisUtils.Initializer(PropertyFactory.getRedisIp(), Integer.parseInt(PropertyFactory.getRedisPort()));
			logger.info("redis2初始化完成");
			
			// 初始化缓存
			CacheFactory.init();
			
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
