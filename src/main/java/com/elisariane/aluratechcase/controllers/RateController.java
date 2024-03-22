package com.elisariane.aluratechcase.controllers;

import com.elisariane.aluratechcase.domain.rate.RateRegistrationData;
import com.elisariane.aluratechcase.service.RateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {

    @Autowired
    private RateService service;

    @PostMapping("/rate")
    @Secured("STUDENT")
    public ResponseEntity<String> rate(@RequestBody @Valid RateRegistrationData rateRegistrationData) {
        return service.evaluateCourse(rateRegistrationData);
    }

}
