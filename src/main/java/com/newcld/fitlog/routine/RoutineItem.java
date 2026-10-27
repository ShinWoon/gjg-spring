package com.newcld.fitlog.routine;

import com.newcld.fitlog.workout.Exercise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class RoutineItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private int orderNo;

    @Column(nullable = false)
    private int targetSets;

    private Integer targetReps;

    private Double targetWeightKg;

    @Column(length = 1_000)
    private String note;

    RoutineItem(Routine routine, Exercise exercise, int orderNo, int targetSets, Integer targetReps, Double targetWeightKg, String note) {
        this.routine = routine;
        this.exercise = exercise;
        this.orderNo = orderNo;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
        this.targetWeightKg = targetWeightKg;
        this.note = note;
    }
}
