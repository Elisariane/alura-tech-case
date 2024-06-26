package com.elisariane.aluratechcase.controllers;


import com.elisariane.aluratechcase.domain.course.CourseRegistrationData;
import com.elisariane.aluratechcase.domain.course.DataListCourse;
import com.elisariane.aluratechcase.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping("/courses")
    @Secured("ADMIN")
    public Page<DataListCourse> getAll(Pageable pageable) {
        return service.getAllCourses(pageable);
    }

}
