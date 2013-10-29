
package com.server.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringUtils implements ApplicationContextAware{
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtils.context = context;
	}
	
	/**
	 * 取得Bean
	 * @param id
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String id, Class<?> cls){
		return (T)context.getBean(id, cls);
	}
	
	/**
	 * 取得Bean
	 * @param id
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String id){
		return (T)context.getBean(id);
	}
}

