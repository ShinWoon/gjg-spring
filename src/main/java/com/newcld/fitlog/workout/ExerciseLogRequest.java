package com.newcld.fitlog.workout;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExerciseLogRequest(
    @NotNull (message = "동작을 선택해 주세요.")
    Long exerciseId,

    @Size(max = 2_000, message = "피드백은 2,000자까지 입력할 수 있어요.")
    String feedback
) {}
