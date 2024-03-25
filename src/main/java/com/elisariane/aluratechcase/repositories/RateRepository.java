package com.elisariane.aluratechcase.repositories;

import com.elisariane.aluratechcase.domain.rate.Rate;
import com.elisariane.aluratechcase.domain.rate.RateNPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("""
            SELECT NEW com.elisariane.aluratechcase.domain.rate.RateNPS ( e.courseId ,
                   (COUNT(CASE WHEN r.score >= 9 THEN 1 ELSE NULL END) / COUNT(r.score)) * 100 AS promoters,
                   (COUNT(CASE WHEN r.score <= 6 THEN 1 ELSE NULL END) / COUNT(r.score)) * 100 AS detractors,
                   ((COUNT(CASE WHEN r.score >= 9 THEN 1 ELSE NULL END ) - COUNT(CASE WHEN r.score <= 6 THEN 1 ELSE NULL END)) / COUNT(r.score)) * 100) AS nps
            FROM enrollment e
            JOIN rate r ON e.courseId  = r.course
            GROUP BY e.courseId\s
            HAVING COUNT(e.courseId) > 4""")
    List<RateNPS> calculateNpsForCoursesWithFourOrMoreEnrollments();


    @Query("""
            SELECT r.id FROM rate r WHERE r.student.id  = ?1 AND r.course.id = ?2 
            """)
    Optional<Rate> findByStudentIdAndCourseId(Long student, Long course);
}
