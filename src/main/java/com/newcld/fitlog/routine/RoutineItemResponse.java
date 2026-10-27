package com.newcld.fitlog.routine;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "루틴 동작")
public record RoutineItemResponse(
        @Schema(description = "루틴 동작 ID", example = "1") 
        Long id,

        @Schema(description = "순서", example = "1") 
        int orderNo,

        @Schema(description = "동작 ID", example = "1") 
        Long exerciseId,

        @Schema(description = "동작 이름", example = "백스쿼트") 
        String exerciseName,

        @Schema(description = "목표 세트 수", example = "3") 
        int targetSets,

        @Schema(description = "목표 횟수", example = "10") 
        Integer targetReps,

        @Schema(description = "목표 무게 (kg)", example = "60") 
        Double targetWeightKg,

        @Schema(description = "메모", example = "무릎 모이지 않게") 
        String note
) {
    public static RoutineItemResponse from(RoutineItem item) {
        return new RoutineItemResponse(item.getId(), item.getOrderNo(), item.getExercise().getId(), item.getExercise().getName(), item.getTargetSets(), item.getTargetReps(), item.getTargetWeightKg(), item.getNote());
    }
}
