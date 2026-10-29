package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

    // 세트(컬렉션)를 join fetch하면서 개수 제한을 걸면 메모리에서 잘라내므로, 기록과 세트를 두 번에 나눠 조회
    @Query("""
            select l from ExerciseLog l
            join fetch l.session s
            where l.exercise.id = :exerciseId
            and (:excludeSessionId is null or s.id <> :excludeSessionId)
            and l.sets is not empty
            order by s.sessionDate desc, l.id desc
            """)
    List<ExerciseLog> findHistoryByExercise(@Param("exerciseId") Long exerciseId, @Param("excludeSessionId") Long excludeSessionId, Pageable pageable);

    @Query("""
            select distinct l from ExerciseLog l
            left join fetch l.sets
            where l in :logs
            """)
    List<ExerciseLog> fetchSets(@Param("logs") List<ExerciseLog> logs);
}
