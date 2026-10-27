package com.newcld.fitlog.workout;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SetRequest(
    @DecimalMin(value = "0.0", message = "무게는 0 이상이어야 해요.")
    @DecimalMax(value = "1000.0", message = "무게를 다시 확인해 주세요.")
    Double weightKg,

    @NotNull(message = "횟수를 입력해 주세요.")
    @Min(value = 1, message = "횟수는 1회 이상이어야 해요.")
    @Max(value = 1_000, message = "횟수를 다시 확인해 주세요.")
    Integer reps
) {}
