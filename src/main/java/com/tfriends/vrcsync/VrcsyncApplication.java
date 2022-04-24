package com.tfriends.vrcsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.tfriends.*"})
@EnableScheduling
public class VrcsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrcsyncApplication.class, args);
	}

}
