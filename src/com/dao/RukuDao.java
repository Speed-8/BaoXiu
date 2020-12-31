package com.dao;

import java.util.List;

import com.model.Ruku;






public interface RukuDao  {
	
	
	
	public void insertBean(Ruku bean);
	
	public void deleteBean(Ruku bean);
	
	public void updateBean(Ruku bean);

	public Ruku selectBean(String where);
	
	public List<Ruku> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
