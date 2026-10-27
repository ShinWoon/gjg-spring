package com.newcld.fitlog.workout;

import java.time.LocalDate;

public record SessionSummaryResponse(Long id, LocalDate sessionDate, SessionType type, String typeLabel, String memo) {
    public static SessionSummaryResponse from(WorkoutSession s) {
        return new SessionSummaryResponse(s.getId(), s.getSessionDate(), s.getType(), s.getType().getLabel(), s.getMemo());
    }
}
