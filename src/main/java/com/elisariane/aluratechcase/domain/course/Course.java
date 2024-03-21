package com.elisariane.aluratechcase.domain.course;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "courses")
@Entity(name = "course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 10, message = "Size must be between 0 and 10")
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)*$", message = "The course code must contain only letters and hyphens, without spaces, numbers or special characters")
    private String code;

    @NotNull
    private Long instructorId;

    @NotBlank
    private String description;

    @NotNull
    private Boolean status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime disabledAt;


    public Course(CourseRegistrationData courseRegistrationData, Long instructor) {
        this.name = courseRegistrationData.name();
        this.code = courseRegistrationData.code();
        this.instructorId = instructor;
        this.status = true;
        this.description = courseRegistrationData.description();
    }


}
