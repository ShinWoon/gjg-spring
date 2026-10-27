package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    @Query("""
            select l from ExerciseLog l
            join fetch l.session s
            join fetch l.exercise e
            where l.pinned = true
            order by s.sessionDate desc, l.id desc
            """)
    List<ExerciseLog> findPinned();

    @Query("""
            select l from ExerciseLog l
            join fetch l.session s
            join fetch l.exercise e
            where e.id = :exerciseId
            and l.feedback is not null
            and l.feedback != ''
            order by s.sessionDate desc, l.id desc
            """)
    List<ExerciseLog> findFeedbacksByExercise(@Param("exerciseId") Long exerciseId);
}
