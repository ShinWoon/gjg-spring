package com.newcld.fitlog.workout;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "운동 세션 상세 (동작 기록과 세트 포함)")
public record SessionDetailResponse(
    @Schema(description = "세션 ID", example = "1")
    Long id, 

    @Schema(description = "운동한 날짜", example = "2026-10-26")
    LocalDate sessionDate, 

    @Schema(description = "운동 종류 코드", example = "PT")
    SessionType type, 

    @Schema(description = "운동 종류 한글 이름 (화면 표시용)", example = "PT")
    String typeLabel, 

    @Schema(description = "세션 메모", example = "하체 데이")
    String memo, 

    @Schema(description = "루틴으로 시작한 경우 루틴 이름", example = "하체 루틴")
    String routineName,

    @Schema(description = "동작 기록 목록 (순서대로)")
    List<ExerciseLogResponse> exercises
) {
    public static SessionDetailResponse from(WorkoutSession s) {
        return new SessionDetailResponse(s.getId(), s.getSessionDate(), s.getType(), s.getType().getLabel(), s.getMemo(), s.getRoutineName(), s.getExerciseLogs().stream().map(ExerciseLogResponse::from).toList());
    }
}
