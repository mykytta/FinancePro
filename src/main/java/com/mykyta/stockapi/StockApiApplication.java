package com.mykyta.stockapi;

import com.mykyta.stockapi.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class StockApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApiApplication.class, args);
	}

}
