package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.KucunDao;
import com.model.Kucun;












public class KucunDaoImpl extends HibernateDaoSupport implements  KucunDao{


	public void deleteBean(Kucun bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Kucun bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Kucun selectBean(String where) {
		List<Kucun> list = this.getHibernateTemplate().find("from Kucun " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Kucun "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Kucun> selectBeanList(final int start,final int limit,final String where) {
		return (List<Kucun>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Kucun> list = session.createQuery("from Kucun "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Kucun bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
