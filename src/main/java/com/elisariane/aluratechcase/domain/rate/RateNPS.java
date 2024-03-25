package com.elisariane.aluratechcase.domain.rate;

import com.elisariane.aluratechcase.domain.course.Course;

public record RateNPS(Course course, Long promoters, Long detractors, Long nps) {

    public RateNPS(Course course, Long promoters, Long detractors, Long nps) {
        this.course = course;
        this.promoters = promoters;
        this.detractors = detractors;
        this.nps = nps;
    }

}
