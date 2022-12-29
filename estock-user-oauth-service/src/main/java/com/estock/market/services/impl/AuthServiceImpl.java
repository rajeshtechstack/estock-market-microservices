package com.estock.market.services.impl;

import com.estock.market.controller.AuthenticationController;
import com.estock.market.dto.GenericResponse;
import com.estock.market.dto.GenericTokenResponse;
import com.estock.market.dto.requests.UserRequest;
import com.estock.market.models.User;
import com.estock.market.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${estock.market.loginurl}")
    private String loginUrl;

    private final RestTemplate restTemplate;

    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GenericResponse getAuthToken(UserRequest userRequest, String baseUrl){
        String serviceUrl = "http://estock-market-authentication-service/oauth/token";
        String hostURL = baseUrl+"/oauth/token";

        logger.info("AuthServiceImpl :: getAuthToken:: {}, {}", loginUrl, hostURL);
        String encodedString = clientId+":"+clientSecret;

        String sceretKey= new String(Base64.encodeBase64(encodedString.getBytes()));
        //String url = "";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Basic " + sceretKey);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", userRequest.getUsername());
        requestBody.add("password", userRequest.getPassword());
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<GenericTokenResponse> response = restTemplate.exchange(hostURL,
                HttpMethod.POST, formEntity, GenericTokenResponse.class);
        GenericResponse genericResponse = new GenericResponse<>("Authenticated successfully",
                response.getBody());
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //logger.info("AuthServiceImpl :: User Info :: {}", user);
        return  genericResponse;
    }
}
