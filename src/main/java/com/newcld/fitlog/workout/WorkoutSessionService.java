package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.NotFoundException;

@Service 
@Transactional(readOnly = true)
public class WorkoutSessionService {
    private final WorkoutSessionRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSessionService(WorkoutSessionRepository sessionRepository, ExerciseRepository exerciseRepository) {
        this.sessionRepository = sessionRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<SessionSummaryResponse> findAll() {
        return sessionRepository.findAllByOrderBySessionDateDescIdDesc().stream().map(SessionSummaryResponse::from).toList();
    }

    public SessionDetailResponse findDetail(Long id) {
        return SessionDetailResponse.from(getSession(id));
    }

    @Transactional 
    public SessionDetailResponse create(SessionRequest request) {
        WorkoutSession session = sessionRepository.save(
            new WorkoutSession(request.sessionDate(), request.type(), request.memo())
        );
        return SessionDetailResponse.from(session);
    }

    @Transactional 
    public SessionDetailResponse addExerciseLog(Long sessionId, ExerciseLogRequest request) {
        WorkoutSession session = getSession(sessionId);
        Exercise exercise = exerciseRepository.findById(request.exerciseId())
        .orElseThrow(() -> new NotFoundException("동작을 찾울 수 없어요."));

        session.addExerciseLog(exercise, request.feedback());
        sessionRepository.flush();
        return SessionDetailResponse.from(session);
    }

    private WorkoutSession getSession(Long id) {
        return sessionRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("운동 기록을 찾을 수 없어요."));
    }
}
