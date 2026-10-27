package com.newcld.fitlog.routine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findAllByOrderByIdDesc();
}
