package com.newcld.fitlog.workout;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SetsRequest(
    @NotNull(message = "세트 목록을 보내주세요.")
    @Valid 
    List<SetRequest> sets
) {}
