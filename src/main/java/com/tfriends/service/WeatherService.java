package com.tfriends.service;

import com.tfriends.domain.WeatherVO;
import com.tfriends.mapper.WeatherMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
	
	@Autowired
	private WeatherMapper mapper;
	
	public WeatherVO WeatherImport(String local) {
		return mapper.winfo(local);
	}

	public boolean DustUpdate(WeatherVO weather) {
		return mapper.updatedust(weather) == 1;
	}
	
	public boolean WeatherUpdate(WeatherVO weather) {
		return mapper.updateweather(weather) == 1;
	}
}