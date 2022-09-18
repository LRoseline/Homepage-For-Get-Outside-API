package com.tfriends.vrcsync.prefab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tfriends.domain.WeatherVO;
import com.tfriends.keys.PrivacyKey;
// import com.tfriends.service.KeyTrigger;
import com.tfriends.service.SettingService;
import com.tfriends.service.WeatherService;

@Component
public class WeatherTest extends PrivacyKey {

	private int nor = 1;
	private int pn = 1;
	public double UTC = 9;
	
	@Autowired
	private WeatherService service;

	@Autowired
	private SettingService k;
	
	@Scheduled(cron = "6 0 * * * * ")
	public void WeatherTry() throws Exception {
		SimpleDateFormat ForeTimeStamp = new SimpleDateFormat("HH:mm", Locale.KOREA);
		
		for (int i = 0; i < loc.length; i++) {
			String urlcom = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat[i] + "&lon=" + lon[i] + 
			"&appid=" + k.SettingLoad("openweathermap1").getValue() + "&cnt=1&units=metric";
			
			URL urlweather = new URL(urlcom);
			String jsonline = "";
			String jsonresult = "";
			
			BufferedReader jsonbf = new BufferedReader(new InputStreamReader(urlweather.openStream()));
			
			jsonline = jsonbf.readLine();
			jsonresult = jsonresult.concat(jsonline);

			JSONObject jObject = new JSONObject(jsonresult);

			// current 그룹
			JSONObject CurrentResult = jObject.getJSONObject("current");
			// System.out.println("현재 날씨");

			// - 일출시간 객체
			Long UnixTimeRise = CurrentResult.getLong("sunrise");
			Long UnixConvRise = Long.parseLong((UnixTimeRise)+"000");
			String SunRiseTime = ForeTimeStamp.format(UnixConvRise);
			// // System.out.println("일출시간 : "+SunRiseTime);

			// - 일몰시간 객체
			Long UnixTimeSet = CurrentResult.getLong("sunset");
			Long UnixConvSet = Long.parseLong((UnixTimeSet)+"000");
			String SunSetTime = ForeTimeStamp.format(UnixConvSet);
			// System.out.println("일몰시간 : "+SunSetTime);

			// - 현재기온 객체
			Long temp = Math.round(CurrentResult.getDouble("temp"));
			// System.out.println("현재기온 : "+temp+"℃");

			// - 현재습도 객체
			Long Humid = CurrentResult.getLong("humidity");
			// System.out.println("현재습도 : "+ Humid);

			// - 바람방향 객체
			Long WindDeg = CurrentResult.getLong("wind_deg");
			// System.out.println("바람방향 : "+WindDeg);
			
			// - 바람속도 객체
			Long WindSpeed = CurrentResult.getLong("wind_speed");
			// System.out.println("바람속도 : "+WindSpeed);

			// - 업뎃날짜 객체
			/*
			Long UnixTimeLatest = CurrentResult.getLong("dt");
			Long UnixConvLatest = Long.parseLong((UnixTimeLatest)+"000");
			// System.out.println("업뎃날짜 : "+ForeTimeStampAPM.format(UnixConvLatest));
			*/

			// - weather 그룹
			JSONArray WeatherArrayResult = CurrentResult.getJSONArray("weather");
			JSONObject WeatherArray0 = WeatherArrayResult.getJSONObject(0);

			// -	- 현재날씨 객체
			String CurrentIcon = WeatherArray0.getString("icon");
			// String CurrentMain = WeatherArray0.getString("main");
			// System.out.println("현재날씨 : "+CurrentIcon+", "+CurrentMain);

			// daily 그룹
			JSONArray Dailyresult = jObject.getJSONArray("daily");

			// - 1일차
			JSONObject day1 = Dailyresult.getJSONObject(1);
			// System.out.println("\n1일차 날씨");
			// -	- 기온
			JSONObject day1t = day1.getJSONObject("temp");
			Long day1tday = Math.round(day1t.getDouble("day"));
			// System.out.println("향후기온 : "+day1tday+"℃");
			// -	- 날씨
			JSONArray day1w = day1.getJSONArray("weather");
			JSONObject day1w0 = day1w.getJSONObject(0);
			String Day1Icon = day1w0.getString("icon");
			// String Day1Main = day1w0.getString("main");
			// System.out.println("향후날씨 : "+Day1Icon+", "+Day1Main);

			// - 2일차
			JSONObject day2 = Dailyresult.getJSONObject(2);
			// System.out.println("\n2일차 날씨");
			// -	- 기온
			JSONObject day2t = day2.getJSONObject("temp");
			Long day2tday = Math.round(day2t.getDouble("day"));
			// System.out.println("향후기온 : "+day2tday+"℃");
			// -	- 날씨
			JSONArray day2w = day2.getJSONArray("weather");
			JSONObject day2w0 = day2w.getJSONObject(0);
			String Day2Icon = day2w0.getString("icon");
			// String Day2Main = day2w0.getString("main");
			// System.out.println("향후날씨 : "+Day2Icon+", "+Day2Main);

			// - 3일차
			JSONObject day3 = Dailyresult.getJSONObject(3);
			// System.out.println("\n3일차 날씨");
			// -	- 기온
			JSONObject day3t = day3.getJSONObject("temp");
			Long day3tday = Math.round(day3t.getDouble("day"));
			// System.out.println("향후기온 : "+day3tday+"℃");
			// -	- 날씨
			JSONArray day3w = day3.getJSONArray("weather");
			JSONObject day3w0 = day3w.getJSONObject(0);
			String Day3Icon = day3w0.getString("icon");
			// String Day3Main = day3w0.getString("main");
			// System.out.println("향후날씨 : "+Day3Icon+", "+Day3Main);

			// - 4일차
			JSONObject day4 = Dailyresult.getJSONObject(4);
			// System.out.println("\n4일차 날씨");
			// -	- 기온
			JSONObject day4t = day4.getJSONObject("temp");
			Long day4tday = Math.round(day4t.getDouble("day"));
			// System.out.println("향후기온 : "+day4tday+"℃");
			// -	- 날씨
			JSONArray day4w = day4.getJSONArray("weather");
			JSONObject day4w0 = day4w.getJSONObject(0);
			String Day4Icon = day4w0.getString("icon");
			// String Day4Main = day4w0.getString("main");
			// System.out.println("향후날씨 : "+Day4Icon+", "+Day4Main);

			WeatherVO weather = service.WeatherImport(loc[i]);

			weather.setWinddegreenow(WindDeg);
			weather.setWindspeednow(WindSpeed);
			weather.setHumidnow(Humid);
			weather.setIconnow(CurrentIcon);
			weather.setTempnow(temp);
			weather.setSunrise(SunRiseTime);
			weather.setSunset(SunSetTime);
			weather.setIcon1(Day1Icon);
			weather.setTemp1(day1tday);

			weather.setIcon2(Day2Icon);
			weather.setTemp2(day2tday);

			weather.setIcon3(Day3Icon);
			weather.setTemp3(day3tday);

			weather.setIcon4(Day4Icon);
			weather.setTemp4(day4tday);
			
			service.WeatherUpdate(weather);
			
			jsonbf.close();
			Thread.sleep(500);
		}

		String dustcom = "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst?" + "&numOfRows=" + nor + "&pageNo=" + pn + "&dataGubun=HOUR&searchCondition=WEEK&returnType=json&serviceKey=" + k.SettingLoad("datagokr").getValue();

		URL urldust10 = new URL(dustcom + "&itemCode=PM10");
		URL urldust25 = new URL(dustcom + "&itemCode=PM25");
		
		String dust10line = "";
		String dust10result = "";
		BufferedReader dust10bf = new BufferedReader(new InputStreamReader(urldust10.openStream()));
		
		String dust25line = "";
		String dust25result = "";
		BufferedReader dust25bf = new BufferedReader(new InputStreamReader(urldust25.openStream()));
		
		dust10line = dust10bf.readLine();
		dust25line = dust25bf.readLine();

		dust10result = dust10result.concat(dust10line);
		dust25result = dust25result.concat(dust25line);
			
		int pm25_g;
		int pm10_g;

		JSONObject dust10Obj = new JSONObject(dust10result);
		JSONObject response10 = dust10Obj.getJSONObject("response");
		JSONObject body10 = response10.getJSONObject("body");
		JSONArray items10 = body10.getJSONArray("items");
		JSONObject local10Object = items10.getJSONObject(0);

		JSONObject dust25Obj = new JSONObject(dust25result);
		JSONObject response25 = dust25Obj.getJSONObject("response");
		JSONObject body25 = response25.getJSONObject("body");
		JSONArray items25 = body25.getJSONArray("items");
		JSONObject local25Object = items25.getJSONObject(0);
		
		for (int i = 0; i < loc.length; i++) {
			String pm10_string = local10Object.getString(loc[i]);
			String pm25_string = local25Object.getString(loc[i]);

			Long pm10_v = (long) Double.parseDouble(pm10_string);
			Long pm25_v = (long) Double.parseDouble(pm25_string);
			
			if (0 < pm10_v && pm10_v <= 30) {
				pm10_g = 1;
			}
			else if (31 <= pm10_v && pm10_v <= 80) {
				pm10_g = 2;
			}
			else if (81 <= pm10_v && pm10_v <= 150) {
				pm10_g = 3;
			}
			else if (151 <= pm10_v) {
				pm10_g = 4;
			}
			else {
				pm10_g = 0;
			} 
			
			if (0 < pm25_v && pm25_v <= 15) {
				pm25_g = 1;
			}
			else if (16 <= pm25_v && pm25_v <= 35) {
				pm25_g = 2;
			}
			else if (36 <= pm25_v && pm25_v <= 75) {
				pm25_g = 3;
			}
			else if (76 <= pm25_v) {
				pm25_g = 4;
			}
			else {
				pm25_g = 0;
			}

			WeatherVO weather = service.WeatherImport(loc[i]);

			weather.setPm10v(pm10_v);
			weather.setPm25v(pm25_v);
			weather.setPm10g(pm10_g);
			weather.setPm25g(pm25_g);

			service.DustUpdate(weather);
		}
		dust10bf.close();
		dust25bf.close();
		System.out.println("날씨DB 업데이트 완료.\n");
	}
}