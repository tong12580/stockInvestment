package com.stock.common.util.response;

/**
 * @Description:常量接口
 * @author yutong
 * @date 2016-3-8 下午06:36:58
 */
public interface Constants {
	
	/** 站点入口*/
	public final static String BASEPATH = "https://xxx.xx.com/";
	
	/** session key 常量定义*/
	public static final String SESSION_KEY_USER = "sessionUser"; // 用户session
	
	/**图片保存路径*/
	public final static String FILE_BASEPATH = "file_basepath";
	/**图片保存路径*/
	public final static String STATIC_BASEPATH = "static_basepath";	
	
	/** 文件映射基路径名称*/
	public final static String UPLOAD_BASE_FOLDER = "upload";
	
	/** 用户缓存信息key*/
	public static final String USER_KEY = "user_key";
	
	/** 注册手机验证码key*/
	public static String CODE_COUNT_NUM = "CODE_COUNT_NUM_";
	
	/**
	 * 固定图片压缩尺寸常量
	 */
	public final static int SIZE_HEAD_IMG_W = 200;
	public final static int SIZE_HEAD_IMG_H = 150;

	public static final String MOBILE = "mobile";
}
