package com.tfriends.vrcsync.prefab;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tfriends.keys.PrivacyKey;
import com.tfriends.service.SettingService;

@Component
public class Finance extends PrivacyKey{

	@Autowired
	private SettingService setting;
	
	private static Image img = null;

	@Scheduled(cron = "0 2 10 * * MON-FRI")
	public void FinanceLoad() throws Exception {
		// String Path = "/home/emilia/mambtv/finance";
		String Path = "C:/Users/amb17/Pictures/tomcat/mambtv/finance";
		Date kyou = new Date();
		SimpleDateFormat TimeStamp = new SimpleDateFormat("YYYY"+"MM"+"dd");
		
		int [] countryselect = {22,12,8};
		String Today = TimeStamp.format(kyou);

		System.out.println(Today);

		String urlload = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+setting.SettingLoad("kxim").getValue()+"&searchdate="+Today+"&data=AP01";
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
}
