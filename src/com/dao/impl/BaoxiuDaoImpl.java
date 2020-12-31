package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.BaoxiuDao;
import com.model.Baoxiu;












public class BaoxiuDaoImpl extends HibernateDaoSupport implements  BaoxiuDao{


	public void deleteBean(Baoxiu bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Baoxiu bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Baoxiu selectBean(String where) {
		List<Baoxiu> list = this.getHibernateTemplate().find("from Baoxiu " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Baoxiu "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Baoxiu> selectBeanList(final int start,final int limit,final String where) {
		return (List<Baoxiu>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Baoxiu> list = session.createQuery("from Baoxiu "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Baoxiu bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
