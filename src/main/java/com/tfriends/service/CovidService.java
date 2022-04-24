package com.tfriends.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tfriends.domain.CountVO;
import com.tfriends.domain.CovidLocationVO;
import com.tfriends.mapper.CovidMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovidService {
	
	@Autowired
	private CovidMapper mapper;

	public List<CovidLocationVO> ListGet() {
		return mapper.getList();
	}

	public CovidLocationVO LocGet(String lname) {
		return mapper.getloc(lname);
	}

	public boolean CovidUpdate(CovidLocationVO CovidVO) {
		return mapper.covidupdate(CovidVO) == 1;
	}

	public boolean PrefabCount(CountVO vo) {
		return mapper.count(vo) == 1;
	}

	public CountVO CountLoad(String daytime) {	        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        daytime = sdf.format(date);

		return mapper.countinfo(daytime);
	}

	public void newday(String daytime) {
		mapper.newday(daytime);
	}
}