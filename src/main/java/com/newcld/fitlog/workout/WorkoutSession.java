package com.newcld.fitlog.workout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutSession {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SessionType type;

    @Column(length = 1_000)
    private String memo;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNo ASC")
    private List<ExerciseLog> exerciseLogs = new ArrayList<>();

    public WorkoutSession(LocalDate sessionDate, SessionType type, String memo) {
        this.sessionDate = sessionDate;
        this.type = type;
        this.memo = memo;
    }

    public ExerciseLog addExerciseLog(Exercise exercise, String feedback) {
        ExerciseLog log = new ExerciseLog(this, exercise, exerciseLogs.size() + 1, feedback);
        exerciseLogs.add(log);
        return log;
    }
}
