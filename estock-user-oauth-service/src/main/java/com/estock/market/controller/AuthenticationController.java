package com.estock.market.controller;

import com.estock.market.config.WebSecurityConfig;
import com.estock.market.dto.GenericResponse;
import com.estock.market.dto.GenericTokenResponse;
import com.estock.market.dto.requests.UserRequest;
import com.estock.market.services.AuthService;
import com.estock.market.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1.0")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request){

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        logger.info("AuthenticationController :: Login:: {}, {}", userRequest.getUsername(), baseUrl);

        try{
            GenericResponse genericTokenResponse = authService.getAuthToken(userRequest, baseUrl);
            return new ResponseEntity<>(genericTokenResponse, HttpStatus.OK);
        }catch (Exception e){
            //var errorMessage = "Error while executing register user request for id - "+userRequest.getId();
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<GenericResponse> getUser(Principal user){
        try{
            logger.info("UserController :: getUser :: user details: {}", user);
            return new ResponseEntity<>(new GenericResponse("Data found", user), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing get all users request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
