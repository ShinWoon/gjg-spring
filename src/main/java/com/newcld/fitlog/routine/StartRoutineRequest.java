package com.newcld.fitlog.routine;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "루틴으로 개인운동 시작 요청")
public record StartRoutineRequest(
        @Schema(description = "운동 날짜", example = "2026-10-28")
        @NotNull(message = "날짜를 입력해 주세요.")
        LocalDate sessionDate
) {}
