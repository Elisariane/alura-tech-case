package com.elisariane.aluratechcase.controllers;


import com.elisariane.aluratechcase.domain.user.DataAuthentication;
import com.elisariane.aluratechcase.infra.security.DataTokenJWT;
import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DataAuthentication dataAuthentication) {

        var token = new UsernamePasswordAuthenticationToken(dataAuthentication.username(), dataAuthentication.password());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
    }
}
