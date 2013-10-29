
package com.server.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class WebContextListener implements ServletContextListener {

	/**
	 *<context-param>
	 *	<param-name>pfwk.data.path</param-name>
	 *	<param-value>$env.SC_DATA_V5</param-value>
	 *</context-param>
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
//        ServletContext context = event.getServletContext();
//        String dataPath = context.getInitParameter("pfwk.data.path"); //取得Data目录的位置
//        if(dataPath.startsWith("$env.")) {
//        	dataPath = System.getenv(dataPath.substring(5));//从环境变量里取得配置
//        }
//        if( dataPath == null ){
//        	throw new RuntimeException("请配置 pfwk.data.path 的目录路径!");
//        }
//        System.setProperty("pfwk.data.path",dataPath);
//        PFWKConfig.init(dataPath);//初始化系统配置
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// to do what?
	}
}

