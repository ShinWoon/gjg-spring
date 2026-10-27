package com.newcld.fitlog.workout;

import java.time.LocalDate;
import java.util.List;

public record SessionDetailResponse(Long id, LocalDate sessionDate, SessionType type, String typeLabel, String memo, List<ExerciseLogRepsonse> exercises) {
    public static SessionDetailResponse from(WorkoutSession s) {
        return new SessionDetailResponse(s.getId(), s.getSessionDate(), s.getType(), s.getType().getLabel(), s.getMemo(), s.getExerciseLogs().stream().map(ExerciseLogRepsonse::from).toList());
    }
}
