package com.elisariane.aluratechcase.service;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.rate.Rate;
import com.elisariane.aluratechcase.domain.rate.RateNPS;
import com.elisariane.aluratechcase.domain.rate.RateNPSDetailed;
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

import java.util.List;
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
        Optional<User> student = userRepository.findById(rateRegistrationData.studentId());

        if (course.isEmpty() || student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Rate> rate = repository.findByStudentIdAndCourseId(student.get().getId(), course.get().getId());

        if (rate.isPresent()) {
            return ResponseEntity.badRequest().body("The student already evaluate this course!");
        }

        if (rateRegistrationData.score() < 6) {
            if (rateRegistrationData.rateDescription().isBlank()) {
                return ResponseEntity.badRequest().body("The field 'rate description' can't be blank if score is under 6");
            }
            
                EmailSender.send(instructor.get().getEmail(), course.get().getName(), rateRegistrationData.rateDescription());
        }
        
        repository.save(new Rate(rateRegistrationData, course.get(), student.get()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public List<RateNPSDetailed> getNPSOfCourses() {
        List<RateNPS> allRateNPSList = repository.calculateNpsForCoursesWithFourOrMoreEnrollments();

        return allRateNPSList.stream().map(rateNPS -> new RateNPSDetailed(rateNPS.course().getName(), rateNPS.course().getCode(), rateNPS.promoters(), rateNPS.detractors(), rateNPS.nps())).toList();
    }

}
