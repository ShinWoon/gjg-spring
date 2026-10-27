package com.newcld.fitlog.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByOrderByNameAsc();
    List<Exercise> findAllByBodyPartOrderByNameAsc(BodyPart bodyPart);
    boolean existsByName(String name);
}
