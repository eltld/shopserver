
package com.wy.shopping.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.scpop.services.mobile.model.VO;
import com.server.online.OnlineUser;
import com.server.online.OnlineUserManager;
import com.server.web.ThreadMap;
import com.wy.shopping.service.core.ServerResponse;
import com.wy.shopping.service.core.ServiceResponse;


public abstract class AbstractService{
	
	private static final Log log = LogFactory.getLog(AbstractService.class);
	
	/**
	 * 返回ServiceResponse
	 * @param value 返回值
	 * @return
	 */
	public ServiceResponse response(Object value) {
		log.info("服务端返回数据："+value.toString());
		return new ServerResponse(value,"",true);
	}
	
	
	public ServiceResponse responseArray() {
		return new ServerResponse(new JSONArray(),"",true);
	}
	
	
	public ServiceResponse response() {
		return new ServerResponse("","",true);
	}
	
	
	public ServiceResponse faild() {
		return new ServerResponse("","",false);
	}
	
	
	public ServiceResponse response(Object value ,String message,boolean success) {
		log.info("服务端返回数据："+value.toString());
		return new ServerResponse(value,message,true);
	}
	
	
	public ServiceResponse exception(Throwable e ){
		return new ServerResponse("", e.getMessage(), false);
	}
	
	
	public ServiceResponse faild(String exception){
		return new ServerResponse("",exception, false);
	}
	
	public ServiceResponse response(String content, boolean success){
		return new ServerResponse(content ,null ,success);
	}
	
	/**
	 * 将List转换为jsonArray返回给客户端
	 */
	public ServiceResponse responseList(List<? extends VO> list){
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			try {
				array.put(i, list.get(i).toJSON());
			} catch (Exception e) {
				return exception(e);
			}
		}
		return response(array);
	}
	
	public ServiceResponse responseStringList(List<String> list){
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			try {
				array.put(i, list.get(i));
			} catch (Exception e) {
				return exception(e);
			}
		}
		return response(array);
	}
	
	/**
	 * 取得在线用户信息
	 * @return
	 */
	public OnlineUser getOnlineUser() {
		Object sessionId = ThreadMap.get(ThreadMap.SESSION_ID);
		return OnlineUserManager.getOnlineUser(sessionId);
	}
	
}

