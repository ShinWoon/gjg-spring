package com.newcld.fitlog.routine;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "루틴 생성·수정 요청")
public record RoutineRequest(
        @Schema(description = "루틴 이름", example = "하체 루틴")
        @NotBlank(message = "루틴 이름을 입력해 주세요.")
        @Size(max = 50, message = "루틴 이름은 50자까지 입력할 수 있어요.")
        String name,

        @Schema(description = "루틴 메모", example = "PT 하체 데이 기준")
        @Size(max = 1000, message = "메모는 1000자까지 입력할 수 있어요.")
        String memo,

        @Schema(description = "동작 목록 (순서대로). 수정 시에는 이 목록으로 통째로 바뀌어요.")
        @NotNull(message = "동작 목록을 보내주세요.")
        @Size(max = 30, message = "루틴에는 동작을 30개까지 넣을 수 있어요.")
        @Valid
        List<RoutineItemRequest> items
) {}
