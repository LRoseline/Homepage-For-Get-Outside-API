package com.tfriends.vrcsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/mambtv/*")
@Controller
public class ImageController {
	String WindowsPath = "D:/Network/Program Files/Apache Software Foundation/Tomcat 9.0/resources/mambtv/weather/";
	
	@ResponseBody
	@GetMapping("/weather_now")
	public ResponseEntity<byte[]> ambtvImageNow(String local) throws Exception {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		try {
			return new ResponseEntity<byte[]>(
					IOUtils.toByteArray(new FileInputStream(
							new File(WindowsPath+local+".png"))),
					header, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(
					IOUtils.toByteArray(new FileInputStream(
							new File(WindowsPath+"daejeon"+".png"))),
					header, HttpStatus.CREATED);
		}
	}
		
	@ResponseBody
	@GetMapping("/weather_forecast")
	public ResponseEntity<byte[]> ambtvImageForecast(String local) throws IOException {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);

		try {
			return new ResponseEntity<byte[]>(
					IOUtils.toByteArray(new FileInputStream(
							new File(WindowsPath+local+"_forecast.png"))),
					header, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(
					IOUtils.toByteArray(new FileInputStream(
							new File(WindowsPath+"daejeon"+"_forecast.png"))),
					header, HttpStatus.CREATED);
		}
	}
}