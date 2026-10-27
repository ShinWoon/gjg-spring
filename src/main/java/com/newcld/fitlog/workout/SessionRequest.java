package com.newcld.fitlog.workout;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SessionRequest(
    @NotNull(message = "날짜를 입력해 주세요.")
    LocalDate sessionDate,

    @NotNull(message = "운동 종류를 선택해 주세요.")
    SessionType type,

    @Size(max = 1_000, message = "메모는 1,000자까지 입력할 수 있어요.")
    String memo
) {}
