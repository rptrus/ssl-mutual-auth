package com.alphastar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
@Profile("!test")
public class ClientApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@PostConstruct
	public void startMessage() {
		// eye catcher
		logger.info("*** SSLClientApplication has started ***");
		logger.info("*** SSLClientApplication has started ***");
		logger.info("*** SSLClientApplication has started ***");
		logger.info("*** SSLClientApplication has started ***");
	}
}
