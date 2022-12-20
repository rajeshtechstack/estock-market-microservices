package com.estock.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EstockMarketServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstockMarketServiceRegistryApplication.class, args);
	}

}
