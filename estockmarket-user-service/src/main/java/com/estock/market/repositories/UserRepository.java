package com.estock.market.repositories;

import com.estock.market.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'emailAddress': ?0}")
    Optional<User> getUserByEmailAddress(String emailAddress);

    @Query("{'account.username': ?0}")
    Optional<User> getUserByUsername(String username);

    @Query("{'$or': [{'firstName':{$regex: ?0, $options: '1i'}}, {'lastName':{$regex: ?0, $options: '1i'}}," +
            "{'emailAddress':{$regex: ?0, $options: '1'}}, {'account.username':{$regex: ?0, $options: '1i'}}]}")
    List<User> findByFilterRegex(String filter);
}
