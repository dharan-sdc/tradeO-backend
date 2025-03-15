package com.sdc.tradeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.sdc.tradeo")
public class TradeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeoApplication.class, args);
		System.out.println("Email configuration connected");
		System.out.println("Mysql Database Connected");
		System.out.println("Spring Boot App has started successfully!");
		System.out.println();
	}

}
