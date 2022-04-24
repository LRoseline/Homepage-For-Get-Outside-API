package com.tfriends.domain;

import lombok.Data;

@Data
public class WeatherVO {
	private String lname;
	
	private String dlocname;
	
	private double pm10v;
	private int pm10g;
	private double pm25v;
	private int pm25g;
	
	private String wlocname;
	
	private double winddegreenow;
	private double windspeednow;
	private double humidnow;
	private String iconnow;
	private double tempnow;
	
	private String sunrise;
	private String sunset;
	
	private String icon1;
	private double temp1;
	private String icon2;
	private double temp2;
	private String icon3;
	private double temp3;
	private String icon4;
	private double temp4;
    public WeatherVO WeatherImport(String local) {
        return null;
    }
}