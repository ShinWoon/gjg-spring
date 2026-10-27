package com.newcld.fitlog.routine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "루틴 동작 하나")
public record RoutineItemRequest(
        @Schema(description = "동작 ID", example = "1")
        @NotNull(message = "동작을 선택해 주세요.")
        Long exerciseId,

        @Schema(description = "목표 세트 수", example = "3")
        @NotNull(message = "세트 수를 입력해 주세요.")
        @Min(value = 1, message = "세트 수는 1 이상이어야 해요.")
        @Max(value = 20, message = "세트 수를 다시 확인해 주세요.")
        Integer targetSets,

        @Schema(description = "목표 횟수. 비워두면 운동 시작 시 세트가 미리 채워지지 않아요.", example = "10")
        @Min(value = 1, message = "횟수는 1회 이상이어야 해요.")
        @Max(value = 1000, message = "횟수를 다시 확인해 주세요.")
        Integer targetReps,

        @Schema(description = "목표 무게 (kg). 맨몸 운동이면 비워둬요.", example = "60")
        @DecimalMin(value = "0.0", message = "무게는 0 이상이어야 해요.")
        @DecimalMax(value = "1000.0", message = "무게를 다시 확인해 주세요.")
        Double targetWeightKg,

        @Schema(description = "메모", example = "무릎 모이지 않게")
        @Size(max = 1000, message = "메모는 1000자까지 입력할 수 있어요.")
        String note
) {}