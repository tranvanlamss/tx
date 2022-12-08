package com.vietsoft;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@EnableAsync
public class MainApplication implements ApplicationRunner {
	static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {
		logger.info("Application starting...");
		SpringApplication.run(MainApplication.class, args);
	}
	
	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
    }
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("------------------------------Application started------------------------------");
	}
}
