package com.newcld.fitlog.workout;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "운동 세션 요약 (목록용)")
public record SessionSummaryResponse(
    @Schema(description = "세션 ID", example = "1")
    Long id, 

    @Schema(description = "운동한 날짜", example = "2026-10-26")
    LocalDate sessionDate, 

    @Schema(description = "운동 종류 코드", example = "PT")
    SessionType type, 

    @Schema(description = "운동 종류 한글 이름 (화면 표시용)", example = "PT")
    String typeLabel, 

    @Schema(description = "세션 메모", example = "하체 데이")
    String memo
) {
    public static SessionSummaryResponse from(WorkoutSession s) {
        return new SessionSummaryResponse(s.getId(), s.getSessionDate(), s.getType(), s.getType().getLabel(), s.getMemo());
    }
}
