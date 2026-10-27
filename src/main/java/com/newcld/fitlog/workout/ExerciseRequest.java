package com.newcld.fitlog.workout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExerciseRequest(
    @NotBlank (message = "동작 이름을 입력해 주세요.")
    @Size(max = 50, message = "동작 이름은 50자까지 입력할 수 있어요.")
    String name,

    @NotNull (message = "운동 부위를 선택해 주세요.")
    BodyPart bodyPart
) {}
