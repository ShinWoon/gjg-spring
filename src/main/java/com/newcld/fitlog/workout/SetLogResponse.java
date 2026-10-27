package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "세트 기록")
public record SetLogResponse(
    @Schema(description = "세트 ID", example = "1")
    Long id, 

    @Schema(description = "세트 번호", example = "1")
    int setNo, 

    @Schema(description = "무게 (kg). 맨몸 운동이면 null", example = "60")
    Double weightKg, 

    @Schema(description = "반복 횟수", example = "10")
    int reps
) {
    public static SetLogResponse from(SetLog s) {
        return new SetLogResponse(s.getId(), s.getSetNo(), s.getWeightKg(), s.getReps());
    }    
}
