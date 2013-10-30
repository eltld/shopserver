
package com.wy.shopping.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.server.dao.BaseDAO;



public abstract class AbstractUpdateService extends AbstractService {
	
	@Autowired
	@Qualifier("mobileBaseDAO")
	protected BaseDAO mobileBaseDAO;

	protected Session getSession() throws Exception{
		return mobileBaseDAO.getDataSession();
	}
	
	protected boolean executeManyChangeSQL(List<String> sqls, Session session)
			throws HibernateException, SQLException {
		boolean bln = true;
		if (sqls == null || sqls.size() == 0)
			return bln;

		StringBuffer executeSql = new StringBuffer();
		Statement ps = session.connection().createStatement();
		for (Object sql : sqls) {
			executeSql.append(sql);
			executeSql.append(";\n");
			System.out.println(sql);
			ps.addBatch(sql.toString());
		}
		ps.executeBatch();
		ps.close();
		return bln;
	}
	
	protected boolean executeOneChangeSQL(String sql, Session session)
			throws HibernateException, SQLException {
		boolean bln = true; 
		Statement ps = session.connection().createStatement(); 
		ps.addBatch(sql.toString());
		ps.executeBatch();
		ps.close();
		return bln;
	}
	
    /**
     * 关闭所有会话
     * */
	protected void closeSession(Session session) {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                session = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
