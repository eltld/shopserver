
package com.server.web;

import java.util.HashMap;
import java.util.Map;


public final class ThreadMap {
	
	public static final String SESSION_ID = "SESSION_ID";
	
	public static final String APK_VERSION = "APK_VERSION";
	
    private ThreadMap() {

    }

    /**
     * 线程容器
     */
    private static final ThreadLocal<Map<Object, Object>> local = new ThreadLocal$Map<Map<Object, Object>>();

    /**
     * 设置线程中的对象
     * 
     * @param key
     * @param value
     */
    public static void put(Object key, Object value) {
        Map<Object, Object> m = getThreadMap();
        m.put(key, value);
    }

    /**
     * 取得线程中的对象
     * 
     * @param key
     * @return
     */
    public static Object get(Object key) {
        Map<Object, Object> m = getThreadMap();
        return m.get(key);
    }

    /**
     * 删除线程中的对象
     * 
     * @param key
     * @return
     */
    public static void remove(Object key) {
        Map<Object, Object> m = getThreadMap();
        m.remove(key);
    }

    /**
     * 清空线程Map
     * 
     * @param key
     * @return
     */
    public static void clear(Object key) {
        Map<Object, Object> m = getThreadMap();
        m.clear();
    }

    /**
     * 清空线程Map
     * 
     * @param key
     * @return
     */
    public static void putAll(Map<Object, Object> p) {
        Map<Object, Object> m = getThreadMap();
        m.putAll(p);
    }

    /**
     * 取得线程中的Map
     * 
     * @return
     */
    private static Map<Object, Object> getThreadMap() {
        return (Map<Object, Object>) local.get();
    }

    /**
     * 内部类实现
     * 
     * @param <T>
     */
    private static class ThreadLocal$Map<T> extends ThreadLocal<Map<Object, Object>> {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }
    }
}
