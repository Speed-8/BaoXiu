package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.GenghuanDao;
import com.model.Genghuan;












public class GenghuanDaoImpl extends HibernateDaoSupport implements  GenghuanDao{


	public void deleteBean(Genghuan bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Genghuan bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Genghuan selectBean(String where) {
		List<Genghuan> list = this.getHibernateTemplate().find("from Genghuan " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Genghuan "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Genghuan> selectBeanList(final int start,final int limit,final String where) {
		return (List<Genghuan>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Genghuan> list = session.createQuery("from Genghuan "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Genghuan bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
