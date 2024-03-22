package com.elisariane.aluratechcase.controllers;

import com.elisariane.aluratechcase.domain.enrollment.EnrollmentRegistrationData;
import com.elisariane.aluratechcase.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @PostMapping("/enrollment")
    @Secured({})
    public ResponseEntity<String> enroll(@RequestBody @Valid EnrollmentRegistrationData enrollmentRegistrationData) {
        return service.enroll(enrollmentRegistrationData);
    }

}
