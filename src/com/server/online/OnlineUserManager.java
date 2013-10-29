package com.server.online;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.server.pojo.UserInfo;
import com.sjump.commons.util.ThreadMap;


public class OnlineUserManager {
	
	private static Map<String,OnlineUser> onlineUserMap = new ConcurrentHashMap<String,OnlineUser>();
	
	/**超过20分钟操作，则超时*/
	public static final Long OUT_TIME = 1000 * 60 * 20l;
	
	public static void login(UserInfo user){
		
		String sessionId = UUID.randomUUID().toString();
		user.setSessionId(sessionId);
		OnlineUser onlineUser = new OnlineUser();
		onlineUser.setLoginDate("2012-10-10");
		onlineUser.setSessionId(sessionId);
		onlineUser.setUserName(user.getUserName());
		onlineUser.setUserId(user.getUid()+"");
		onlineUser.setLastAccessTime(System.currentTimeMillis());
		
		//登录的时候，删除相同的在线用户：这些存在相同用户的原因是，比如断电造成的。
		//TODO这里需要验证是否正确，主要是equal的时候
		Set<String> key = onlineUserMap.keySet();
		for (Iterator<String> it = key.iterator();it.hasNext();) {
			String s = it.next();
			OnlineUser ou =	onlineUserMap.get(s);
			if(user.getUserName().equals(ou.getUserName())) {
				onlineUserMap.remove(s);
			}
		}
		onlineUserMap.put(sessionId, onlineUser);
		ThreadMap.put("SESSION_ID", sessionId);
	}
	
	//删除当前在线用户
	public static void logout(Object sessionId){
		if (sessionId != null) {
			onlineUserMap.remove(sessionId.toString());
		}
	}
	
	public static OnlineUser getOnlineUser(Object sessionId){
		if(sessionId != null){
			return onlineUserMap.get(sessionId.toString());
		}
		return null;
	}
	
	public static boolean isOnline(Object sessionId){
		if(sessionId != null){
			return onlineUserMap.get(sessionId.toString()) != null;
		}
		return false;
	}
	
	public static Map<String,OnlineUser> getOnlineUserMap() {
		return onlineUserMap;
	}
}

