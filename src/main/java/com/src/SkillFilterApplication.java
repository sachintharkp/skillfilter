package com.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaAuditing
public class SkillFilterApplication {

	private static Logger Log = Logger.getLogger(String.valueOf(SkillFilterApplication.class));

	public static void main(String[] args) {
		Log.info("Starting backend-service.... ");
		SpringApplication.run(SkillFilterApplication.class, args);
	}

}
