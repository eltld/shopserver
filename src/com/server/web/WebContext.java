package com.server.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebContext {

    public static final String HTTP_REQUEST = "HTTP_REQUEST";

    public static final String HTTP_RESPONSE = "HTTP_RESPONSE";

    public static HttpServletRequest getRequest() {
        Object request = ThreadMap.get(HTTP_REQUEST);
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse getResponse() {
        Object response = ThreadMap.get(HTTP_RESPONSE);
        return (HttpServletResponse) response;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletContext getServletContext() {
        return getSession().getServletContext();
    }
}
