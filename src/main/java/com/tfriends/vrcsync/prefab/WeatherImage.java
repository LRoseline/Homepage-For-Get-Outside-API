package com.tfriends.vrcsync.prefab;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
public class WeatherImage extends PrivacyKey{
	private static Image img = null;
	private static Image dayimg = null;
	private static Image dayimg2 = null;
	private static Image dayimg3 = null;
	private static Image dayimg4 = null;
	double UTC = 9;
	
	@Autowired
	private WeatherService service;
	
	@Scheduled(cron = "25 0 0,8-22 * * *")
	public void CronTry() throws Exception {
		String LinuxPath = "D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/mainstream/vrcpref/WEB-INF/classes/static/imgs/weathericons/";
		String LinuxSave = "D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/resources/mambtv/weather/";
	
		SimpleDateFormat date = new SimpleDateFormat("MM월 dd일");
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		Calendar d3 = Calendar.getInstance();
		Calendar d4 = Calendar.getInstance();
		
		d1.add(Calendar.DATE, +1);
		d2.add(Calendar.DATE, +2);
		d3.add(Calendar.DATE, +3);
		d4.add(Calendar.DATE, +4);
		
		String dat1str = date.format(d1.getTime());
		String dat2str = date.format(d2.getTime());
		String dat3str = date.format(d3.getTime());
		String dat4str = date.format(d4.getTime());
		
		for (int i = 0; i < loc.length; i++) {
			WeatherVO tester = service.WeatherImport(loc[i]);
			
			String locale = tester.getLname();
			String icon = tester.getIconnow();
			double temp = tester.getTempnow();
			double pm10gv = tester.getPm10g();
			double pm25gv = tester.getPm25g();
			
			String pm10g;
			String pm25g;
			
			String icon_1 = tester.getIcon1();
			String icon_2 = tester.getIcon2();
			String icon_3 = tester.getIcon3();
			String icon_4 = tester.getIcon4();
				
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

			// Common Area ----------------------------
			String pm10txt = "미세먼지";
			String pm25txt = "초미세먼지";
			
			// Image Area -------------------------------
			int width = 300;
			int height = 500;
			
			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			String tempd = Math.round(temp) + "˚C";
			
			// 날씨
			
			Graphics2D g2d = buffImg.createGraphics();
			g2d.setColor(Color.black);
			g2d.setFont(new Font("맑은 고딕", Font.BOLD, 40));
			FontMetrics fm = g2d.getFontMetrics();
			int xt = ((300 - fm.stringWidth(locale)) / 2);
			// img = ImageIO.read(new File(WindowsPath+icon+".png"));
			img = ImageIO.read(new File(LinuxPath+icon+".png"));
			
			
			Graphics2D g2dd = buffImg.createGraphics();
			g2dd.setColor(Color.black);
			g2dd.setFont(new Font("DX핫플레이스", Font.PLAIN, 48));
			FontMetrics fmm = g2dd.getFontMetrics();
			int x = ((300 - fmm.stringWidth(tempd)) / 2);
			
			// 이미지 출력(날씨)
			
			g2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString(locale, xt, 40);
			
			g2d.drawImage(img,60,113,180,180, null);
			
			g2dd.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2dd.drawString(tempd, x, 380);
			
			// Image Area -------------------------------
			
			// 미세먼지
			
			Graphics2D dust10 = buffImg.createGraphics();
			dust10.setColor(Color.black);
			dust10.setFont(new Font("맑은 고딕", Font.BOLD, 26));
			FontMetrics dustfm10 = dust10.getFontMetrics();
			int dust10x = ((150 - dustfm10.stringWidth(pm10txt)) / 2);
			
			Graphics2D dust25 = buffImg.createGraphics();
			dust25.setColor(Color.black);
			dust25.setFont(new Font("맑은 고딕", Font.BOLD, 26));
			FontMetrics dustfm25 = dust25.getFontMetrics();
			int dust25x = ((150 - dustfm25.stringWidth(pm25txt)) / 2);
			
			Graphics2D dust10b = buffImg.createGraphics();
			dust10b.setColor(Color.black);
			dust10b.setFont(new Font("맑은 고딕", Font.BOLD, 26));
			FontMetrics dustfm10a = dust10b.getFontMetrics();
			int dust10xt = ((150 - dustfm10a.stringWidth(pm10g)) / 2);
			
			Graphics2D dust25b = buffImg.createGraphics();
			dust25b.setColor(Color.black);
			dust25b.setFont(new Font("맑은 고딕", Font.BOLD, 26));
			FontMetrics dustfm25a = dust25b.getFontMetrics();
			int dust25xt = ((150 - dustfm25a.stringWidth(pm25g)) / 2);
			
			// 출처
			Graphics2D newfrom = buffImg.createGraphics();
			newfrom.setColor(Color.black);
			newfrom.setFont(new Font("2002L KOG", Font.PLAIN,18));
			
			//이미지 출력 (미세먼지)
			
			dust10.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dust10.drawString(pm10txt, 0 + dust10x, 430);
			
			dust25.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dust25.drawString(pm25txt, 150 + dust25x, 430);
			
			dust10.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dust10.drawString(pm10g, 0 + dust10xt, 456);
			
			dust25.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dust25.drawString(pm25g, 150 + dust25xt, 456);
			
			newfrom.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			newfrom.drawString("출처: 한국환경공단", 164, 494);
			
			g2d.dispose();
			g2dd.dispose();
			dust10.dispose();
			dust10b.dispose();
			dust25.dispose();
			dust25.dispose();
			newfrom.dispose();
			
			// File Load
			File file = new File(LinuxSave+loc[i]+".png");
			file.delete();
			ImageIO.write(buffImg, "png", file);
			
			// Forecast
			BufferedImage daybuffimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			String naming [] = {"내일","모레","글피","그글피"};
			
			Graphics2D d0local = daybuffimg.createGraphics();
			d0local.setColor(Color.black);
			d0local.setFont(new Font("맑은 고딕", Font.BOLD, 40));
			FontMetrics fm0 = d0local.getFontMetrics();
			int xt0 = ((300 - fm0.stringWidth(locale)) / 2);
			
			// Day 1
			String tempd1 = Math.round(tester.getTemp1()) + "˚C";
			
			Graphics2D naming1 = daybuffimg.createGraphics();
			naming1.setColor(Color.BLACK);
			naming1.setFont(new Font("맑은 고딕", Font.BOLD,36));
			FontMetrics naming1centre = naming1.getFontMetrics();
			int naming1x = ((150 - naming1centre.stringWidth(naming[0])) / 2);
			
			Graphics2D dat1 = daybuffimg.createGraphics();
			dat1.setColor(Color.BLACK);
			dat1.setFont(new Font("맑은 고딕", Font.BOLD,22));
			FontMetrics dat1centre = dat1.getFontMetrics();
			int dat1x = ((150 - dat1centre.stringWidth(dat1str)) / 2);
	
			Graphics2D d1icon = daybuffimg.createGraphics();
			
//	    	dayimg = ImageIO.read(new File(WindowsPath+icon_1+".png"));
			dayimg = ImageIO.read(new File(LinuxPath+icon_1+".png"));
			
			Graphics2D d1temp = daybuffimg.createGraphics();
			d1temp.setColor(Color.BLACK);
			d1temp.setFont(new Font("맑은 고딕", Font.BOLD,30));
			FontMetrics d1tempcentre = d1temp.getFontMetrics();
			int d1tempx = ((300 - d1tempcentre.stringWidth(tempd1)) / 2);
			
			// Day 2
			String tempd2 = Math.round(tester.getTemp2()) + "˚C";
			
			Graphics2D naming2 = daybuffimg.createGraphics();
			naming2.setColor(Color.BLACK);
			naming2.setFont(new Font("맑은 고딕", Font.BOLD,36));
			FontMetrics naming2centre = naming2.getFontMetrics();
			int naming2x = ((150 - naming2centre.stringWidth(naming[1])) / 2);
			
			Graphics2D dat2 = daybuffimg.createGraphics();
			dat2.setColor(Color.BLACK);
			dat2.setFont(new Font("맑은 고딕", Font.BOLD,22));
			FontMetrics dat2centre = dat2.getFontMetrics();
			int dat2x = ((150 - dat2centre.stringWidth(dat2str)) / 2);
	
			Graphics2D d2icon = daybuffimg.createGraphics();
// 	    	dayimg2 = ImageIO.read(new File(WindowsPath+icon_2+".png"));
			dayimg2 = ImageIO.read(new File(LinuxPath+icon_2+".png"));
			
			Graphics2D d2temp = daybuffimg.createGraphics();
			d2temp.setColor(Color.BLACK);
			d2temp.setFont(new Font("맑은 고딕", Font.BOLD,30));
			FontMetrics d2tempcentre = d2temp.getFontMetrics();
			int d2tempx = ((300 - d2tempcentre.stringWidth(tempd2)) / 2);
			
			// Day 3
			String tempd3 = Math.round(Math.round(tester.getTemp3())) + "˚C";
			
			Graphics2D naming3 = daybuffimg.createGraphics();
			naming3.setColor(Color.BLACK);
			naming3.setFont(new Font("맑은 고딕", Font.BOLD,36));
			FontMetrics naming3centre = naming3.getFontMetrics();
			int naming3x = ((150 - naming3centre.stringWidth(naming[2])) / 2);
			
			Graphics2D dat3 = daybuffimg.createGraphics();
			dat3.setColor(Color.BLACK);
			dat3.setFont(new Font("맑은 고딕", Font.BOLD,22));
			FontMetrics dat3centre = dat3.getFontMetrics();
			int dat3x = ((150 - dat3centre.stringWidth(dat3str)) / 2);
	
			Graphics2D d3icon = daybuffimg.createGraphics();
// 	    	dayimg3 = ImageIO.read(new File(WindowsPath+icon_3+".png"));
			dayimg3 = ImageIO.read(new File(LinuxPath+icon_3+".png"));
			
			Graphics2D d3temp = daybuffimg.createGraphics();
			d3temp.setColor(Color.BLACK);
			d3temp.setFont(new Font("맑은 고딕", Font.BOLD,30));
			FontMetrics d3tempcentre = d3temp.getFontMetrics();
			int d3tempx = ((300 - d3tempcentre.stringWidth(tempd3)) / 2);
			
			// Day 4
			String tempd4 = Math.round(Math.round(tester.getTemp4())) + "˚C";
			
			Graphics2D naming4 = daybuffimg.createGraphics();
			naming4.setColor(Color.BLACK);
			naming4.setFont(new Font("맑은 고딕", Font.BOLD,36));
			FontMetrics naming4centre = naming4.getFontMetrics();
			int naming4x = ((150 - naming4centre.stringWidth(naming[3])) / 2);
			
			Graphics2D dat4 = daybuffimg.createGraphics();
			dat4.setColor(Color.BLACK);
			dat4.setFont(new Font("맑은 고딕", Font.BOLD,22));
			FontMetrics dat4centre = dat4.getFontMetrics();
			int dat4x = ((150 - dat4centre.stringWidth(dat4str)) / 2);
	
			Graphics2D d4icon = daybuffimg.createGraphics();
// 	    	dayimg4 = ImageIO.read(new File(WindowsPath+icon_4+".png"));
			dayimg4 = ImageIO.read(new File(LinuxPath+icon_4+".png"));
			
			Graphics2D d4temp = daybuffimg.createGraphics();
			d4temp.setColor(Color.BLACK);
			d4temp.setFont(new Font("맑은 고딕", Font.BOLD,30));
			FontMetrics d4tempcentre = d4temp.getFontMetrics();
			int d4tempx = ((300 - d4tempcentre.stringWidth(tempd4)) / 2);
			
			// 이미지 출력(날씨)

			d0local.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			d0local.drawString(locale, xt0, 40);
			
			// Day 1
			
			naming1.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			naming1.drawString(naming[0], naming1x, 113);
			
			dat1.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dat1.drawString(dat1str, dat1x, 158);

			d1icon.drawImage(dayimg,188,61,74,74, null);

			d1temp.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			d1temp.drawString(tempd1, d1tempx+75, 160);
			
			// Day 2
			
			naming2.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			naming2.drawString(naming[1], naming2x, 113+(104*1));
			
			dat2.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dat2.drawString(dat2str, dat2x, 158+(104*1));

			d2icon.drawImage(dayimg2,188,61+(104*1),74,74, null);

			d2temp.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			d2temp.drawString(tempd2, d2tempx+75, 160+(104*1));
			
			// Day 3
			
			naming3.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			naming3.drawString(naming[2], naming3x, 113+(104*2));
			
			dat3.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dat3.drawString(dat3str, dat3x, 158+(104*2));

			d3icon.drawImage(dayimg3,188,61+(104*2),74,74, null);

			d3temp.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			d3temp.drawString(tempd3, d3tempx+75, 160+(104*2));
			
			// Day 4
			
			naming4.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			naming4.drawString(naming[3], naming4x, 113+(104*3));
			
			dat4.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			dat4.drawString(dat4str, dat4x, 158+(104*3));

			d4icon.drawImage(dayimg4,188,61+(104*3),74,74, null);

			d4temp.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			d4temp.drawString(tempd4, d4tempx+75, 160+(104*3));
			
			// Save level
			d0local.dispose();
			
			naming1.dispose();
			dat1.dispose();
			d1icon.dispose();
			d1temp.dispose();
			
			naming2.dispose();
			dat2.dispose();
			d2icon.dispose();
			d2temp.dispose();
			
			naming3.dispose();
			dat3.dispose();
			d3icon.dispose();
			d3temp.dispose();
			
			naming4.dispose();
			dat4.dispose();
			d4icon.dispose();
			d4temp.dispose();

			
//			File file1 = new File(WindowsSave+loc[i]+"_forecast.png");
			File file1 = new File(LinuxSave+loc[i]+"_forecast.png");
			file1.delete();
			ImageIO.write(daybuffimg, "png", file1);
			}
		System.out.println("AMBTV 날씨 출력끝");
	}
}