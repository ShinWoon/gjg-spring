package com.newcld.fitlog.workout;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동작별 지난 기록 (세트 입력 화면 참고용)")
public record ExerciseHistoryResponse(
    @Schema(description = "동작 기록 ID", example = "1")
    Long exerciseLogId,

    @Schema(description = "세션 ID (상세로 이동할 때 사용)", example = "1")
    Long sessionId,

    @Schema(description = "운동한 날짜", example = "2026-10-26")
    LocalDate sessionDate,

    @Schema(description = "운동 종류 한글 이름", example = "PT")
    String sessionTypeLabel,

    @Schema(description = "피드백", example = "무릎이 안쪽으로 모이지 않게 주의")
    String feedback,

    @Schema(description = "세트 목록 (순서대로)")
    List<SetLogResponse> sets
) {
    public static ExerciseHistoryResponse from(ExerciseLog log) {
        WorkoutSession s = log.getSession();
        return new ExerciseHistoryResponse(log.getId(), s.getId(), s.getSessionDate(), s.getType().getLabel(), log.getFeedback(), log.getSets().stream().map(SetLogResponse::from).toList());
    }
}
