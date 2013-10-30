
package com.server.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.online.AccessLog;
import com.server.online.OnlineUser;
import com.server.online.OnlineUserManager;
import com.wy.shopping.service.core.ServerResponse;
import com.wy.shopping.service.core.ServiceResponse;


@Controller
public class ServiceController extends AbstractController {


    /**
     * Service请求
     * 
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/service")
    public void request(InputStream in, HttpServletResponse response)
            throws Exception {
        boolean canSaveLog = true;
        AccessLog msg = new AccessLog();

        long startTime = System.currentTimeMillis();
        msg.setBeginTime("2012-12-12");
        Object sessionId = null;
        ServiceRequestHandler request = null;
        ServiceResponse sr = null;
        try {
            ServiceFinder finder = new SpringServiceFinder();
            request = new ServiceRequestHandler(in, finder);
            ThreadMap.put(ThreadMap.SESSION_ID, request.getSessionId());
            // 客户端的sessionId
            sessionId = ThreadMap.get(ThreadMap.SESSION_ID);

            // 调用下面Service以外的Service，必须保证用户在线
//            boolean bx = !ServiceExcludeUtil.restartExcludeServices
//                    .contains(request.getClassType());
//            boolean b3 = !OnlineUserManager.isOnline(sessionId);
            // if (bx && b3) {
            // throw new SessionDisconnectException("对不起，服务器可能已经重启，请重新登陆!");
            // }
            //
            // // 判断当前用户是否已经长时间未访问服务器
            // if (bx && sessionTimeOut(sessionId)) {
            // throw new SessionTimeOutException("长时间未操作，请重新登陆!");
            // }

            sr = request.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            // 返回错误信息
            String message = (cause != null ? cause.getMessage() : e.getClass()
                    .getName());
            sr = new ServerResponse(null, message, false);
            canSaveLog = false;
        }

        // 如果是登录成功后则应该重新获取sessionId
        sessionId = ThreadMap.get(ThreadMap.SESSION_ID);

        // 记录服务调用时间
        long invokeTime = System.currentTimeMillis();

        // 向客户端写响应内容
        OutputStream out = response.getOutputStream();
        Object value = sr.getContent();
        if (value instanceof InputStream) {
            response.setContentType("application/octet-stream");
            HttpUtil.write(out, (InputStream) value);
        } else {
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            HttpUtil.write(out, sr.toString(), "UTF-8");
        }

        // 记录合计时间
        long endtTime = System.currentTimeMillis();

        // 设置访问日志vo的属性
        msg.setResponseTime(endtTime - invokeTime);
        msg.setTotalTime(endtTime - startTime);
        msg.setServiceInvokeTime(invokeTime - startTime);
        msg.setMethod(request.getMethodName());
        msg.setServiceName(request.getClassType().toString());
        msg.setApkVersion(request.getApkVersion());
        if (sessionId != null) {
            msg.setSessionId(sessionId.toString());
            OnlineUser user = OnlineUserManager.getOnlineUser(sessionId);
            if (user != null) {
                msg.setUserName(user.getUserName());
            }
        }
        // 成功
        if (sr.isSuccess()) {
            msg.setSuccess(1l);
        } else {
            // 失败
            msg.setSuccess(0l);
            msg.setErrorMessage(sr.getMessage());
        }

        // 添加日志
        if (canSaveLog) {
//            AccessLogUtil.addLog(msg);
        }

        // 退出系统
        boolean logout = request.getMethodName().equals("logout");
        boolean isOnline=OnlineUserManager.isOnline(sessionId);
        if (logout && isOnline ) {
            OnlineUserManager.logout(sessionId.toString());
        }
        for (int i = 0; i < 1; i++) {
//            log.debug("在线人数:" + OnlineUserManager.getOnlineUserMap().size()
//                    + "==============================================");
            Set<String> key = OnlineUserManager.getOnlineUserMap().keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext();) {
                String s = it.next();
                OnlineUser ou = OnlineUserManager.getOnlineUserMap().get(s);
                System.out.println("在线的sessionId：" + ou.getSessionId());
                System.out.println(ou.getUserId() + ":" + ou.getUserName());
            }
        }

    }

    public boolean sessionTimeOut(Object sessionId) {
        // 判断当前用户是否已经长时间未访问服务器
        OnlineUser user = OnlineUserManager.getOnlineUser(sessionId);
        if (user != null) {
            Long currTime = System.currentTimeMillis();
            if (currTime - user.getLastAccessTime() > OnlineUserManager.OUT_TIME) {
                OnlineUserManager.logout(sessionId);
                return true;
            } else {
                user.setLastAccessTime(currTime);
            }
        }
        return false;
    }

}
