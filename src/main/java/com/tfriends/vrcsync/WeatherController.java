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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {
	String WindowsPath = "D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/resources/Weather Lite/";
	
	@ResponseBody
	@GetMapping("/weatherlite")
	public ResponseEntity<byte[]> ambtvImageNow(String local) throws IOException {
		
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
}
