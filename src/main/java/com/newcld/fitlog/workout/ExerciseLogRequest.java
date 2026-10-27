package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "세션에 동작 추가 요청")
public record ExerciseLogRequest(
    @Schema(description = "추가할 동작 ID (동작 목록 조회에서 확인)", example = "1")
    @NotNull (message = "동작을 선택해 주세요.")
    Long exerciseId,

    @Schema(description = "이 동작에 대한 피드백", example = "무릎이 안쪽으로 모이지 않게 주의")
    @Size(max = 2_000, message = "피드백은 2,000자까지 입력할 수 있어요.")
    String feedback
) {}
