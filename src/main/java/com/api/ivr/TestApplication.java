package com.api.ivr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class TestApplication {
	static Logger LOGGER = LogManager.getLogger(TestApplication.class);
	public static void main(String[] args) {
		LOGGER.info("RestApi Started");
		SpringApplication.run(TestApplication.class, args);
		
	}
	
	
	

}
