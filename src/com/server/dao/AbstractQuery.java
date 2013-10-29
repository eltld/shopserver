///*
// * 创建日期 2009-11-27
// *
// * 成都天和软件公司
// * 电话：028-85425861
// * 传真：028-85425861-8008
// * 邮编：610041 
// * 版权所有
// */
//package com.server.dao;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.th.mobile.collection.server.util.SqlUtils;
//
///**
// * 抽象查询
// * 
// * @author: 王文成
// * @version: 1.0
// * @since 2009-11-27
// */
//public abstract class AbstractQuery extends DataSourceSupport implements SqlQuery {
//
//	@SuppressWarnings("unused")
//	private static Log log = LogFactory.getLog(AbstractQuery.class);
//	
//	protected static final String FORMAT_LIST = "FORMAT_LIST";
//
//	protected static final String FORMAT_REPORT = "FORMAT_REPORT";
//
//	protected static final String FORMAT_XML = "FORMAT_XML";
//	
//	protected static final String META_DATA = "META_DATA";
//
//	protected String sql;
//
//	protected List<Object> values;
//
//	protected Map<Object, Object> paramMap = new HashMap<Object, Object>();
//
//	protected int pageStart = 0;
//
//	protected int pageSize = 50;
//	
//	protected int count = -1;
//	
//	protected boolean isPaged;
//
//	protected String countType;
//	
//	@Override
//	public SqlQuery setParamMap(Map<String, Object> paramMap) {
//		this.paramMap.putAll(paramMap);
//		return this;
//	}
//
//	public void setSql(String sql) {
//		this.sql = sql;
//	}
//	
//	@Override
//	public void reset() {
//		this.pageStart = 0;
//		this.pageSize = 0;
//	}
//	
//	@Override
//	public SqlQuery setPage(int pageStart, int pageSize) {
//		this.pageStart = pageStart;
//		this.pageSize = pageSize;
//		this.isPaged = false;
//		return this;
//	}
//
//	@Override
//	public SqlQuery setCount(int count) {
//		this.count = (count <= 0 ? -1 : count);
//		return this;
//	}
//
//	/**
//	 * 初始化SQL
//	 */
//	protected String prepare(String sql) {
//		List<String> params = SqlUtils.getParameterList(sql);
//		List<Object> paramValues = new ArrayList<Object>();
//		if (!paramMap.isEmpty()) {
//			for (String param : params) {
//				paramValues.add(paramMap.get(param));
//				sql = StringUtils.replaceOnce(sql, ":" + param, "?");
//			}
//		}
//		this.values = paramValues;
//		return sql;
//	}
//
//	/**
//	 * 是否分页
//	 * 
//	 * @return
//	 */
//	public boolean isPaged() {
//		return isPaged;
//	}
//
//	@Override
//	public SqlQuery setParam(String name, Object value) {
//		paramMap.put(name, value);
//		return this;
//	}
//	
//	/**
//	 * 是否需要计算总数
//	 * @return
//	 */
//	public boolean isCount(){
//		return count != -1;
//	}
//
//	@Override
//	public List<ColumnBean> getColumns() throws Exception {
//		return new ArrayList<ColumnBean>(0);
//	}
//}
