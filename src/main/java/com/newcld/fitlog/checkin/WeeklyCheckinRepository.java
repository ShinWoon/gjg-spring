package com.newcld.fitlog.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyCheckinRepository extends JpaRepository<WeeklyCheckin, Long> {
    List<WeeklyCheckin> findAllByOrderByCheckDateDesc();

    boolean existsByCheckDate(LocalDate checkDate);

    Optional<WeeklyCheckin> findTopByOrderByCheckDateDesc();
}