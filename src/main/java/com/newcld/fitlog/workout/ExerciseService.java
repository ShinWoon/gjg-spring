package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.DuplicateException;
import com.newcld.fitlog.common.NotFoundException;


@Service
@Transactional(readOnly = true)
public class ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }
    
    public List<Exercise> findAll(BodyPart bodyPart) {
        if(bodyPart == null) {
            return repository.findAllByOrderByNameAsc();
        }
        return repository.findAllByBodyPartOrderByNameAsc(bodyPart);
    }

    @Transactional 
    public Exercise create(ExerciseRequest request) {
        String name = request.name().trim();
        if(repository.existsByName(name)) {
            throw new DuplicateException("이미 등록된 동작이에요.");
        }
        return repository.save(new Exercise(name, request.bodyPart()));
    }

    @Transactional 
    public Exercise update(Long id, ExerciseRequest request) {
        Exercise exercise = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("동작을 찾을 수 없어요."));

        String name = request.name().trim();
        if(!exercise.getName().equals(name) && repository.existsByName(name)) {
            throw new DuplicateException("이미 등록된 동작이에요.");
        }

        exercise.update(name, request.bodyPart());
        return exercise;
    }

}
