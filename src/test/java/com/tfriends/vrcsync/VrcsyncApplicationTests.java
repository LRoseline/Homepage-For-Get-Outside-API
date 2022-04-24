package com.tfriends.vrcsync;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.tfriends.keys.PrivacyKey;
import com.tfriends.service.CovidService;
import com.tfriends.service.KeyTrigger;

import org.json.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

@WebAppConfiguration
@SpringBootTest
public class VrcsyncApplicationTests extends PrivacyKey{

	private int nor = 1;
	private int pn = 1;

	@Autowired
	public KeyTrigger kv;

	@Test
	public void newWeatherTest () throws Exception {

		int i = 6;

		SimpleDateFormat ForeTimeStamp = new SimpleDateFormat("HH:mm", Locale.KOREA);
		SimpleDateFormat ForeTimeStampAPM = new SimpleDateFormat("aa hh:mm", Locale.KOREA);
		
		// Start Area in for()
		String urlcom = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat[i] + "&lon=" + lon[i] + 
		"&appid=" + kv.Tricker(1) + "&cnt=1&units=metric";
		
		URL urlweather = new URL(urlcom);
		String jsonline = "";
		String jsonresult = "";
		
		BufferedReader jsonbf = new BufferedReader(new InputStreamReader(urlweather.openStream()));
		
		jsonline = jsonbf.readLine();
		jsonresult = jsonresult.concat(jsonline);

		JSONObject jObject = new JSONObject(jsonresult);

		// current 그룹
		JSONObject CurrentResult = jObject.getJSONObject("current");
		System.out.println("현재 날씨");

		// - 일출시간 객체
		Long UnixTimeRise = CurrentResult.getLong("sunrise");
		Long UnixConvRise = Long.parseLong((UnixTimeRise)+"000");
		String SunRiseTime = ForeTimeStamp.format(UnixConvRise);
		System.out.println("일출시간 : "+SunRiseTime);

		// - 일몰시간 객체
		Long UnixTimeSet = CurrentResult.getLong("sunset");
		Long UnixConvSet = Long.parseLong((UnixTimeSet)+"000");
		String SunSetTime = ForeTimeStamp.format(UnixConvSet);
		System.out.println("일몰시간 : "+SunSetTime);

		// - 현재기온 객체
		Long temp = Math.round(CurrentResult.getDouble("temp"));
		System.out.println("현재기온 : "+temp+"℃");

		// - 현재습도 객체
		Long Humid = CurrentResult.getLong("humidity");
		System.out.println("현재습도 : "+ Humid);

		// - 바람방향 객체
		Long WindDeg = CurrentResult.getLong("wind_deg");
		System.out.println("바람방향 : "+WindDeg);
		
		// - 바람속도 객체
		Long WindSpeed = CurrentResult.getLong("wind_speed");
		System.out.println("바람속도 : "+WindSpeed);

		// - 업뎃날짜 객체
		
		Long UnixTimeLatest = CurrentResult.getLong("dt");
		Long UnixConvLatest = Long.parseLong((UnixTimeLatest)+"000");
		System.out.println("업뎃날짜 : "+ForeTimeStampAPM.format(UnixConvLatest));
		

		// - weather 그룹
		JSONArray WeatherArrayResult = CurrentResult.getJSONArray("weather");
		JSONObject WeatherArray0 = WeatherArrayResult.getJSONObject(0);

		// -	- 현재날씨 객체
		String CurrentIcon = WeatherArray0.getString("icon");
		String CurrentMain = WeatherArray0.getString("main");
		System.out.println("현재날씨 : "+CurrentIcon+", "+CurrentMain);

		// daily 그룹
		JSONArray Dailyresult = jObject.getJSONArray("daily");

		// - 1일차
		JSONObject day1 = Dailyresult.getJSONObject(1);
		System.out.println("/n1일차 날씨");
		// -	- 기온
		JSONObject day1t = day1.getJSONObject("temp");
		Long day1tday = Math.round(day1t.getDouble("day"));
		System.out.println("향후기온 : "+day1tday+"℃");
		// -	- 날씨
		JSONArray day1w = day1.getJSONArray("weather");
		JSONObject day1w0 = day1w.getJSONObject(0);
		String Day1Icon = day1w0.getString("icon");
		String Day1Main = day1w0.getString("main");
		System.out.println("향후날씨 : "+Day1Icon+", "+Day1Main);

		// - 2일차
		JSONObject day2 = Dailyresult.getJSONObject(2);
		System.out.println("/n2일차 날씨");
		// -	- 기온
		JSONObject day2t = day2.getJSONObject("temp");
		Long day2tday = Math.round(day2t.getDouble("day"));
		System.out.println("향후기온 : "+day2tday+"℃");
		// -	- 날씨
		JSONArray day2w = day2.getJSONArray("weather");
		JSONObject day2w0 = day2w.getJSONObject(0);
		String Day2Icon = day2w0.getString("icon");
		String Day2Main = day2w0.getString("main");
		System.out.println("향후날씨 : "+Day2Icon+", "+Day2Main);

		// - 3일차
		JSONObject day3 = Dailyresult.getJSONObject(3);
		System.out.println("/n3일차 날씨");
		// -	- 기온
		JSONObject day3t = day3.getJSONObject("temp");
		Long day3tday = Math.round(day3t.getDouble("day"));
		System.out.println("향후기온 : "+day3tday+"℃");
		// -	- 날씨
		JSONArray day3w = day3.getJSONArray("weather");
		JSONObject day3w0 = day3w.getJSONObject(0);
		String Day3Icon = day3w0.getString("icon");
		String Day3Main = day3w0.getString("main");
		System.out.println("향후날씨 : "+Day3Icon+", "+Day3Main);

		// - 4일차
		JSONObject day4 = Dailyresult.getJSONObject(4);
		System.out.println("/n4일차 날씨");
		// -	- 기온
		JSONObject day4t = day4.getJSONObject("temp");
		Long day4tday = Math.round(day4t.getDouble("day"));
		System.out.println("향후기온 : "+day4tday+"℃");
		// -	- 날씨
		JSONArray day4w = day4.getJSONArray("weather");
		JSONObject day4w0 = day4w.getJSONObject(0);
		String Day4Icon = day4w0.getString("icon");
		String Day4Main = day4w0.getString("main");
		System.out.println("향후날씨 : "+Day4Icon+", "+Day4Main);
	}

	@Test
	public void newDustTest () throws Exception {
		
		String dustcom = "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst?" + "&numOfRows=" + nor + "&pageNo=" + pn + "&dataGubun=HOUR&searchCondition=WEEK&returnType=json&serviceKey=" + kv.Tricker(3);

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

			System.out.println(lockr[i]+" 일미세먼지 : ("+pm10_v+", "+pm10_g+")");
			System.out.println(lockr[i]+" 초미세먼지 : ("+pm25_v+", "+pm25_g+")");
		}
	}

	private static Image img = null;

	@Test
	public void newExchangeTest () throws Exception {

		String Path = "/home/emilia/mambtv/finance";
		// Path = "C:/Users/amb17/Pictures/tomcat/mambtv/finance";
		Date kyou = new Date();
		SimpleDateFormat TimeStamp = new SimpleDateFormat("YYYY"+"MM"+"dd");
		
		int [] countryselect = {22,12,8};
		String Today = TimeStamp.format(kyou);

		System.out.println(Today);

		String urlload = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+kv.Tricker(4)+"&searchdate="+"20220118"+"&data=AP01";
		URL FinanceFree = new URL(urlload);
		BufferedReader reader;
		String line;
		String result="";
		
		reader = new BufferedReader(new InputStreamReader(FinanceFree.openStream()));

		while((line=reader.readLine())!=null) {
			result=result.concat(line);
		}

		JSONArray array = new JSONArray(result);

		int width = 300;
		int height = 300;
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img = ImageIO.read(new File(Path+"/backg/finance.png"));

		Graphics2D g2d = buffImg.createGraphics();
		g2d.setColor(Color.black);
		g2d.setFont(new Font("2002lkog", Font.PLAIN, 40));

		for (int i = 0; i < countryselect.length; i++) {
			JSONObject obj = array.getJSONObject(countryselect[i]);
			System.out.println(i+": "+obj.get("cur_nm"));
			
			String how = (String) obj.get("deal_bas_r");
			
			g2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString(how, 125, 115+(i*50));
		}
		
		g2d.drawImage(img,0,0,300,300, null);
		g2d.dispose();
		
		// Save level
		
		File file = new File(Path+"/financevrc.png");
		
		file.delete();
		ImageIO.write(buffImg, "png", file);
        reader.close();
	}

	@Autowired
	private CovidService cservice;

	@Test
	public void newCounter () {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        String daytime = sdf.format(date);

		cservice.newday(daytime);
	}

	@Test
	public void covidTest() throws Exception {

		String xml;

		SimpleDateFormat date = new SimpleDateFormat("YYYYMMdd");
		Calendar today = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		
		yesterday.add(Calendar.DATE, -2);
		
		String todayin = date.format(today.getTime());
		String yesterdayin = date.format(yesterday.getTime());
		
		System.out.println(yesterdayin);
		System.out.println(todayin);
		
        String addr = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="+kv.Tricker(3)+"&pageNo=1&numOfRows=10&startCreateDt="+yesterdayin+"&endCreateDt="+todayin;
        URL url = new URL(addr);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
//        http.setConnectTimeout(10000);
        http.setUseCaches(false);

        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            sb.append(line);
        }
        xml = sb.toString();
        br.close();
        http.disconnect();
        
        if (xml != null) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory
	                .newInstance();
	        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
	
	        InputStream is =new ByteArrayInputStream(xml.getBytes());
	        Document doc = documentBuilder.parse(is);
	        Element element = doc.getDocumentElement();
			
			NodeList [] infestinfo = {
				element.getElementsByTagName("gubun"), // 있음 			0
				element.getElementsByTagName("overFlowCnt"), // 있음	1
				element.getElementsByTagName("localOccCnt"), // 있음	2
				element.getElementsByTagName("defCnt"), // 있음			3
		        element.getElementsByTagName("isolClearCnt"), // 없음	4
		        element.getElementsByTagName("deathCnt"), // 있음		5
		        element.getElementsByTagName("incDec"), // 있음			6
		        element.getElementsByTagName("stdDay") // 있음			7
			};

			String index = infestinfo[5].item(18).getFirstChild().getNodeValue();
			System.out.println(index);
		}
	}

	@Test
	public void KeyTxTest() throws Exception {
		File a = new File("C:/Users/amb17/Documents/VSCode/vrcsync/src/main/resources/keys.txt");
		FileReader reader = new FileReader(a);
		BufferedReader br = new BufferedReader(reader);

		String WYF = "\n";

		String str="", l="";

		while((l=br.readLine())!=null) {
			str += l+WYF;
		}

		br.close();
		reader.close();

		String[] array = str.trim().split(WYF);

		String [] codesplit = array[0].trim().split(" : ");
		System.out.println(codesplit[1]);
	}

	@Test
	public void TrickonPack () throws Exception {
		KeyTrigger t = new KeyTrigger();

		t.Tricker(0);
	}
}
