package com.newcld.fitlog.meal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MealGuideRepository extends JpaRepository<MealGuide, Long> {
    List<MealGuide> findAllByOrderByValidFromDescIdDesc();

    boolean existsByMealTypeAndValidFrom(MealType mealType, LocalDate validFrom);

    // 끼니별로 date 이전(당일 포함) 중 가장 최근에 시작된 가이드
    @Query("""
            select g from MealGuide g
            where g.validFrom = (
                select max(g2.validFrom) from MealGuide g2
                where g2.mealType = g.mealType
                and g2.validFrom <= :date
            )
            """)
    List<MealGuide> findCurrent(@Param("date") LocalDate date);
}
