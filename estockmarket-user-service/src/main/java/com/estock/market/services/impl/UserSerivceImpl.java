package com.estock.market.services.impl;

import com.estock.market.models.User;
import com.estock.market.repositories.UserRepository;
import com.estock.market.security.PasswordEncoder;
import com.estock.market.services.UserSerivce;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSerivceImpl implements UserSerivce {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSerivceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User createUser) {
        String password = passwordEncoder.hashPassword(createUser.getAccount().getPassword());
        createUser.getAccount().setPassword(password);
        User user = userRepository.save(createUser);
        return user;
    }

    @Override
    public Optional<User> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public Optional<User> getUserByEmailAddress(String emailAddress) {
        Optional<User> user = userRepository.getUserByEmailAddress(emailAddress);
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        return user;
    }

    @Override
    public User updateUser(User updatedUser) {
        User user =  userRepository.save(updatedUser);
        return user;
    }

    @Override
    public void removeUser(String id) {
        userRepository.deleteById(id);;
    }

    @Override
    public List<User> findByFilterRegex(String searchtext) {
        List<User> users = userRepository.findByFilterRegex(searchtext);
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
