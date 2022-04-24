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
	String WindowsPath = "C:/Users/amb17/Pictures/tomcat/Weather Lite/";
	String LinuxPath = "/home/emilia/Weather Lite/";
	
	@ResponseBody
	@GetMapping("/weatherlite")
	public ResponseEntity<byte[]> ambtvImageNow(String local) throws IOException {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		try {
			try {
				return new ResponseEntity<byte[]>(
						IOUtils.toByteArray(new FileInputStream(
								new File(LinuxPath+local+".png"))),
						header, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<byte[]>(
						IOUtils.toByteArray(new FileInputStream(
								new File(LinuxPath+"daejeon"+".png"))),
						header, HttpStatus.CREATED);
			}
		} catch (Exception e1) {
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
}
