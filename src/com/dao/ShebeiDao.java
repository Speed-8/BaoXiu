package com.dao;

import java.util.List;

import com.model.Shebei;






public interface ShebeiDao  {
	
	
	
	public void insertBean(Shebei bean);
	
	public void deleteBean(Shebei bean);
	
	public void updateBean(Shebei bean);

	public Shebei selectBean(String where);
	
	public List<Shebei> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
