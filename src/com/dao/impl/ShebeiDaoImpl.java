package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.ShebeiDao;
import com.model.Shebei;












public class ShebeiDaoImpl extends HibernateDaoSupport implements  ShebeiDao{


	public void deleteBean(Shebei bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Shebei bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Shebei selectBean(String where) {
		List<Shebei> list = this.getHibernateTemplate().find("from Shebei " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Shebei "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Shebei> selectBeanList(final int start,final int limit,final String where) {
		return (List<Shebei>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Shebei> list = session.createQuery("from Shebei "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Shebei bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
