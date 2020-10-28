package com.nazriaz.botwow;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotWowApplication {

	public static void main(String[] args) {
		OpenCV.loadLocally();
		SpringApplication.run(BotWowApplication.class, args);
	}

}
