package com.stock.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.stock.common.util.MyX509TrustManager;
import cn.toruk.pub.comm.Http;

import com.alibaba.fastjson.JSONObject;




public class HttpUtil {
	
	  private static final Logger logger = Logger.getLogger(HttpUtil.class);  
	
	  /**
		 * 发起https请求并获取结果
		 * 
		 * @param requestUrl 请求地址
		 * @param requestMethod 请求方式（GET、POST）
		 * @param outputStr 提交的数据
		 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
		 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr){
		
		JSONObject jsonObject =null;
		StringBuffer buffer	  =new StringBuffer();
		
		try {
						// 创建SSLContext对象，并使用我们指定的信任管理器初始化
						TrustManager[] tm = { new MyX509TrustManager() };
						SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
						sslContext.init(null, tm, new java.security.SecureRandom());
						// 从上述SSLContext对象中得到SSLSocketFactory对象
						SSLSocketFactory ssf = sslContext.getSocketFactory();

						URL url = new URL(requestUrl);
						HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
						httpUrlConn.setSSLSocketFactory(ssf);

						httpUrlConn.setDoOutput(true);
						httpUrlConn.setDoInput(true);
						httpUrlConn.setUseCaches(false);
						// 设置请求方式（GET/POST）
						httpUrlConn.setRequestMethod(requestMethod);

						if ("GET".equalsIgnoreCase(requestMethod))
							httpUrlConn.connect();

						// 当有数据需要提交时
						if (null != outputStr) {
							OutputStream outputStream = httpUrlConn.getOutputStream();
							// 注意编码格式，防止中文乱码
							outputStream.write(outputStr.getBytes("UTF-8"));
							outputStream.close();
						}

						// 将返回的输入流转换成字符串
						InputStream inputStream = httpUrlConn.getInputStream();
						InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

						String str = null;
						while ((str = bufferedReader.readLine()) != null) {
							buffer.append(str);
						}
						bufferedReader.close();
						inputStreamReader.close();
						// 释放资源
						inputStream.close();
						inputStream = null;
						httpUrlConn.disconnect();
						jsonObject=JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			logger.error("https request error:{}" + e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 发送http请求，支持url为https(https通讯)
	 * @param url  请求url
	 * @param method 请求方式(get/post)
	 * @param encode 编码格式
	 * @param params 请求参数集
	 * @return
	 */
	public static String sendHttpRequest(String url ,String method, String encode, Map<String, String> params){
		//ƴ��url
		int index = 0;
		String connStr = "&";
		for(Entry<String, String> param:params.entrySet()){
			if(url.indexOf("?") < 0 && index == 0){
				connStr += "?";
			}else{
				connStr += "&";
			}
			url += connStr+param.getKey()+"="+param.getValue();
			index++;
		}
		return Http.send(url, method, encode);
	} 
}
