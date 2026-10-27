package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.NotFoundException;


@Service 
@Transactional(readOnly = true)
public class FeedbackService {
    private final ExerciseLogRepository exerciseLogRepository;
    private final ExerciseRepository exerciseRepository;

    public FeedbackService(ExerciseLogRepository exerciseLogRepository, ExerciseRepository exerciseRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<FeedbackResponse> findPinned() {
        return exerciseLogRepository.findPinned().stream().map(FeedbackResponse::from).toList();
    }

    public List<FeedbackResponse> findByExercise(Long exerciseId) {
        if(!exerciseRepository.existsById(exerciseId)) {
            throw new NotFoundException("동작을 찾을 수 없어요.");
        }
        return exerciseLogRepository.findFeedbacksByExercise(exerciseId).stream()
        .map(FeedbackResponse::from)
        .toList();
    }
}
