package com.server.web;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wy.shopping.service.core.ServiceResponse;

public class ServiceRequestHandler {
	
	Log log = LogFactory.getLog(ServiceRequestHandler.class);
	
	/**
	 * 服务接口类
	 */
	private Class<?> classType;

	/**
	 * 服务实现类
	 */
	private Class<?> classImpl;

	/**
	 * 服务实现类实例
	 */
	private Object service;

	/**
	 * 方法名
	 */
	private String methodName;
	
	/**
	 * 客户端用户会话id
	 */
	private String sessionId;
	
	/**
	 * 请求参数类型
	 */
	private List<Class<?>> paramTypes = new ArrayList<Class<?>>();

	/**
	 * 请求参数值
	 */
	private List<Object> paramValues = new ArrayList<Object>();
	
	/**
	 * 该请求的客户端版本号 
	 */
	private String apkVersion;

	public ServiceRequestHandler(InputStream in , ServiceFinder finder) throws Exception {
		init(in,finder);
	}

	/**
	 * 初始化
	 * 
	 * @param in
	 * @throws Exception
	 */
	public void init(InputStream in,ServiceFinder finder) throws Exception {
		String jsonValue = HttpUtil.read(in, "UTF-8");
		//TODO 待删除
		log.debug("客户端提交json字符串=======" + jsonValue);
		JSONObject o = new JSONObject(jsonValue);
		String className = o.getString("class");
		classType = Class.forName(className);
		service = finder.find(className);
		classImpl = service.getClass();
		methodName = o.getString("method");
		try {
			// 为了兼容低版本apk，捕捉了异常
			apkVersion = o.getString("apkVersion");
		} catch (Exception e) {
			//TODO what?
		}
		ThreadMap.put(ThreadMap.APK_VERSION, apkVersion);
		
		//如果是用户接口的退出系统方法，还是应该去获取sessionId
		boolean b1 =  methodName.equals("logout");
//		boolean b2 = classType == UpdateService.class;
//		boolean b3 = classType == Upload.class;
//		boolean b4 = classType == PlugService.class;
		
//		if (!b1 && !b1 && !b1 && !b1) {
//		}
		if(b1){
		    sessionId = o.getString("values");
		}else{
		    sessionId = o.getString("sessionId");
		}
//		setSessionId(sessionId);
		
		JSONArray types = o.getJSONArray("types");
		// 初始化参数类型
		for (int i = 0; i < types.length(); i++) {
			String name = types.getString(i);
			Class<?> classType = AndroidClassMapping.getClass(name);
			if( classType == null ){
				throw new Exception("不支持参数类型[" + name + "]!");
			}
			paramTypes.add(classType);
		}
		// 初始化参数值 
		JSONArray values = o.getJSONArray("values");
		for (int i = 0; i < values.length(); i++) {
			String value = values.getString(i);
			if(!"null".equalsIgnoreCase(value)){
				String name = types.getString(i);
				Class<?> classType = AndroidClassMapping.getClass(name);
				if( classType == null ){
					throw new Exception("不支持参数类型[" + name + "]!");
				}
				Constructor<?> constructor = classType.getConstructor(new Class[]{String.class});
				Object object = constructor.newInstance(value);
				paramValues.add(object);
			}else{
				paramValues.add(null);
			}
		}
	}

	/**
	 * 取得接口
	 * 
	 * @return
	 */
	public Class<?> getClassType() {
		return classType;
	}

	/**
	 * 取得实现类
	 * 
	 * @return
	 */
	public Class<?> getClassImpl() {
		return classImpl;
	}

	/**
	 * 取得请求参数列表
	 * 
	 * @return
	 */
	public Class<?>[] getParamTypes() {
		return paramTypes.toArray(new Class<?>[] {});
	}

	/**
	 * 取得请求参数
	 * 
	 * @return
	 */
	public Object[] getParamValues() {
		return paramValues.toArray(new Object[] {});
	}

	/**
	 * 调用方法名
	 * 
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 调用
	 * 
	 * @return
	 */
	public ServiceResponse invoke() throws Exception {
		Class<?>[] types = getParamTypes();
		Object[] values = getParamValues();
		Method method = classImpl.getMethod(methodName, types);
		Class<?> returnType = method.getReturnType();
		if (!ServiceResponse.class.equals(returnType)) {
			throw new Exception("返回参数类型必须是[" + ServiceResponse.class + "][当前类型=" + returnType + "]!");
		}
		ServiceResponse response = (ServiceResponse) method.invoke(service, values);
		return response;
	}

	@Override
	public String toString() {
		return super.toString();
//		StringBuilder builder = new StringBuilder();
//		builder.append("ServiceRequest \n[classType=").append(classType).append(";\nclassImpl=").append(classImpl).append(";\nservice=").append(service)
//				.append(";\nmethodName=").append(methodName).append(";\nparamTypes=").append(paramTypes)
//				.append(";\nparamValues=").append(paramValues).append("]");
//		return builder.toString();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	

    public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public static void main(String[] a) {
		Object[] args = new Object[]{"{id:1,name:'aa'}",null,"2"};
		
		@SuppressWarnings("unused")
        JSONArray values = new JSONArray(Arrays.asList(args));
		String s = null;
		System.out.println(new StringBuilder().append(s));
	}
}
