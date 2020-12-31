package com.dao;

import java.util.List;

import com.model.Baoxiu;






public interface BaoxiuDao  {
	
	
	
	public void insertBean(Baoxiu bean);
	
	public void deleteBean(Baoxiu bean);
	
	public void updateBean(Baoxiu bean);

	public Baoxiu selectBean(String where);
	
	public List<Baoxiu> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
