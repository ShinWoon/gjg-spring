package com.newcld.fitlog.checkin;

import java.time.LocalDate;

public record CheckinResponse(Long id, LocalDate checkDate, Double weightKg, String memo) {
    public static CheckinResponse from(WeeklyCheckin c) {
        return new CheckinResponse(c.getId(), c.getCheckDate(), c.getWeightKg(), c.getMemo());
    }
}
