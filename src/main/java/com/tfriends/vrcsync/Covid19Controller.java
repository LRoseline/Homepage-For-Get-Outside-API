package com.tfriends.vrcsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tfriends.domain.CountVO;
import com.tfriends.service.CovidService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/covid19/*")
@Controller
public class Covid19Controller {
    @Autowired
    private CovidService service;

	@ResponseBody
	@GetMapping("/covidvrc.png")
	public ResponseEntity<byte[]> covid19vrc0(CountVO vo) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        String daytime = sdf.format(date);

        vo = service.CountLoad(daytime);

        service.PrefabCount(vo);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(
			new File("D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/resources/covid19/covidvrc.png"))), header, HttpStatus.CREATED);
	}
	
	
	@ResponseBody
	@GetMapping("/covidvrc1.png")
	public ResponseEntity<byte[]> covid19vrc1() throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(
			new File("D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/resources/covid19/covidvrc1.png"))), header, HttpStatus.CREATED);
	}
}