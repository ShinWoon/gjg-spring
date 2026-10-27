package com.newcld.fitlog.workout;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_log_id")
    private ExerciseLog exerciseLog;

    @Column(nullable = false)
    private int setNo;

    private Double weightKg;

    @Column(nullable = false)
    private int reps;

    SetLog(ExerciseLog exerciseLog, int setNo, Double weightKg, int reps) {
        this.exerciseLog = exerciseLog;
        this.setNo = setNo;
        this.weightKg = weightKg;
        this.reps = reps;
    }
}
