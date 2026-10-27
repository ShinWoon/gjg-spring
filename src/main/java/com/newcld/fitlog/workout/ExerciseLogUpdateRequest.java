package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "동작 기록 피드백 수정 요청")
public record ExerciseLogUpdateRequest(
        @Schema(description = "피드백", example = "무릎이 안쪽으로 모이지 않게 주의")
        @Size(max = 2_000, message = "피드백은 2,000자까지 입력할 수 있어요.")
        String feedback,

        @Schema(description = "고정 피드백 여부. 고정하면 고정 피드백 목록에 나와요.", example = "true")
        @NotNull(message = "고정 여부를 보내주세요.")
        Boolean pinned
) {}
