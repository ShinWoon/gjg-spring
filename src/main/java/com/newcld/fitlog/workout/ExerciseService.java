package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.BadRequestException;
import com.newcld.fitlog.common.DuplicateException;
import com.newcld.fitlog.common.NotFoundException;


@Service
@Transactional(readOnly = true)
public class ExerciseService {
    private final ExerciseRepository repository;
    private final ExerciseLogRepository exerciseLogRepository;

    public ExerciseService(ExerciseRepository repository, ExerciseLogRepository exerciseLogRepository) {
        this.repository = repository;
        this.exerciseLogRepository = exerciseLogRepository;
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

    public List<ExerciseHistoryResponse> findHistory(Long id, int limit, Long excludeSessionId) {
        if(limit < 1 || limit > 20) {
            throw new BadRequestException("조회 개수는 1~20 사이로 입력해 주세요.");
        }
        if(!repository.existsById(id)) {
            throw new NotFoundException("동작을 찾을 수 없어요.");
        }

        List<ExerciseLog> logs = exerciseLogRepository.findHistoryByExercise(id, excludeSessionId, PageRequest.of(0, limit));
        if(logs.isEmpty()) {
            return List.of();
        }
        // 같은 트랜잭션 안이라 이 조회로 logs의 세트가 채워져요. 반환값 대신 원래 순서의 logs를 그대로 사용
        exerciseLogRepository.fetchSets(logs);
        return logs.stream().map(ExerciseHistoryResponse::from).toList();
    }

}
