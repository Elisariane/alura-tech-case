package com.elisariane.aluratechcase.service;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.course.CourseRegistrationData;
import com.elisariane.aluratechcase.domain.user.Role;
import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.repositories.CourseRepository;
import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> createCourse(CourseRegistrationData courseRegistrationData) {
        Optional<Course> existingCourse = repository.findByCode(courseRegistrationData.code());

        if (existingCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This course already exists!");
        }

        Optional<User> user = userRepository.findByEmail(courseRegistrationData.instructor());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!user.get().getRole().equals(Role.INSTRUCTOR)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry, but only Instructor users can be authors of courses");
        }

        Course newCourse = new Course(courseRegistrationData, user.get().getId());

        repository.save(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully with the code: " + newCourse.getCode());
    }

    @Transactional
    public ResponseEntity<String> inactivated(String code) {
        Optional<Course> optionalCourse = repository.findByCode(code);
        if (optionalCourse.isPresent()) {
            repository.save(optionalCourse.get().inativacted());
            return ResponseEntity.ok().body("Course is inactivated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
