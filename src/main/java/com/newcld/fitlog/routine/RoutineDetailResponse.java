package com.newcld.fitlog.routine;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "루틴 상세")
public record RoutineDetailResponse(
        @Schema(description = "루틴 ID", example = "1") Long id,
        @Schema(description = "루틴 이름", example = "하체 루틴") String name,
        @Schema(description = "메모", example = "PT 하체 데이 기준") String memo,
        @Schema(description = "이 루틴을 만든 운동 기록 ID (직접 만들었으면 null)", example = "1") Long sourceSessionId,
        @Schema(description = "동작 목록 (순서대로)") List<RoutineItemResponse> items
) {
    public static RoutineDetailResponse from(Routine r) {
        return new RoutineDetailResponse(r.getId(), r.getName(), r.getMemo(), r.getSourceSessionId(), r.getItems().stream().map(RoutineItemResponse::from).toList());
    }
}
