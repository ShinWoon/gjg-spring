package com.newcld.fitlog.routine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "운동 기록으로 루틴 만들기 요청")
public record RoutineFromSessionRequest(
        @Schema(description = "새 루틴 이름", example = "하체 루틴")
        @NotBlank(message = "루틴 이름을 입력해 주세요.")
        @Size(max = 50, message = "루틴 이름은 50자까지 입력할 수 있어요.")
        String name
) {}