package com.newcld.fitlog.meal;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "식단 기록 등록·수정 요청")
public record MealLogRequest(
    @Schema(description = "먹은 날짜", example = "2026-10-29")
    @NotNull(message = "날짜를 입력해 주세요.")
    LocalDate mealDate,

    @Schema(description = "끼니", example = "LUNCH")
    @NotNull(message = "끼니를 선택해 주세요.")
    MealType mealType,

    @Schema(description = "먹은 내용", example = "현미밥 반 공기, 닭가슴살 샐러드")
    @NotBlank(message = "먹은 내용을 입력해 주세요.")
    @Size(max = 1_000, message = "먹은 내용은 1,000자까지 입력할 수 있어요.")
    String content,

    @Schema(description = "가이드 준수 여부. 판단하지 않으면 null (생략 가능)", example = "true", nullable = true)
    Boolean followedGuide
) {}
