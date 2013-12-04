package com.wy.shopping.service;

import org.json.JSONObject;

import com.wy.shopping.service.core.ServiceResponse;

public interface LoginService {
    
    ServiceResponse login(JSONObject user);
    
}
