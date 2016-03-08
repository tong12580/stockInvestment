/**
 * Filename:	PropertyFactory.java
 * Description:
 * Copyright:   Copyright (c)2011
 * Company:    easy
 * @author:     guosheng.zhu
 * @version:    1.0  
 * Create at:   2011-6-10 下午08:08:37  
 *  
 * Modification History:  
 * Date           Author       Version      Description  
 * ------------------------------------------------------------------  
 * 2011-6-10    guosheng.zhu       1.0        1.0 Version
 */
package com.stock.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * @ClassName: PropertyFactory
 * @Description: 属性加载工具类
 * @author guosheng.zhu
 * @date 2011-6-10 下午08:08:37
 */
public class PropertyFactory {

	private static Logger logger = Logger.getLogger(PropertyFactory.class);

	private static Map<String, Properties> propMap = null;

	/**
	 * @Title: getProperty
	 * @Description: 获取属性数据
	 * @param @param filePath
	 * @param @param name
	 * @param @return
	 * @return String
	 */
	public static String getProperty(String filePath, String name) {
		if (propMap == null) {
			propMap = new HashMap<String, Properties>();
		}
		String key = getFileNameFromPath(filePath);
		if (!propMap.containsKey(key)) {
			Properties prop = new Properties();
			try {
				InputStream is = new BufferedInputStream(new FileInputStream(
						filePath));
				prop.load(is);
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				logger.error(e.getStackTrace());
				return null;
			}
			propMap.put(key, prop);
			return prop.getProperty(name);
		}
		return propMap.get(key).getProperty(name);
	}

	/**
	 * @Title: getFileNameFromPath
	 * @Description: 根据路径解析文件名
	 * @param @param path
	 * @param @return
	 * @return String
	 */
	private static String getFileNameFromPath(String path) {
		int location = path.lastIndexOf("/");
		if (location == -1) {
			return null;
		}
		String name = path.substring(location + 1);
		return name;
	}

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + path + "/";
	}
	
	public static String getRedisIp() {
		return getProperty(BasePathFactory.getResourcePath("redis.properties"), "redis.ip");
		//return "127.0.0.1";
	}
	
	public static String getRedisPort() {
		return getProperty(BasePathFactory.getResourcePath("redis.properties"), "redis.port");
		//return "6379";
	}
	
  /***
	* 
	* @Title: getSolrUrl 
	* @Description: 获取solr服务端地址 
	* @param @return  
	* @return String
	 */
	public static String getSolrUrl(){
		return getProperty(BasePathFactory.getResourcePath("init.properties"), "solr.url");
	}
	
	
	/**
	 * 
	* @Title: getSWFToolsPath 
	* @Description: 获取swftools安装路径 
	* @param @return  
	* @return String
	 */
	public static String getSWFToolsPath(){
		return getProperty(BasePathFactory.getResourcePath("other.properties"), "swftools_path");
	}
	
	/**
	 * 
	* @Title: getOpenofficePath 
	* @Description: 获取swftools安装路径 
	* @param @return  
	* @return String
	 */
	public static String getOpenofficePath(){
		return getProperty(BasePathFactory.getResourcePath("other.properties"), "openoffice_path");
	}
	
	/**
	 * 
	* @Title: getPdf2swfLanguagePath 
	* @Description: 获取pdf2swf_language安装路径 
	* @param @return  
	* @return String
	 */
	public static String getPdf2swfLanguagePath(){
		return getProperty(BasePathFactory.getResourcePath("other.properties"), "pdf2swf_language");
	}
	
	public static String getImageBasePath() {
		return getProperty(BasePathFactory.getResourcePath("other.properties"),
				"picpath");
	}
	/**
	 * 
	 * @Title: getWebRootBasePath
	 * @Description: 获取根路径
	 * @author xiao.he
	 * @date 2014-6-19 上午11:07:13
	 * @param @return
	 * @return String
	 */
	public static String getWebRootBasePath() {
		String path = BasePathFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			return null;
		}
		return path;
	}
}

