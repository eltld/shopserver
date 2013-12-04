package com.wy.shopping.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chatserver.ChatServerHandler;
import com.server.dao.BaseDAO;
import com.server.utils.StringUtils;
import com.wy.shopping.service.AbstractQueryService;
import com.wy.shopping.service.LoginService;
import com.wy.shopping.service.core.ServiceResponse;
import com.wy.vo.Info;

@Service("loginService")
public class LoginServiceImpl extends AbstractQueryService implements LoginService {

    @Autowired
    @Qualifier("mobileBaseDAO")
    private BaseDAO mobileBaseDAO;

    @Override
    public ServiceResponse login(JSONObject user) {
        Info info = new Info();// 客户端提交的用户对象
        try {
            info.setName(user.getString("name"));
            info.setPass(user.getString("pass"));
            String sql = "SELECT * FROM info where name = '" + info.getName() + "'";
            List<Info> result = mobileBaseDAO.find(Info.class, sql);
           //检查用户名存不存在
            if (StringUtils.isEmpty(result)) {
                mobileBaseDAO.save(info);
                System.out.println("%%%%%%%%%%%%%%"+ChatServerHandler.onlineUser.size());
                return response(ChatServerHandler.onlineUser);
            }else{
                String cql = "SELECT * FROM info where name = '" + info.getName() + "' and pass = '" + info.getPass() + "'";
                List<Info> data = mobileBaseDAO.find(Info.class, cql);
                if(StringUtils.isEmpty(data)){
                    return  faild("登录失败");
                }else{
                    System.out.println("%%%%%%%%%%%%%%"+ChatServerHandler.onlineUser.size());
                    return response(ChatServerHandler.onlineUser);
                }
            }
        } catch (Exception e) {
           System.out.println(e);
        }
        return null;
    }
}
