package com.elisariane.aluratechcase.domain.rate;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "rates")
@Entity(name = "rate")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "The score must be between 1 to 10")
    @Max(value = 10, message = "The score must be between 1 to 10")
    private int score;

    private String rateDescription;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;


    public Rate(RateRegistrationData registrationData, Course course, User student) {
        this.score = registrationData.score();
        this.rateDescription = registrationData.rateDescription();
        this.course = course;
        this.student = student;
    }

}
