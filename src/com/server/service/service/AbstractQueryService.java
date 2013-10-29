
package com.server.service.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.object.SqlQuery;


public abstract class AbstractQueryService extends AbstractService {

	public static final String MOBILE = "MOBILE";
	
	@Autowired
	protected Options options;

	@Autowired
	@Qualifier("mobileDataSource")
	protected DataSource mobileDataSource;

	protected SqlQuery createSqlQuery(String dbName, String sql) {
//		SqlServerQueryFactory factory = new SqlServerQueryFactory();
//		    if (MOBILE.endsWith(dbName)) {
//			factory.setDataSource(mobileDataSource);
//		} 
//		factory.setOptions(options);
//		return factory.getSqlQuery(sql);
	    return null;
	}
}
