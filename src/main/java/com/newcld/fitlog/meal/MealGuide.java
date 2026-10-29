package com.newcld.fitlog.meal;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 같은 끼니에 새 가이드를 등록하면 validFrom부터 새 가이드가 적용되고, 이전 가이드는 이력으로 남아요
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"meal_type", "valid_from"}))
public class MealGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MealType mealType;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false, length = 1_000)
    private String content;

    public MealGuide(MealType mealType, LocalDate validFrom, String content) {
        this.mealType = mealType;
        this.validFrom = validFrom;
        this.content = content;
    }

    public void update(MealType mealType, LocalDate validFrom, String content) {
        this.mealType = mealType;
        this.validFrom = validFrom;
        this.content = content;
    }
}
