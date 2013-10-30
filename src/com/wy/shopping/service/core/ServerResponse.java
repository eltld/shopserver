package com.wy.shopping.service.core;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.server.utils.StringUtils;


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
        JSONObject object = new JSONObject();
        try {
            if( content instanceof InputStream ){
                object.put(Param.CONTENT,InputStream.class.getName());
            }else{
                object.put(Param.CONTENT,StringUtils.asString(content));
            }
            object.put(Param.MESSAGE, StringUtils.asString(message));
            object.put(Param.SUCCESS, success);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
