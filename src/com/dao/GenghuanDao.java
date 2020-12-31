package com.dao;

import java.util.List;

import com.model.Genghuan;






public interface GenghuanDao  {
	
	
	
	public void insertBean(Genghuan bean);
	
	public void deleteBean(Genghuan bean);
	
	public void updateBean(Genghuan bean);

	public Genghuan selectBean(String where);
	
	public List<Genghuan> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
