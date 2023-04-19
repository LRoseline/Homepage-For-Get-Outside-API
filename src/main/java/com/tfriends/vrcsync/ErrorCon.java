package com.tfriends.vrcsync;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorCon implements ErrorController {
    // String Path = "/usr/local/tomcat/webapps/vrcsync/WEB-INF/classes/static/errorimg/";
    String Path = "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/vrcsync/WEB-INF/classes/static/errorimg/";
    // String Path = "C:/Users/amb17/eclipse-workspace/vrcsync/src/main/resources/static/errorimg/";

    @GetMapping("/error")
    public ResponseEntity<byte[]> errorimage(HttpServletRequest request) throws Exception {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String code = "";

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            switch(statusCode) {
                case 403:
                code = "403";
                break;

                case 404:
                code = "404";
                break;

                case 500:
                code = "500";
                break;

                default:
                code = "etc";
                break;
            }
        }

        HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(new File(Path+code+".png"))),header, HttpStatus.CREATED);
    }
}
