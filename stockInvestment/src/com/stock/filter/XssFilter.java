/**  
* Filename:    XssFilter.java  
* Description:   
* Copyright:   Copyright (c)2014  
* Company:    easy 
* @author:     Issac 
* @version:    1.0  
* Create at:   2014-6-12 下午02:58:03  
*/

package com.stock.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.stock.common.XssHttpServletRequestWrapper;


/** 
 * @ClassName: XssFilter 
 * @Description: TODO
 * @author Issac
 * @date 2014-6-12 下午02:58:03 
 *  
 */

public class XssFilter implements Filter {
	
	FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		this.filterConfig = null;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
