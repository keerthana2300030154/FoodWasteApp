package com.foodwaste.service;

import com.foodwaste.entity.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByEmail(String email);
    boolean validateCredentials(String email, String rawPassword);
}
