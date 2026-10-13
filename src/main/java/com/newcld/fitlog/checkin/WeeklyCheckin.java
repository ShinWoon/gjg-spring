package com.newcld.fitlog.checkin;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WeeklyCheckin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate checkDate;

    @Column(nullable =  false)
    private Double weightKg;

    private String memo;

    public WeeklyCheckin(LocalDate checkDate, Double weightKg, String memo) {
        this.checkDate = checkDate;
        this.weightKg = weightKg;
        this.memo = memo;
    }
}
