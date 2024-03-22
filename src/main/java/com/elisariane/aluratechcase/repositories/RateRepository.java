package com.elisariane.aluratechcase.repositories;

import com.elisariane.aluratechcase.domain.rate.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
