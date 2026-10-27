package com.newcld.fitlog.workout;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "세트 목록 교체 요청")
public record SetsRequest(
    @Schema(description = "새 세트 목록 (순서대로 1세트, 2세트...). 빈 목록을 보내면 세트가 모두 지워져요.")
    @NotNull(message = "세트 목록을 보내주세요.")
    @Size(max = 30, message = "세트는 30개까지 기록할 수 있어요.")
    @Valid 
    List<SetRequest> sets
) {}
