package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동작 응답")
public record ExerciseResponse(
    @Schema(description = "동작 ID", example = "1")
    Long id, 

    @Schema(description = "동작 이름", example = "백스쿼트")
    String name, 

    @Schema(description = "운동 부위 코드", example = "LEGS")
    BodyPart bodyPart, 

    @Schema(description = "운동 부위 한글 이름 (화면 표시용)", example = "하체")
    String bodyPartLabel
) {
    public static ExerciseResponse from(Exercise e) {
        return new ExerciseResponse(e.getId(), e.getName(), e.getBodyPart(), e.getBodyPart().getLabel());
    }
}
