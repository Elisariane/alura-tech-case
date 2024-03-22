package com.elisariane.aluratechcase.controllers;

import com.elisariane.aluratechcase.domain.user.UserListData;
import com.elisariane.aluratechcase.domain.user.UserRegistrationData;
import com.elisariane.aluratechcase.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRegistrationData userRegistrationData) {
        return service.createUser(userRegistrationData);
    }

    @GetMapping("/users/{username}")
    @Secured("ADMIN")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserListData> getUserByUsername(@PathVariable String username) {
        var user = service.getUserByUsername(username);

        if (user.isPresent()) {
            return  ResponseEntity.ok().body(new UserListData(user.get()));
        }

       return ResponseEntity.notFound().build();
    }

}
