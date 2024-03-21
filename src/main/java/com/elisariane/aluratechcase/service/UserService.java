package com.elisariane.aluratechcase.service;

import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.domain.user.UserRegistrationData;
import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ResponseEntity<String> createUser(UserRegistrationData userRegistrationData) {
        Optional<User> existingUser = getUserByUsername(userRegistrationData.username());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This user already exists!");
        }

        String encryptedPassword = encryptPassword(userRegistrationData.password());
        User newUser = createUserFromRegistrationData(userRegistrationData, encryptedPassword);
        newUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with ID: " + newUser.getId());
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private User createUserFromRegistrationData(UserRegistrationData userRegistrationData, String encryptedPassword) {
        return new User(userRegistrationData, encryptedPassword);
    }
}