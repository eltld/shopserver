package com.server.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

public interface BaseDAO {
    /**
     * 通过对象标识获得对象
     * 
     * @param cls
     *            对象类型
     * @param id
     *            标识
     * @return
     */
    <T> T get(Class<T> cls, Serializable id);

    /**
     * 更新对象
     * 
     * @param obj
     */
    void update(Object obj);

    /**
     * 保存对象
     * 
     * @param obj
     * @return
     */
    <T> T save(T obj);

    /**
     * 删除对象
     * 
     * @param obj
     */
    void delete(Object obj);

    /**
     * 删除对象集合
     * 
     * @param objs
     */
    void deleteAll(Collection<?> objs);

    /**
     * 清除缓存
     */
    void clear();

    /**
     * 合并对象
     * 
     * @param obj
     */
    void merge(Object obj);

    /**
     * 批量保存
     * 
     * @param list
     */
    void saveAll(List<?> list, int size);

    /**
     * 返回集合
     * 
     * @param hql
     * @return
     */
    List<?> findByHql(String hql);

    /**
     * 执行一条删除语句
     * 
     * @param hql
     * @return
     */
    boolean deleteByHql(String hql);

    /**
     * 保存对象
     * 
     * @param obj
     * @return
     */
    <T> T save(T obj, Session session);

    Session getDataSession();

    <T> List<T> find(Class<T> cls, String sql) throws Exception;

    <T> List<T> find(String sql) throws Exception;

    boolean excuteSql(String sql);
}
