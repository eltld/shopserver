//
//package com.server.dao;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.jdbc.object.SqlQuery;
//
//import com.server.service.service.Options;
//
//
//public class SqlServerQueryFactory implements QueryFactory{
//
//    @SuppressWarnings("unused")
//	private static Log log = LogFactory.getLog(SqlServerQueryFactory.class);
//
//    /**
//     * 数据源
//     */
//    private DataSource dataSource;
//    
//    /**
//     * 参数配置
//     */
//    private Options options = new Options();
//    
//    @Override
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//    
//    @Override
//    public void setOptions(Options options) {
//        this.options = options;
//    }
//
//    /**
//     * 查询，新增，删除，修改
//     * @param sql
//     * @return
//     */
//    @Override
//    public SqlQuery getSqlQuery(String sql) {
//        return getSqlQuery(sql,new HashMap<String,Object>());
//    }
//    
//    /**
//     * 查询，新增，删除，修改
//     * @param sql
//     * @param paramMap
//     * @return
//     */
//    @Override
//    public SqlQuery getSqlQuery(String sql, Map<String,Object> paramMap) {
//        SqlServerQuery query = new SqlServerQuery();
//        query.setDataSource(dataSource);
//        query.setSql(sql);
//        query.setParamMap(paramMap);
//        query.setOptions(options);
//        return query;
//    }
//    
//    public static void main(String[] args) throws Exception {
//        
//    }
//}
