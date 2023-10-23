package com.jobscout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

public class JobScoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobScoutApplication.class, args);
	}

}