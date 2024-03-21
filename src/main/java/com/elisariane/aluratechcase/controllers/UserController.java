package com.elisariane.aluratechcase.controllers;

import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.domain.user.UserRegistrationData;
import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRegistrationData userRegistrationData) {
        Optional<User> existingUser = repository.getUserByUsername(userRegistrationData.username());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This user already exists!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegistrationData.password());
        User newUser = new User(userRegistrationData, encryptedPassword);
        newUser = repository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with ID: " + newUser.getId());
    }


}
