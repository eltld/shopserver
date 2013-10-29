package com.server.web;

import com.server.utils.SpringUtils;
import com.server.utils.StringUtils;

public class SpringServiceFinder implements ServiceFinder {

    @Override
    public Object find(String className) throws Exception {
        String shortName = className.replaceAll(".*\\.(\\w+)$", "$1");
        String beanName = StringUtils.firstCharLowerCase(shortName);
        Object bean = SpringUtils.getBean(beanName);
        if (bean == null)
            throw new Exception("无法在Spring中到Service实例[beanName=" + beanName + "]");
        return bean;
    }
}
