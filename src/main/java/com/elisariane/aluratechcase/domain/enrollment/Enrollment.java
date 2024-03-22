package com.elisariane.aluratechcase.domain.enrollment;

import com.elisariane.aluratechcase.domain.course.Course;
import com.elisariane.aluratechcase.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name= "enrollments", uniqueConstraints={
        @UniqueConstraint(columnNames = {"course_id", "user_id"})
})
@Entity(name = "enrollment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course courseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @CreationTimestamp
    private LocalDateTime enrollmentDate;

    public Enrollment(Course course, User user) {
        this.userId = user;
        this.courseId = course;
    }
}
