package com.newcld.fitlog.meal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealLogRepository extends JpaRepository<MealLog, Long> {
    List<MealLog> findAllByMealDateBetween(LocalDate from, LocalDate to);
}
