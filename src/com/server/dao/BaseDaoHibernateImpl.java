

package com.server.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoHibernateImpl extends HibernateDaoSupport implements BaseDAO{

	@Override
	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	@Override
	public void deleteAll(Collection<?> objs) {
		getHibernateTemplate().deleteAll(objs);
	}

	@Override
	public <T> T get(Class<T> cls, Serializable id) {
		return getHibernateTemplate().get(cls, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T save(T obj) {
		return (T)getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}

	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	@Override
	public void merge(Object obj) {
		getHibernateTemplate().merge(obj);
	}

	@Override
	public void saveAll(final List<?> list, final int size) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				for (int j = 0; j < list.size(); j++) {
					Object object = list.get(j);
					session.save(object);
					if (j % size == 0) {
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
	}

	@Override
	public List<?> findByHql(String hql) {
			List<?> list = getHibernateTemplate().find(hql);
			return list;	
	}

	@Override
	public boolean deleteByHql(String hql) {
		Session session = null ;
		try {
		    session = getHibernateTemplate().getSessionFactory().openSession();
			Transaction ts = session.beginTransaction();
			session.delete(hql);
			ts.commit();
			return true ;
		} catch (Exception e) {
			new RuntimeException("删除语句执行错误");
			return false;
		} finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T obj, Session session) {
		return (T)session.save(obj);
	}

	@Override
	public Session getDataSession() {
		return getHibernateTemplate().getSessionFactory().openSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(Class<T> cls, String sql) throws Exception{
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			List<T> list = (List<T>) session.createSQLQuery(sql).addEntity(cls).list(); 
			return list ;
		} catch (Exception e) {
			new RuntimeException("通过sql查询对象错误");
			return null;
		} finally{
			if (session != null) {
				session.close();
			}
		}
	}

    @Override
    public <T> List<T> find(String sql) throws Exception {
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            List<T> list = (List<T>) session.createSQLQuery(sql).list();
            return list ;
        } catch (Exception e) {
            new RuntimeException("通过sql查询对象错误");
            return null;
        } finally{
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean excuteSql(final String sql) {
    
        Session session = null ;
        try { 
            session = getHibernateTemplate().getSessionFactory().openSession();
            Transaction ts = session.beginTransaction();
            Statement state = session.connection().createStatement();
            state.execute(sql);
            state.close();
            ts.commit();
            return true ;
        } catch (Exception e) {
            new RuntimeException("删除语句执行错误");
            return false;
        } finally{
            if (session != null) {
                session.close();
            }
        }
    }
	
}
