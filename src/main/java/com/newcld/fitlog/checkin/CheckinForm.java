package com.newcld.fitlog.checkin;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckinForm {
    
    @NotNull(message = "날짜를 입력해 주세요.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkDate = LocalDate.now();

    @NotNull(message = "몸무게를 입력해 주세요.")
    @DecimalMin(value = "20.0", message = "몸무게를 다시 확인해 주세요.")
    @DecimalMax(value = "300.0", message = "몸무게를 다시 확인해 주세요.")
    private Double weightKg;
    
    @Size(max = 500, message = "메모는 500자까지 입력할 수 있어요.")
    private String memo;
}
