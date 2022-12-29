package com.estock.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.client.RestTemplate;

@EnableAuthorizationServer
//@EnableEurekaClient
@SpringBootApplication
public class EstockUserOauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstockUserOauthServiceApplication.class, args);
    }

}
