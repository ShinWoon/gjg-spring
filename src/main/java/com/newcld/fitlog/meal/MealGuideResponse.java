package com.newcld.fitlog.meal;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "식단 가이드")
public record MealGuideResponse(
    @Schema(description = "가이드 ID", example = "1")
    Long id,

    @Schema(description = "끼니", example = "BREAKFAST")
    MealType mealType,

    @Schema(description = "끼니 한글 이름", example = "아침")
    String mealTypeLabel,

    @Schema(description = "가이드 적용 시작일", example = "2026-10-27")
    LocalDate validFrom,

    @Schema(description = "가이드 내용", example = "현미밥 150g + 닭가슴살 100g + 채소")
    String content
) {
    public static MealGuideResponse from(MealGuide g) {
        return new MealGuideResponse(g.getId(), g.getMealType(), g.getMealType().getLabel(), g.getValidFrom(), g.getContent());
    }
}
