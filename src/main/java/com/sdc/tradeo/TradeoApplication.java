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
	}

}
