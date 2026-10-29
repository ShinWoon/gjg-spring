package com.newcld.fitlog.meal;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "식단 기록")
public record MealLogResponse(
    @Schema(description = "식단 기록 ID", example = "1")
    Long id,

    @Schema(description = "먹은 날짜", example = "2026-10-29")
    LocalDate mealDate,

    @Schema(description = "끼니", example = "LUNCH")
    MealType mealType,

    @Schema(description = "끼니 한글 이름", example = "점심")
    String mealTypeLabel,

    @Schema(description = "먹은 내용", example = "현미밥 반 공기, 닭가슴살 샐러드")
    String content,

    @Schema(description = "가이드 준수 여부. 판단하지 않았으면 null", example = "true", nullable = true)
    Boolean followedGuide
) {
    public static MealLogResponse from(MealLog m) {
        return new MealLogResponse(m.getId(), m.getMealDate(), m.getMealType(), m.getMealType().getLabel(), m.getContent(), m.getFollowedGuide());
    }
}
