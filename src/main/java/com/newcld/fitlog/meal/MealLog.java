package com.newcld.fitlog.meal;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 간식처럼 한 끼니에 여러 번 먹을 수 있어서 날짜+끼니를 unique로 두지 않아요
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MealType mealType;

    @Column(length = 1_000)
    private String content;

    // null이면 판단 안 함 (그 끼니에 가이드가 없을 때 등)
    private Boolean followedGuide;

    public MealLog(LocalDate mealDate, MealType mealType, String content, Boolean followedGuide) {
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.content = content;
        this.followedGuide = followedGuide;
    }

    public void update(LocalDate mealDate, MealType mealType, String content, Boolean followedGuide) {
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.content = content;
        this.followedGuide = followedGuide;
    }
}
