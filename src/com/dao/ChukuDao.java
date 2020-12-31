package com.dao;

import java.util.List;

import com.model.Chuku;






public interface ChukuDao  {
	
	
	
	public void insertBean(Chuku bean);
	
	public void deleteBean(Chuku bean);
	
	public void updateBean(Chuku bean);

	public Chuku selectBean(String where);
	
	public List<Chuku> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
