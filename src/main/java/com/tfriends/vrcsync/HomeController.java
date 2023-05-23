package com.tfriends.vrcsync;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String requestMethodName(Model mdl) {
        mdl.addAttribute("getting", "타임리프 리메이크 완료");
        return "index";
    }

    @ResponseBody
	@GetMapping("/weather/bg")
	public ResponseEntity<byte[]> ambtvImageNow(String icon) throws Exception {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(
			IOUtils.toByteArray(new FileInputStream(
					new File("D:/Network/Program Files/Apache Software Foundation/Tomcat 10.1/resources/weatherbg/"+icon+".jpg"))),
			header, HttpStatus.CREATED);
	}
}