package com.newcld.fitlog.workout;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "운동 세션 생성 요청")
public record SessionRequest(
    @Schema(description = "운동한 날짜", example = "2026-10-26")
    @NotNull(message = "날짜를 입력해 주세요.")
    LocalDate sessionDate,

    @Schema(description = "운동 종류 (PT: PT 수업, PERSONAL: 개인운동)", example = "PT")
    @NotNull(message = "운동 종류를 선택해 주세요.")
    SessionType type,

    @Schema(description = "세션 메모", example = "하체 데이")
    @Size(max = 1_000, message = "메모는 1,000자까지 입력할 수 있어요.")
    String memo
) {}
