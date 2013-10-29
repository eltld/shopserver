//package com.server.dao;
//
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.object.SqlQuery;
//
//import com.server.service.service.Options;
//
//public interface QueryFactory {
//
//    /**
//     * 查询，新增，删除，修改
//     * 
//     * @param sql
//     * @return
//     */
//    public SqlQuery getSqlQuery(String sql);
//
//    /**
//     * 查询，新增，删除，修改
//     * 
//     * @param sql
//     * @return
//     */
//    public SqlQuery getSqlQuery(String sql, Map<String, Object> qryMap);
//
//    /**
//     * 设置数据源
//     * 
//     * @param dataSource
//     */
//    public void setDataSource(DataSource dataSource);
//
//    /**
//     * 设置参数
//     * 
//     * @param options
//     */
//    public void setOptions(Options options);
//}
