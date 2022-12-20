package com.estock.market.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/userAuthenticationFallBack")
    public Mono<String> userAuthenticationFallBack() {
        return Mono.just("User authentication Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/marketApiFallback")
    public Mono<String> paymentServiceFallBack() {
        return Mono.just("Market Service is taking too long to respond or is down. Please try again later");
    }

    @RequestMapping("/userApiFallBack")
    public Mono<String> userServiceFallBack() {
        return Mono.just("User Service is taking too long to respond or is down. Please try again later");
    }
}
