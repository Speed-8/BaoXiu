package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.ChukuDao;
import com.model.Chuku;












public class ChukuDaoImpl extends HibernateDaoSupport implements  ChukuDao{


	public void deleteBean(Chuku bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Chuku bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Chuku selectBean(String where) {
		List<Chuku> list = this.getHibernateTemplate().find("from Chuku " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Chuku "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Chuku> selectBeanList(final int start,final int limit,final String where) {
		return (List<Chuku>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Chuku> list = session.createQuery("from Chuku "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Chuku bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
