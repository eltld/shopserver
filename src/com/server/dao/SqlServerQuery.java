//package com.server.dao;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.Predicate;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.mysql.jdbc.ResultSetRow;
//import com.sjump.commons.util.SqlUtils;
//import com.sjump.services.query.QueryResult;
//
//
//public class SqlServerQuery extends AbstractQuery {
//
//	private static Log log = LogFactory.getLog(SqlServerQuery.class);
//
//	@Override
//	public QueryResult execute() throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		QueryResult result = new QueryResult();
//		try {
//			conn = dataSource.getConnection();
//			String sql = prepare(this.sql);
//			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			stmt.setQueryTimeout(options.getTimeOut());
//			result.setSql(SqlServerUtils.getRuntimeSql(sql, values));
//			setPrepareStatementValues(stmt, values);
//			rs = stmt.executeQuery();
//			count=0;
//			while(rs.next()){
//			    count++;
//			}
//			System.out.println("记录总数："+count);
//			// 计算总数
//			if (isPaged()) {
//				if (!isCount()) {
//					rs.last();
//					result.setPageCount(rs.getRow());
//				} else {
//					result.setPageCount(count);
//				}
//				result.setPageSize(pageSize);
//				result.setPageStart(pageStart);
//			}
//			// 计算起始位置
//			int offset = (pageStart - 1);
//			rs.absolute( offset >= 0 ? offset : 1);
//			if( isPaged() ){
//				ResultSetRow resultSet = new ResultSetRow(rs, pageSize);
//				result.setRows(resultSet.getRows());
//				result.setColumns(resultSet.getColumns());
//			}else{
//				ResultSetRow resultSet = new ResultSetRow(rs);
//				result.setRows(resultSet.getRows());
//				result.setColumns(resultSet.getColumns());
//			}
//			return result;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			log.error(ex);
//			log.error(SqlServerUtils.getRuntimeSql(sql, values));
//			LogUtil.saveError(ex);
//			throw new Exception(SqlServerUtils.getRuntimeSql(sql, values),ex);
//		} finally {
//			SqlServerUtils.closeAll(conn, stmt, rs);
//		}
//	}
//
//	@SuppressWarnings("all")
//	public int count() throws Exception {
//		String sql = prepare(this.sql);
//		return count(sql);
//	}
//
//	/**
//	 * 执行查询
//	 * 
//	 * @param sql
//	 * @return
//	 * @throws DBException
//	 */
//	@SuppressWarnings("unused")
//	private Object executeQuery(String sql) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dataSource.getConnection();
//			stmt = conn.prepareStatement(sql);
//			stmt.setQueryTimeout(options.getTimeOut());
//			setPrepareStatementValues(stmt, values);
//			rs = stmt.executeQuery();
//			return new ResultSetRow(rs).getColumns();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			log.error(ex);
//			log.error(SqlServerUtils.getRuntimeSql(sql, values));
//			LogUtil.saveError(ex);
//			throw new Exception(SqlServerUtils.getRuntimeSql(sql, values),ex);
//		} finally {
//			SqlServerUtils.closeAll(conn, stmt, rs);
//		}
//	}
//
//	/**
//	 * 查询总数
//	 * 
//	 * @param sql
//	 * @return
//	 * @throws DBException
//	 */
//	private int count(String sql) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dataSource.getConnection();
//			stmt = conn.prepareStatement(sql);
//			stmt.setQueryTimeout(options.getTimeOut());
//			setPrepareStatementValues(stmt, values);
//			rs = stmt.executeQuery();
//			rs.last();
//			return rs.getRow();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			log.error(ex);
//			log.error(SqlServerUtils.getRuntimeSql(sql, values));
//			LogUtil.saveError(ex);
//			throw new Exception(SqlServerUtils.getRuntimeSql(sql, values),ex);
//		} finally {
//			SqlServerUtils.closeAll(conn, stmt, rs);
//		}
//	}
//
//	/**
//	 * 设置参数值
//	 * 
//	 * @param stmt
//	 * @param values
//	 */
//	private void setPrepareStatementValues(PreparedStatement stmt, List<Object> values) throws SQLException {
//		log.debug("Set query time out:" + options.getTimeOut());
//		log.info(SqlServerUtils.getRuntimeSql(sql, values));
//		for (int i = 0; i < values.size(); i++) {
//			if (values.get(i) instanceof Integer)
//				stmt.setInt(i + 1, ((Integer) values.get(i)).intValue());
//			else if (values.get(i) instanceof Long)
//				stmt.setLong(i + 1, ((Long) values.get(i)).longValue());
//			else if (values.get(i) instanceof String)
//				stmt.setString(i + 1, values.get(i).toString());
//			else if (values.get(i) instanceof java.sql.Date)
//				stmt.setDate(i + 1, (java.sql.Date) values.get(i));
//			else if (values.get(i) instanceof java.sql.Timestamp)
//				stmt.setTimestamp(i + 1, (java.sql.Timestamp) values.get(i));
//			else if (values.get(i) instanceof java.util.Date)
//				stmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values.get(i)).getTime()));
//			else if (values.get(i) instanceof Double)
//				stmt.setDouble(i + 1, ((Double) values.get(i)).doubleValue());
//			else if (values.get(i) instanceof Float)
//				stmt.setFloat(i + 1, ((Float) values.get(i)).floatValue());
//			else
//				stmt.setObject(i + 1, values.get(i));
//		}
//	}
//
//	@Override
//	public int update() throws Exception {
//		String sql = prepare(this.sql);
//		return executeUpdate(sql);
//	}
//
//	/**
//	 * DML
//	 * 
//	 * @param sql
//	 * @return
//	 * @throws DBException
//	 */
//	private int executeUpdate(String sql) throws Exception {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		try {
//			conn = dataSource.getConnection();
//			stmt = conn.prepareStatement(sql);
//			log.debug(SqlServerUtils.getRuntimeSql(sql, values));
//			for (int i = 0; i < values.size(); i++) {
//				if (values.get(i) instanceof Integer)
//					stmt.setInt(i + 1, ((Integer) values.get(i)).intValue());
//				else if (values.get(i) instanceof Long)
//					stmt.setLong(i + 1, ((Long) values.get(i)).longValue());
//				else if (values.get(i) instanceof String) {
//					String s = (String) values.get(i);
//					if (s.getBytes().length < 4000)
//						stmt.setString(i + 1, values.get(i).toString());
//					else {
//						if (s.getBytes().length > 4000)
//							stmt.setCharacterStream(i + 1, new InputStreamReader(new ByteArrayInputStream((new String(s.getBytes(), 0, 4000)).getBytes())),
//									4000);
//						else
//							stmt.setCharacterStream(i + 1, new InputStreamReader(new ByteArrayInputStream(s.getBytes())), s.length());
//					}
//				} else if (values.get(i) instanceof java.sql.Date)
//					stmt.setDate(i + 1, (java.sql.Date) values.get(i));
//				else if (values.get(i) instanceof java.sql.Timestamp)
//					stmt.setTimestamp(i + 1, (java.sql.Timestamp) values.get(i));
//				else if (values.get(i) instanceof java.util.Date)
//					stmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values.get(i)).getTime()));
//				else if (values.get(i) instanceof Double)
//					stmt.setDouble(i + 1, ((Double) values.get(i)).doubleValue());
//				else if (values.get(i) instanceof Float)
//					stmt.setFloat(i + 1, ((Float) values.get(i)).floatValue());
//				else if (values.get(i) instanceof BigDecimal)
//					stmt.setBigDecimal(i + 1, (BigDecimal) values.get(i));
//				else
//					stmt.setObject(i + 1, values.get(i));
//			}
//			int count = stmt.executeUpdate();
//			conn.commit();
//			return count;
//		} catch (SQLException ex) {
//			log.error(ex);
//			log.error(SqlServerUtils.getRuntimeSql(sql, values));
//			LogUtil.saveError(ex);
//			throw new Exception(SqlServerUtils.getRuntimeSql(sql, values),ex);
//		} finally {
//			SqlServerUtils.closeAll(conn, stmt, null);
//		}
//	}
//
//	@Override
//	public List<ColumnBean> getColumns() throws Exception {
//		String sql = prepare(this.sql);
//		String qrySql = SqlServerUtils.getTopRowsSql(sql, 0);
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dataSource.getConnection();
//			stmt = conn.prepareStatement(qrySql);
//			stmt.setQueryTimeout(options.getTimeOut());
//			setPrepareStatementValues(stmt, values);
//			rs = stmt.executeQuery();
//			return new ResultSetRow(rs).getColumns();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			log.error(ex);
//			log.error(SqlServerUtils.getRuntimeSql(sql, values));
//			LogUtil.saveError(ex);
//			throw new Exception(SqlServerUtils.getRuntimeSql(sql, values),ex);
//		} finally {
//			SqlServerUtils.closeAll(conn, stmt, rs);
//		}
//	}
//
//	/**
//	 * 取得运行时SQL
//	 * 
//	 * @return
//	 */
//	@Override
//	public String getRuntimeSql() {
//		String sql = prepare(this.sql);
//		return SqlServerUtils.getRuntimeSql(sql, values);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) {
//		String sql = "select to_char('24H:MI:SS') from cust";
//		List<String> params = SqlUtils.getParameterList(sql);
//		List<Object> paramValues = new ArrayList<Object>();
//		Map paramMap = new HashMap();
//		if (!paramMap.isEmpty()) {
//			// 过滤掉"MI","SS"
//			CollectionUtils.filter(params, new Predicate() {
//				public boolean evaluate(Object object) {
//					String o = (String) object;
//					return !("MI".equalsIgnoreCase(o) || "SS".equalsIgnoreCase(o));
//				}
//			});
//			for (String param : params) {
//				paramValues.add(paramMap.get(param));
//				sql = StringUtils.replaceOnce(sql, ":" + param, "?");
//			}
//		}
//		System.out.println(sql);
//	}
//}
