package com.dao;

import java.util.List;

import com.model.Kucun;






public interface KucunDao  {
	
	
	
	public void insertBean(Kucun bean);
	
	public void deleteBean(Kucun bean);
	
	public void updateBean(Kucun bean);

	public Kucun selectBean(String where);
	
	public List<Kucun> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
