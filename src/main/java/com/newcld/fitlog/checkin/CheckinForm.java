package com.newcld.fitlog.checkin;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "주간 체크인 등록·수정 요청")
@Getter
@Setter
public class CheckinForm {
    
    @Schema(description = "체크인 날짜 (보통 월요일)", example = "2026-10-26")
    @NotNull(message = "날짜를 입력해 주세요.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkDate = LocalDate.now();

    @Schema(description = "공복 몸무게 (kg)", example = "72.4")
    @NotNull(message = "몸무게를 입력해 주세요.")
    @DecimalMin(value = "20.0", message = "몸무게를 다시 확인해 주세요.")
    @DecimalMax(value = "300.0", message = "몸무게를 다시 확인해 주세요.")
    private Double weightKg;
    
    @Schema(description = "메모", example = "컨디션 좋음")
    @Size(max = 500, message = "메모는 500자까지 입력할 수 있어요.")
    private String memo;
}
