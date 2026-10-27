package com.newcld.fitlog.workout;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "동작 등록·수정 요청")
public record ExerciseRequest(
    @Schema(description = "동작 이름", example = "백스쿼트")
    @NotBlank (message = "동작 이름을 입력해 주세요.")
    @Size(max = 50, message = "동작 이름은 50자까지 입력할 수 있어요.")
    String name,

    @Schema(description = "운동 부위", example = "LEGS")
    @NotNull (message = "운동 부위를 선택해 주세요.")
    BodyPart bodyPart
) {}
