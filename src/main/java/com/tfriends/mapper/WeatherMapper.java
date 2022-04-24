package com.tfriends.mapper;

import com.tfriends.domain.WeatherVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
	public WeatherVO winfo(String local);
	  
	public int updatedust(WeatherVO weather);
	  
	public int updateweather(WeatherVO weather);
}