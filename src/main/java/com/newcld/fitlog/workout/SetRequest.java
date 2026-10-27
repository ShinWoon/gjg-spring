package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "세트 하나")
public record SetRequest(
    @Schema(description = "무게 (kg). 맨몸 운동이면 비워둬요.", example = "60")
    @DecimalMin(value = "0.0", message = "무게는 0 이상이어야 해요.")
    @DecimalMax(value = "1000.0", message = "무게를 다시 확인해 주세요.")
    Double weightKg,

    @Schema(description = "반복 횟수", example = "10")
    @NotNull(message = "횟수를 입력해 주세요.")
    @Min(value = 1, message = "횟수는 1회 이상이어야 해요.")
    @Max(value = 1_000, message = "횟수를 다시 확인해 주세요.")
    Integer reps
) {}
