package com.newcld.fitlog.workout;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "피드백 모아보기 항목")
public record FeedbackResponse(
    @Schema(description = "동작 기록 ID", example = "1") 
    Long exerciseLogId,

    @Schema(description = "세션 ID (상세로 이동할 때 사용)", example = "1")
    Long sessionId,

    @Schema(description = "피드백 받은 날짜", example = "2026-10-26")
    LocalDate sessionDate,

    @Schema(description = "운동 종류 한글 이름", example = "PT") 
    String sessionTypeLabel,

    @Schema(description = "동작 ID", example = "1") 
    Long exerciseId,

    @Schema(description = "동작 이름", example = "백스쿼트") 
    String exerciseName,

    @Schema(description = "피드백", example = "무릎이 안쪽으로 모이지 않게 주의") 
    String feedback,

    @Schema(description = "고정 피드백 여부", example = "true") 
    boolean pinned
) {
    public static FeedbackResponse from(ExerciseLog log) {
        WorkoutSession s = log.getSession();
        return new FeedbackResponse(log.getId(), s.getId(), s.getSessionDate(), s.getType().getLabel(), log.getExercise().getId(), log.getExercise().getName(), log.getFeedback(), log.isPinned());
    }
}
