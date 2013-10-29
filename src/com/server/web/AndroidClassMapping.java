
package com.server.web;

import java.util.HashMap;
import java.util.Map;


public class AndroidClassMapping {
	
	/**
	 * 映射关系
	 */
	private static Map<String,Class<?>> mapping = new HashMap<String,Class<?>>();
	
	static{
		
		//长整型
		mapping.put("java.lang.Long", Long.class);
		
		//整型
		mapping.put("java.lang.Integer", Integer.class);
		
		//双精度型
		mapping.put("java.lang.Double", Double.class);
		
		//浮点型
		mapping.put("java.lang.Float", Float.class);
		
		//短整型
		mapping.put("java.lang.Short", Short.class);
		
		//字符串
		mapping.put("java.lang.String", String.class);
		
		//JSON类型
		mapping.put("org.json.JSONObject", org.json.JSONObject.class);
		mapping.put("org.json.JSONArray", org.json.JSONArray.class);
	}
	
	/**
	 * 取得Class
	 * @param className
	 * @return
	 */
	public static Class<?> getClass(String className){
		return mapping.get(className);
	}
}

