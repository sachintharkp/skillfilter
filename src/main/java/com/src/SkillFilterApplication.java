package com.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class SkillFilterApplication {

	private static Logger Log = Logger.getLogger(String.valueOf(SkillFilterApplication.class));

	public static void main(String[] args) {
		Log.info("Starting backend-service.... ");
		SpringApplication.run(SkillFilterApplication.class, args);
	}

}
