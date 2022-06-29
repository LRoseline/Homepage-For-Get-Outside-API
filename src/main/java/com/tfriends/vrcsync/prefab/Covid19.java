package com.tfriends.vrcsync.prefab;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import com.tfriends.domain.CovidLocationVO;
import com.tfriends.keys.PrivacyKey;
import com.tfriends.service.CovidService;
import com.tfriends.service.KeyTrigger;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@Component
public class Covid19 extends PrivacyKey{
	
	@Autowired
	private CovidService service;

	@Autowired
	private KeyTrigger k;
	
	private static Image img = null;
	private static Image imgloc = null;

	@Scheduled(cron = "0 50 21 * * *")
	public void AdarasiCovidCount() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, +1);
        String daytime = sdf.format(tomorrow.getTime());

		service.newday(daytime);
	}
	
	@Scheduled(cron = "0 5 * * * *")
	public void Covid19_PG1() throws Exception {
        int width = 450;
        int height = 500;
    	String xml;
    	
    	String Path = "/home/emilia/covid19/";
//    	String Path = "C:/Users/amb17/Pictures/tomcat/covid19/";
    	
		SimpleDateFormat date = new SimpleDateFormat("YYYYMMdd");
		Calendar today = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		
		yesterday.add(Calendar.DATE, -2);
		
		String todayin = date.format(today.getTime());
		String yesterdayin = date.format(yesterday.getTime());
		
		System.out.println(yesterdayin);
		System.out.println(todayin);
		
        String addr = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="+k.Tricker(3)+"&pageNo=1&numOfRows=10&startCreateDt="+yesterdayin+"&endCreateDt="+todayin;
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
				element.getElementsByTagName("gubun"),
				element.getElementsByTagName("overFlowCnt"),
				element.getElementsByTagName("localOccCnt"),
				element.getElementsByTagName("defCnt"),
		        element.getElementsByTagName("isolClearCnt"),
		        element.getElementsByTagName("deathCnt"),
		        element.getElementsByTagName("incDec"),
		        element.getElementsByTagName("stdDay")
			};
//			---------------------------------------------------------------------
			
	        BufferedImage covidbuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = covidbuff.createGraphics();
	        
	        img = ImageIO.read(new File(Path+"backg/covidbg.png"));
			g2d.drawImage(img,0,0,width,height, null);
	        
			// 확진환자
			String ttv = infestinfo[3].item(18).getFirstChild().getNodeValue();
			
		    Graphics2D ttvi = covidbuff.createGraphics();
			ttvi.setColor(Color.WHITE);
			ttvi.setFont(new Font("서울남산 장체 B", Font.BOLD, 48));
		    FontMetrics ttvir = ttvi.getFontMetrics();
		    int ttvix = ((width - ttvir.stringWidth(ttv)-59));
		    
			ttvi.setRenderingHint(
	    			RenderingHints.KEY_TEXT_ANTIALIASING,
	    			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			ttvi.drawString(ttv, 0 + ttvix, 118);
			
			for (int i = 2; i > 0; i--) {
				String tlv = infestinfo[i].item(18).getFirstChild().getNodeValue();
				
			    Graphics2D tlvi = covidbuff.createGraphics();
				tlvi.setColor(Color.WHITE);
				tlvi.setFont(new Font("서울남산 장체 B", Font.BOLD, 32));
			    FontMetrics tlvir = tlvi.getFontMetrics();
			    int tlvix = ((width - tlvir.stringWidth(tlv)-59));
			    
	    		tlvi.setRenderingHint(
	        			RenderingHints.KEY_TEXT_ANTIALIASING,
	        			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    		tlvi.drawString(tlv, 0 + tlvix, 236-(i*38));
	    		
	    		double tlvd = Double.parseDouble(tlv);
	    		
				Graphics2D tlvdia = covidbuff.createGraphics();
	    		tlvdia.setColor(new Color(255, 115, 118));
	    		tlvdia.setFont(new Font("서울남산체 B", Font.BOLD, 32));
	    		
	    		tlvdia.setRenderingHint(
	    				RenderingHints.KEY_TEXT_ANTIALIASING,
	        			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    		
	    		if (tlvd > 0) {
		    		tlvdia.drawString("▲", 401, 238-(i*38));
	    		}
	    		else
	    		{
		    		tlvdia.drawString("-", 411, 234-(i*38));
	    		}
			}
			
			Graphics2D tdvi = covidbuff.createGraphics();
			tdvi.setColor(Color.WHITE);
			tdvi.setFont(new Font("서울남산 장체 B", Font.BOLD, 48));
			FontMetrics tdvir = tdvi.getFontMetrics();
			
			for (int i = 1; i < 2; i++) {
				String index = infestinfo[i+4].item(18).getFirstChild().getNodeValue();
				
			    int tdvix = ((width - tdvir.stringWidth(index)-59));
			    
	    		tdvi.setRenderingHint(
	        			RenderingHints.KEY_TEXT_ANTIALIASING,
	        			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    		tdvi.drawString(index, 0 + tdvix, 333+(i*84));
	    	}
			
			Color [] colours = new Color[3];
			
			colours[0] = new Color(112, 208, 236);
			colours[1] = new Color(193, 155, 223);
			
			for (int i = 1; i < 2; i++) {
	    		double todayiv = Double.parseDouble(infestinfo[i+4].item(18).getFirstChild().getNodeValue());
	    		double yesterdayiv = Double.parseDouble(infestinfo[i+4].item(37).getFirstChild().getNodeValue());
	    		double yivd = todayiv-yesterdayiv;
	    		
	    		long yivdn = Math.round(yivd);
	    		String yivdm = Long.toString(yivdn);
	    		
	    		if (yivdn < 0) {
	    			yivdm = Long.toString(yivdn*-1);
	    		}
	    		
			    Graphics2D yivdi = covidbuff.createGraphics();
				yivdi.setColor(Color.WHITE);
				yivdi.setFont(new Font("서울남산 장체 B", Font.BOLD, 32));
			    FontMetrics yivdir = yivdi.getFontMetrics();
			    int yivdix = ((width - yivdir.stringWidth(yivdm)-59));
			    
	    		yivdi.setRenderingHint(
	        			RenderingHints.KEY_TEXT_ANTIALIASING,
	        			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    		yivdi.drawString(yivdm, 0 + yivdix, 368+(i*84));
	    		
	    		Graphics2D yivdia = covidbuff.createGraphics();
	    		yivdia.setColor(colours[i]);
	    		yivdia.setFont(new Font("서울남산체 B", Font.BOLD, 32));
	    		
	    		yivdia.setRenderingHint(
	        			RenderingHints.KEY_TEXT_ANTIALIASING,
	        			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    		
	    		if (yivd > 0) {
		    		yivdia.drawString("▲", 401, 370+(i*84));
	    		}
	    		else if (yivd == 0)
	    		{
	        		yivdia.drawString("-", 411, 364+(i*84));
	    		}
	    		else
	    		{
	        		yivdia.drawString("▼", 401, 370+(i*84));
	    		}
			}
			
			File file = new File(Path+"covidvrc.png");
			file.delete();
			ImageIO.write(covidbuff, "png", file);
			
			
	        BufferedImage covidbuffloc = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D backgloc = covidbuffloc.createGraphics();
	        imgloc = ImageIO.read(new File(Path+"backg/sidecovid.png"));
    		backgloc.drawImage(imgloc,0,0,width,height, null);
    		
			for (int i = 0; i <= 18; i++) {
				double over = Double.parseDouble(infestinfo[1].item(i).getFirstChild().getNodeValue());
				double local = Double.parseDouble(infestinfo[2].item(i).getFirstChild().getNodeValue());
				String getNode_localIn = (Math.round(over+local))+"";
				String getNode_localNm = infestinfo[0].item(i).getFirstChild().getNodeValue();
				
				CovidLocationVO CovidVO = service.LocGet(getNode_localNm);
				
				CovidVO.setTodayoverflow(infestinfo[1].item(i).getFirstChild().getNodeValue());
				CovidVO.setTodaylocalocc(infestinfo[2].item(i).getFirstChild().getNodeValue());
				CovidVO.setTodaydef(infestinfo[3].item(i).getFirstChild().getNodeValue());
				// CovidVO.setTodayisoclear(infestinfo[4].item(i).getFirstChild().getNodeValue());
				CovidVO.setTodaydeath(infestinfo[5].item(i).getFirstChild().getNodeValue());
				CovidVO.setTodayinc(infestinfo[6].item(i).getFirstChild().getNodeValue());
				
				CovidVO.setYesterdayoverflow(infestinfo[1].item(i+18).getFirstChild().getNodeValue());
				CovidVO.setYesterdaylocalocc(infestinfo[2].item(i+18).getFirstChild().getNodeValue());
				CovidVO.setYesterdaydef(infestinfo[3].item(i+18).getFirstChild().getNodeValue());
				// CovidVO.setYesterdayisoclear(infestinfo[4].item(i+18).getFirstChild().getNodeValue());
				CovidVO.setYesterdaydeath(infestinfo[5].item(i+18).getFirstChild().getNodeValue());
				CovidVO.setYesterdayinc(infestinfo[6].item(i+18).getFirstChild().getNodeValue());
				
				service.CovidUpdate(CovidVO);
				
				System.out.println("\nNum: "+i);
				System.out.println("Local: "+getNode_localNm);
				System.out.println("Infest: "+getNode_localIn);
				
				Graphics2D imglocal = covidbuffloc.createGraphics();
				imglocal.setColor(Color.WHITE);
				imglocal.setFont(new Font("서울남산 장체 B", Font.BOLD, 30));
				FontMetrics imglocalfm = imglocal.getFontMetrics();
				
				int imglocalright;
				String up = "●";
				Color incStat = new Color(103, 177, 255);
				
				if (over+local > 0) {
					up = "▲";
					incStat = new Color(255, 80, 80);
				}
				
				Graphics2D imgInc = covidbuffloc.createGraphics();
				imgInc.setColor(incStat);
				imgInc.setFont(new Font("서울남산체 B", Font.BOLD, 24));
				
				imgInc.setRenderingHint(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				if ((i % 2) == 0) {
					imglocalright = ((width - imglocalfm.stringWidth(getNode_localIn)-45));
					imglocal.setRenderingHint(
							RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					imglocal.drawString(getNode_localIn, 0 + imglocalright, 430+(-18*(i)));
					imgInc.drawString(up, 408, 410+(-18*(i-1)));
				} else {
					imglocalright = ((width - imglocalfm.stringWidth(getNode_localIn)-275));
					imglocal.setRenderingHint(
							RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					imglocal.drawString(getNode_localIn, 0 + imglocalright, 430+(-18*(i-1)));
					imgInc.drawString(up, 180, 410+(-18*(i-2)));
				}
			}
			
    		File file2 = new File(Path + "covidvrc1.png");
    		file2.delete();
    		ImageIO.write(covidbuffloc, "png", file2);
        }
    }
}