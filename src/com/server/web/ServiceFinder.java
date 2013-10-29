/* 
 * 创建日期 2011-9-7
 *
 * 成都天和软件公司
 * 电话：028-85425861 
 * 传真：028-85425861-8008 
 * 邮编：610041 
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001 
 * 版权所有
 */
package com.server.web;


/**
 * 服务查找接口
 * 
 * @author 王文成
 * @version 1.0
 * @since 2011-9-7
 */
public interface ServiceFinder {
	
	/**
	 * 查找Service实例
	 * @param className 服务接口类名
	 * @return Service实例
	 * @throws Exception
	 */
	Object find(String className) throws Exception;
}

