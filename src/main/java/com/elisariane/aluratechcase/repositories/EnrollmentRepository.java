package com.elisariane.aluratechcase.repositories;

import com.elisariane.aluratechcase.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
