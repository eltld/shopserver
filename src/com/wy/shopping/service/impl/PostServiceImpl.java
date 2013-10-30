package com.wy.shopping.service.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.wy.shopping.service.AbstractQueryService;
import com.wy.shopping.service.PostService;
import com.wy.shopping.service.core.ServiceResponse;

@Service("postService")
public class PostServiceImpl extends AbstractQueryService implements PostService {

//    @Autowired
//    @Qualifier("mobileBaseDAO")
    // private BaseDAO mobileBaseDAO;
    /** 每页的数量 */
    private int pageSize = 15;

    @Override
    public ServiceResponse post(JSONObject postDetail) {
        return null;
    }

    @Override
    public ServiceResponse getPostItem(Integer userId, String lastTime) {
        return null;

    }

    @Override
    public ServiceResponse getPostTips(Integer postId) {
        return null;
    }

    @Override
    public ServiceResponse savePostTips(JSONObject tips) {
        return null;
    }

    @Override
    public ServiceResponse upPostTips(Integer postId) {
        return null;
    }

    @Override
    public ServiceResponse downPostTips(Integer postId) {
        return null;
    }

    @Override
    public ServiceResponse getOnlinePost(Long pageNum) {
        return null;
    }

    @Override
    public ServiceResponse getNewPost(String lastTime) {
        return null;
    }

    @Override
    public ServiceResponse deletePostByUserId(Integer uid) {
        return null;
    }

    @Override
    public ServiceResponse deletePostTipsByUserId(Integer uid) {
        return null;
    }

    @Override
    public ServiceResponse getTipsByUserId(Integer uid) {
        return null;
    }

    @Override
    public ServiceResponse getSinglePostByTipsId(Integer tipsId, Integer postId) {
        return null;
    }

    @Override
    public ServiceResponse testConnectServer(Integer param) {
        if(param==1){
            return response("连接成功");
        }
       return response("发送了请求");
    }
}
