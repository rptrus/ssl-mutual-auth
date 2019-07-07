package com.alphastar.SSLServer;

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
public class SSLServerApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(SSLServerApplication.class, args);
	}

	@PostConstruct
	public void startMessage() {
		// eye catcher
		logger.info("*** SSLServerApplication has started ***");
		logger.info("*** SSLServerApplication has started ***");
		logger.info("*** SSLServerApplication has started ***");
		logger.info("*** SSLServerApplication has started ***");
	}
}
