package com.server.service.core;


public class ServerResponse implements ServiceResponse {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回数据
     */
    private Object content;

    /**
     * 返回信息
     */
    private String message;

    public ServerResponse(Object content, String message, boolean success) {
        this.success = success;
        this.content = content;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerResponse [success=" + success + ", content=" + content + ", message=" + message + "]";
    }

}
