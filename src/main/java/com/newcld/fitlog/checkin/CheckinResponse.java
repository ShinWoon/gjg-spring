package com.newcld.fitlog.checkin;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주간 체크인 응답")
public record CheckinResponse(
    @Schema(description = "체크인 ID", example = "1")
    Long id,

    @Schema(description = "체크인 날짜", example = "2026-10-26")
    LocalDate checkDate,

    @Schema(description = "공복 몸무게 (kg)", example = "72.4")
    Double weightKg, 

    @Schema(description = "메모", example = "컨디션 좋음")
    String memo
) {
    public static CheckinResponse from(WeeklyCheckin c) {
        return new CheckinResponse(c.getId(), c.getCheckDate(), c.getWeightKg(), c.getMemo());
    }
}
