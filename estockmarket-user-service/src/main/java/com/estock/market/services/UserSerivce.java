package com.estock.market.services;

import com.estock.market.models.User;

import java.util.List;
import java.util.Optional;

public interface UserSerivce {
    User createUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> getUserByEmailAddress(String emailAddress);
    Optional<User> getUserByUsername(String username);
    User updateUser(User user);
    void removeUser(String id);
    List<User> findByFilterRegex(String searchtext);
    List<User> getAllUsers();
}
