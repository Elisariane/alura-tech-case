package com.elisariane.aluratechcase.controllers;


import com.elisariane.aluratechcase.domain.course.CourseRegistrationData;
import com.elisariane.aluratechcase.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService service;


    @PostMapping("/course")
    @Secured("ADMIN")
    public ResponseEntity<String> createCourse(@RequestBody @Valid CourseRegistrationData courseRegistrationData) {
        return service.createCourse(courseRegistrationData);
    }

    @DeleteMapping("/course/{code}")
    @Secured("ADMIN")
    public ResponseEntity<String> inativacted(@PathVariable String code) {
        return service.inactivated(code);
    }

}
