package com.tfriends.vrcsync.prefab;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tfriends.domain.WeatherVO;
import com.tfriends.keys.PrivacyKey;
import com.tfriends.service.WeatherService;

@Component
public class WeatherOnlyImg extends PrivacyKey {
	private static Image imgbg = null;
	private static Image imgico = null;
	private static Image imgico_forecast = null;
	private static Image imgico_wind = null;
	
	@Autowired
	private WeatherService service;
	
	@Scheduled(cron = "6 2 0,8-22 * * *")
	public void WeatherLite() throws Exception {
		int width = 500;
		int height = 400;

		String LinuxPath = "D:/Network/Program Files/Apache Software Foundation/Tomcat 9.0/mainstream/vrcpref/WEB-INF/classes/static/imgs/weathericons/";
		String LinuxSave = "D:/Network/Program Files/Apache Software Foundation/Tomcat 9.0/resources/Weather Lite/";
		
		for (int i = 0; i < loc.length; i++) {
			WeatherVO tester = service.WeatherImport(loc[i]);
			
			String locale = tester.getLname();
			String icon = tester.getIconnow();
			double temp = tester.getTempnow();
			double pm10vv = tester.getPm10v();
			double pm10gv = tester.getPm10g();
			double pm25vv = tester.getPm25v();
			double pm25gv = tester.getPm25g();
			
			String pm10g;
			String pm25g;
			
			String [] foreicon = {
				tester.getIcon1(),
				tester.getIcon2(),
				tester.getIcon3(),
				tester.getIcon4()
			};
			
			double [] foretemp = {
				tester.getTemp1(),
				tester.getTemp2(),
				tester.getTemp3(),
				tester.getTemp4()
			};
			
			if (pm10gv == 1)
			{
				pm10g = "좋음";
			}
			else if (pm10gv == 2)
			{
				pm10g = "보통";
			}
			else if (pm10gv == 3)
			{
				pm10g = "나쁨";
			}
			else if (pm10gv == 4)
			{
				pm10g = "매우나쁨";
			}
			else
			{
				pm10g = "-";
			}
			
			// 초미세먼지
			
			if (pm25gv == 1)
			{
				pm25g = "좋음";
			}
			else if (pm25gv == 2)
			{
				pm25g = "보통";
			}
			else if (pm25gv == 3)
			{
				pm25g = "나쁨";
			}
			else if (pm25gv == 4)
			{
				pm25g = "매우나쁨";
			}
			else
			{
				pm25g = "-";
			}
			
//			준비
			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			String tempd = Math.round(temp) + "℃";
			
//			배경
			imgbg = ImageIO.read(new File(LinuxPath+"weather.png"));
			Graphics2D g2d_bg = buffImg.createGraphics();
			g2d_bg.drawImage(imgbg,0,0,width,height, null);
			
//			지역이름
			Graphics2D g2d_local = buffImg.createGraphics();
			g2d_local.setColor(Color.black);
			g2d_local.setFont(new Font("서울남산체 EB", Font.BOLD, 30));
			FontMetrics fm_local = g2d_local.getFontMetrics();
			int x_local = ((92 - fm_local.stringWidth(locale)) / 2);
			
			g2d_local.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d_local.drawString(locale, x_local, 34);
			g2d_local.dispose();
			
//			오늘의 날씨 (아이콘)
			Graphics2D g2d_icon_today = buffImg.createGraphics();
			imgico = ImageIO.read(new File(LinuxPath+icon+".png"));
			g2d_icon_today.drawImage(imgico,7,60,142,142, null);
			g2d_icon_today.dispose();
			
//			오늘의 날씨 (기온)
			Graphics2D g2d_temp_today = buffImg.createGraphics();
			g2d_temp_today.setColor(Color.black);
			g2d_temp_today.setFont(new Font("서울남산체 EB", Font.BOLD, 52));
			FontMetrics fm_temp_today = g2d_temp_today.getFontMetrics();
			int x_temp_today = ((158 - fm_temp_today.stringWidth(tempd)) / 2);
			
			g2d_temp_today.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d_temp_today.drawString(tempd, x_temp_today, 264);
			g2d_temp_today.dispose();

//			날씨예보 (반복문) 200+(i*86),68
//			기간
			Graphics2D g2d_forecast_date = buffImg.createGraphics();
			g2d_forecast_date.setColor(Color.BLACK);
			g2d_forecast_date.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_forecast_date = g2d_forecast_date.getFontMetrics();
			
			SimpleDateFormat date = new SimpleDateFormat("MM.dd");
			
			Calendar d1 = Calendar.getInstance();
			Calendar d2 = Calendar.getInstance();
			Calendar d3 = Calendar.getInstance();
			Calendar d4 = Calendar.getInstance();
			
			d1.add(Calendar.DATE, +1);
			d2.add(Calendar.DATE, +2);
			d3.add(Calendar.DATE, +3);
			d4.add(Calendar.DATE, +4);
			
		    String [] datstr = {
		    		date.format(d1.getTime()),
		    		date.format(d2.getTime()),
		    		date.format(d3.getTime()),
		    		date.format(d4.getTime()),
		    };
			
//			기온
			Graphics2D g2d_forecast = buffImg.createGraphics();
			g2d_forecast.setColor(Color.BLACK);
			g2d_forecast.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_forecast = g2d_forecast.getFontMetrics();
			
			for (int j = 0; j < 4; j++) {
//				기간
				String forecast_date_string = datstr[j];
				int x_forecast_date = (((400+(j*172)) - fm_forecast_date.stringWidth(forecast_date_string)) / 2);
				
				g2d_forecast_date.setRenderingHint(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d_forecast_date.drawString(forecast_date_string, x_forecast_date, 78);
				
//				아이콘
				Graphics2D g2d_icon_forecast = buffImg.createGraphics();
				imgico_forecast = ImageIO.read(new File(LinuxPath+foreicon[j]+".png"));
				g2d_icon_forecast.drawImage(imgico_forecast,164+(j*86),87,71,71, null);
				g2d_icon_forecast.dispose();
				
//				기온
				String forecast_temp = Math.round(foretemp[j])+"℃";
				int x_forecast = (((400+(j*172)) - fm_forecast.stringWidth(forecast_temp)) / 2);
				
				g2d_forecast.setRenderingHint(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d_forecast.drawString(forecast_temp, x_forecast, 190);
			}
			g2d_forecast_date.dispose();
			g2d_forecast.dispose();
			
//			초미세먼지
			Graphics2D g2d_pm25 = buffImg.createGraphics();
			g2d_pm25.setColor(Color.BLACK);
			g2d_pm25.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_pm25 = g2d_pm25.getFontMetrics();
			
			String pm25total = Math.round(pm25vv)+"("+pm25g+")";
			int x_pm25 = (((234*2) - fm_pm25.stringWidth(pm25total))/2);
			
			g2d_pm25.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d_pm25.drawString(pm25total, x_pm25, 272);
			g2d_pm25.dispose();
			
//			미세먼지
			Graphics2D g2d_pm10 = buffImg.createGraphics();
			g2d_pm10.setColor(Color.BLACK);
			g2d_pm10.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_pm10 = g2d_pm10.getFontMetrics();
			
			String pm10total = Math.round(pm10vv)+"("+pm10g+")";
			int x_pm10 = (((352*2) - fm_pm10.stringWidth(pm10total))/2);
			
			g2d_pm10.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d_pm10.drawString(pm10total, x_pm10, 272);
			g2d_pm10.dispose();
			
//			습도
			Graphics2D g2d_humid = buffImg.createGraphics();
			g2d_humid.setColor(Color.BLACK);
			g2d_humid.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_humid = g2d_humid.getFontMetrics();
			
			String Humid = Math.round(tester.getHumidnow())+"";
			int x_humid = (((440*2) - fm_humid.stringWidth(Humid))/2);
			
			g2d_humid.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d_humid.drawString(Humid, x_humid, 272);
			g2d_humid.dispose();
			
//			일출
			Graphics2D g2d_sun = buffImg.createGraphics();
			g2d_sun.setColor(Color.BLACK);
			g2d_sun.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_sun = g2d_sun.getFontMetrics();
			
			g2d_sun.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			String [] sun = {
					tester.getSunrise(),
					tester.getSunset()
					};
			
			for (int j = 0; j < 2; j++) {
				int x_sun = ((((232+(j*128))*2) - fm_sun.stringWidth(sun[j]))/2);
				g2d_sun.drawString(sun[j], x_sun, 383);
			}
			g2d_sun.dispose();
			
//			바람 47,287
			imgico_wind = ImageIO.read(new File(LinuxPath+"wind"+".png"));
			
			AffineTransform at = AffineTransform.getTranslateInstance(47,287);
			at.rotate(tester.getWinddegreenow()-0.5, imgico_wind.getWidth(null)/2, imgico_wind.getHeight(null)/2);
			
			Graphics2D g2d_wind_degree = buffImg.createGraphics();
			
			g2d_wind_degree.drawImage(imgico_wind, at, null);
			g2d_wind_degree.dispose();
			
			Graphics2D g2d_wind_speed = buffImg.createGraphics();
			g2d_wind_speed.setColor(Color.BLACK);
			g2d_wind_speed.setFont(new Font("서울남산체 EB", Font.BOLD, 24));
			FontMetrics fm_wind_speed = g2d_wind_speed.getFontMetrics();
			
			String wind = tester.getWindspeednow()+"m/s";
			
			g2d_wind_speed.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int x_wind_speed = (77 - fm_wind_speed.stringWidth(wind)/2);
			g2d_wind_speed.drawString(wind, x_wind_speed, 383);
			
//			저장
			File file = new File(LinuxSave+loc[i]+".png");
			file.delete();
			ImageIO.write(buffImg, "png", file);
			
			Thread.sleep(500);
		}
	}
}