package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.RukuDao;
import com.model.Ruku;












public class RukuDaoImpl extends HibernateDaoSupport implements  RukuDao{


	public void deleteBean(Ruku bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Ruku bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Ruku selectBean(String where) {
		List<Ruku> list = this.getHibernateTemplate().find("from Ruku " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Ruku "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Ruku> selectBeanList(final int start,final int limit,final String where) {
		return (List<Ruku>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Ruku> list = session.createQuery("from Ruku "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Ruku bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
