package com.newcld.fitlog.meal;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "식단 가이드 등록·수정 요청")
public record MealGuideRequest(
    @Schema(description = "끼니", example = "BREAKFAST")
    @NotNull(message = "끼니를 선택해 주세요.")
    MealType mealType,

    @Schema(description = "가이드 적용 시작일", example = "2026-10-27")
    @NotNull(message = "적용 시작일을 입력해 주세요.")
    LocalDate validFrom,

    @Schema(description = "가이드 내용", example = "현미밥 150g + 닭가슴살 100g + 채소")
    @NotBlank(message = "가이드 내용을 입력해 주세요.")
    @Size(max = 1_000, message = "가이드 내용은 1,000자까지 입력할 수 있어요.")
    String content
) {}
