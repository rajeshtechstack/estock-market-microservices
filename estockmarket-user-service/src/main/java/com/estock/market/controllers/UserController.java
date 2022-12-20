package com.estock.market.controllers;

import com.estock.market.dto.GenericResponse;
import com.estock.market.models.User;
import com.estock.market.services.UserSerivce;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1.0/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserSerivce userSerivce;

    public UserController(UserSerivce userSerivce) {
        this.userSerivce = userSerivce;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody User createUser){
        logger.info("UserController :: createUser:: user details: {}", createUser);
        //registerUserCommand.setId(UUID.randomUUID().toString());
        Optional <User> existUser = userSerivce.getUserByUsername(createUser.getAccount().getUsername());

        if(existUser.isEmpty()){
            try{
                User user = userSerivce.createUser(createUser);
                logger.info("UserController :: createUser:: User successfully registered:");
                return new ResponseEntity<>(new GenericResponse("User successfully registered", user), HttpStatus.CREATED);
            }catch (Exception e){
                logger.error("UserController :: createUser:: Error while executing register user request:: error: {}",
                        e.getMessage());
                var errorMessage = "Error while executing register user request ";
                return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        logger.info("UserController :: createUser:: Username already existed:");
        return new ResponseEntity<>(new GenericResponse("Username already existed."), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<GenericResponse> updateUser(@Valid @RequestBody User updateUser){
        try{
            User user = userSerivce.updateUser(updateUser);
            return new ResponseEntity<>(new GenericResponse("User successfully updated!!"), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing update user request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable(value = "id") String id){
        try{
            userSerivce.removeUser(id);
            return new ResponseEntity<>(new GenericResponse("User successfully removed!!"), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing remove user request for id - "+id;
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<GenericResponse> getAllUsers(){
        try{
           List<User> users = userSerivce.getAllUsers();
            if(users == null){
                return new ResponseEntity<>(new GenericResponse("Data not found", null), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new GenericResponse("Data found", users), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing get all users request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<GenericResponse> getUser(Principal principal){
        try{
            logger.info("UserController :: getUser :: user details: {}", principal.getName());
           Optional<User> user = userSerivce.getUserByUsername(principal.getName());
           if(user.isPresent()){
               return new ResponseEntity<>(new GenericResponse("Data found", user.get()), HttpStatus.OK);
           }
            return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
        }catch (Exception e){
            var errorMessage = "Error while executing get all users request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<GenericResponse> getUserById(@PathVariable(value = "id") String id){
        try{

            Optional <User> user = userSerivce.getUserById(id);
            if(user.get() == null ){
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new GenericResponse("Data found", user.get()), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing get user by id request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{filter}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<GenericResponse> searchUserByFilter(@PathVariable(value = "filter") String filter){
        try{
           List<User> users = userSerivce.findByFilterRegex(filter);
            if(users.isEmpty()){
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new GenericResponse("Data found", users), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing user search by filter request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
