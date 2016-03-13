package com.stock.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.stock.common.util.response.LogUtil;


/**
 * @ClassName: CacheFactoty
 * @Description: 自定义缓存处理
 * @author guosheng.zhu
 * @date 2016-03-08 下午04:55:35
 */
public class CacheFactory extends LogUtil{
	
	private static Map<Object, Object> map = new HashMap<Object, Object>();
	
	/**
	 * @Title: init
	 * @Description: 缓存初始化
	 * @return void
	 */
	public static void init(){
		try {
			
			Timer timer =new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					long longs=System.currentTimeMillis();
					CacheBankCard.getInstance().init();
					longs=System.currentTimeMillis()-longs;
					info("初始化加载12-->"+longs);
				}
			}, 2000);
			
		} catch (Exception e) {
			error(e.getMessage());
		}
	}
	
	/**
	 * @Title: getCache
	 * @Description: 获取缓存对象
	 * @param cacheKey
	 * @return Object
	 */
	public static Object getCache(String cacheKey) {
		Object obj = map.get(cacheKey);
		return obj;
	}
}
