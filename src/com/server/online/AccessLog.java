package com.server.online;


public class AccessLog {

	private Long id;// 编号
	protected String sessionId; // 用户区域
	protected String userName; // 用户名

	protected String serviceName; // 调用的服务名
	protected String method; // 方法名
	protected String beginTime; // 开始
	protected Long success = 1l; // 调用的方法是否成功，1=成功，0失败
	protected String executeSQL; // 执行的sql语句 预留，以后有可能会记录
	protected String errorMessage; // 错误消息
	protected String apkVersion;// 客户端版本号

	// 记录时间
	protected Long serviceInvokeTime; // 服务调用时间
	protected Long responseTime; // 写结果时间
	protected Long totalTime; // 合计时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Long getSuccess() {
		return success;
	}

	public void setSuccess(Long success) {
		this.success = success;
	}

	public String getExecuteSQL() {
		return executeSQL;
	}

	public void setExecuteSQL(String executeSQL) {
		this.executeSQL = executeSQL;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getServiceInvokeTime() {
		return serviceInvokeTime;
	}

	public void setServiceInvokeTime(Long serviceInvokeTime) {
		this.serviceInvokeTime = serviceInvokeTime;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	
	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	@Override
	public String toString() {
		return "AccessLog [apkVersion=" + apkVersion + ", beginTime="
				+ beginTime + ", errorMessage=" + errorMessage
				+ ", executeSQL=" + executeSQL + ", id=" + id + ", method="
				+ method + ", responseTime=" + responseTime
				+ ", serviceInvokeTime=" + serviceInvokeTime + ", serviceName="
				+ serviceName + ", sessionId=" + sessionId + ", success="
				+ success + ", totalTime=" + totalTime + ", userName="
				+ userName + "]";
	}

}
