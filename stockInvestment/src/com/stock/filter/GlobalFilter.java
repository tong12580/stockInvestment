/**
 * Filename:	BBCFilter.java
 * Description:
 * Copyright:   Copyright (c)2011
 * Company:    easy
 * @author:     guosheng.zhu
 * @version:    1.0  
 * Create at:   2011-5-19 上午09:50:20  
 *  
 * Modification History:  
 * Date           Author       Version      Description  
 * ------------------------------------------------------------------  
 * 2011-5-19    guosheng.zhu       1.0        1.0 Version
 */
package com.stock.filter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.stock.common.util.response.Constants;
import com.stock.common.PropertyFactory;

/**
 * @ClassName: GlobalFilter
 * @Description: 全局过滤器
 * @author guosheng.zhu
 * @date 2011-5-19 上午09:50:32
 * 
 */
public class GlobalFilter implements Filter {

	private final static String[] suffixs = new String[] { ".gif", ".jpg",
			".bmp", ".png", ".html" };
	// 配置
	protected FilterConfig filterConfig = null;

	// 销毁处理
	@Override
	public void destroy() {
	}

	/**
	 * URL过滤处理
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		// 判断图片，做路径映射
		if (!uri.contains("static") && uri.contains("upload")
				&& checkFileRequest(uri)) {
			uri = java.net.URLDecoder.decode(uri, "UTF-8");
			int location = uri.indexOf("upload");
			if (location == -1) {
				// 不存在判断
				return;
			}

			String realPath = PropertyFactory.getImageBasePath()
					+ uri.substring(location);
			InputStream fis = null;
			try {
				fis = new BufferedInputStream(new FileInputStream(realPath));
			} catch (Exception e) {
				fis = new BufferedInputStream(new FileInputStream(
						PropertyFactory.getImageBasePath()
								+ Constants.UPLOAD_BASE_FOLDER
								+ "/filenotfound.jpg"));
			}
			ServletOutputStream op = response.getOutputStream();
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			op.write(buffer);
			fis.close();
			op.flush();
			op.close();
			return;
		}

		// 编写过滤规则
		chain.doFilter(request, response);
	}

	/**
	 * 初始化
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

	/**
	 * @Title: checkFileRequest
	 * @Description: 判断是否是文件请求
	 * @param @param uri
	 * @param @return
	 * @return boolean
	 */
	private boolean checkFileRequest(String uri) {
		for (String item : suffixs) {
			if (uri.toLowerCase().contains(item)) {
				return true;
			}
		}
		return false;
	}
}
