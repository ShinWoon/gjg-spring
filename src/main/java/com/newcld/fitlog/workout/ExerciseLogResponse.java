package com.newcld.fitlog.workout;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동작 기록")
public record ExerciseLogResponse(
    @Schema(description = "동작 기록 ID (세트 교체 API에서 사용)", example = "1")
    Long id, 

    @Schema(description = "세션 안에서의 순서", example = "1")
    int orderNo, 

    @Schema(description = "동작 ID", example = "1")
    Long exerciseId, 

    @Schema(description = "동작 이름", example = "백스쿼트")
    String exerciseName, 

    @Schema(description = "피드백", example = "무릎이 안쪽으로 모이지 않게 주의")
    String feedback, 

    @Schema(description = "고정 피드백 여부", example = "false")
    boolean pinned, 

    @Schema(description = "세트 목록 (순서대로)")
    List<SetLogResponse> sets
) {
    public static ExerciseLogResponse from(ExerciseLog log) {
        return new ExerciseLogResponse(log.getId(), log.getOrderNo(), log.getExercise().getId(), log.getExercise().getName(), log.getFeedback(), log.isPinned(), log.getSets().stream().map(SetLogResponse::from).toList());
    }
}
