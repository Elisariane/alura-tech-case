package com.elisariane.aluratechcase.service;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.enrollment.Enrollment;
import com.elisariane.aluratechcase.domain.enrollment.EnrollmentRegistrationData;
import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.repositories.CourseRepository;
import com.elisariane.aluratechcase.repositories.EnrollmentRepository;
import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository repository;


    @Transactional
    public ResponseEntity<String> enroll(EnrollmentRegistrationData enrollmentRegistrationData) {
        Optional<Course> course = courseRepository.findById(enrollmentRegistrationData.courseId());
        Optional<User> user = userRepository.findById(enrollmentRegistrationData.userId());

        if (course.isEmpty() || user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if(course.get().getStatus().equals(false)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry, but this course is inactive");
        }

        repository.save(new Enrollment(course.get(), user.get()));
        return  ResponseEntity.status(HttpStatus.CREATED).body("This enroll realized with successfully");


    }

}
