
package com.server.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WebContextFilter implements Filter {
	public static Log log = LogFactory.getLog(WebContextFilter.class);

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {
	}

	/**
	 * 过滤
	 * 
	 * @param request
	 * @param response
	 * @param filter
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException,
			ServletException {
		ThreadMap.put(WebContext.HTTP_REQUEST, request);
		ThreadMap.put(WebContext.HTTP_RESPONSE, response);
		log.info("****************** WebContextFilter doFilter Complete! ******************");
		filter.doFilter(request, response);
	}

}
