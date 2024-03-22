package com.elisariane.aluratechcase.domain.course;

import java.time.LocalDateTime;

public record DataListCourse(String name, String code, String description, Long instructor, Boolean status, LocalDateTime createdAt, LocalDateTime disabledAt) {
    public DataListCourse(Course course) {
        this(course.getName(), course.getCode(), course.getDescription(), course.getInstructorId(), course.getStatus(), course.getCreatedAt(), course.getDisabledAt());
    }
}
