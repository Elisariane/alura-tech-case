package com.elisariane.aluratechcase.domain.rate;

public record RateNPSDetailed(String courseName, String courseCode, Long promoters, Long detractors, Long nps) {

    public RateNPSDetailed(String courseName, String courseCode, Long promoters, Long detractors, Long nps) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.promoters = promoters;
        this.detractors = detractors;
        this.nps = nps;
    }
}
