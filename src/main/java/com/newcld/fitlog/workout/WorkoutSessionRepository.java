package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findAllByOrderBySessionDateDescIdDesc();
}
