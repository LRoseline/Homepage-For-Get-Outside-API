package com.tfriends.mapper;

import java.util.List;

import com.tfriends.domain.CountVO;
import com.tfriends.domain.CovidLocationVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CovidMapper {
	public List<CovidLocationVO> getList();
	
	public CovidLocationVO getloc(String lname);
	
	public int covidupdate(CovidLocationVO CovidVO);

	public void newday(String daytime);
	public CountVO countinfo(String daytime);
	public int count(CountVO vo);
}
