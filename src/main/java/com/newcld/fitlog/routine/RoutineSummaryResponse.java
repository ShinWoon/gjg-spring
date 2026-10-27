package com.newcld.fitlog.routine;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "루틴 요약 (목록용)")
public record RoutineSummaryResponse(
        @Schema(description = "루틴 ID", example = "1") Long id,
        @Schema(description = "루틴 이름", example = "하체 루틴") String name,
        @Schema(description = "메모", example = "PT 하체 데이 기준") String memo
) {
    public static RoutineSummaryResponse from(Routine r) {
        return new RoutineSummaryResponse(r.getId(), r.getName(), r.getMemo());
    }
}