package com.elisariane.aluratechcase.service;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.rate.Rate;
import com.elisariane.aluratechcase.domain.rate.RateRegistrationData;
import com.elisariane.aluratechcase.domain.user.User;
import com.elisariane.aluratechcase.repositories.CourseRepository;
import com.elisariane.aluratechcase.repositories.RateRepository;
import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RateRepository repository;

    @Autowired
    private UserRepository userRepository;
      
    @Transactional
    public ResponseEntity<String> evaluateCourse(RateRegistrationData rateRegistrationData) {
        Optional<Course> course = courseRepository.findByCode(rateRegistrationData.courseCode());
        Optional<User> instructor = userRepository.findById(course.get().getInstructorId());

        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (rateRegistrationData.score() < 6) {
            if (rateRegistrationData.rateDescription().isBlank()) {
                return ResponseEntity.badRequest().body("The field 'rate description' can't be blank if score is under 6");
            }
            
            EmailSender.send(instructor.get().getEmail(), course.get().getName(), rateRegistrationData.rateDescription());
        }
        
        repository.save(new Rate(rateRegistrationData, course.get()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
