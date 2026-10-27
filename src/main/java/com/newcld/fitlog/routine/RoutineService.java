package com.newcld.fitlog.routine;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.BadRequestException;
import com.newcld.fitlog.common.NotFoundException;
import com.newcld.fitlog.workout.Exercise;
import com.newcld.fitlog.workout.ExerciseLog;
import com.newcld.fitlog.workout.ExerciseRepository;
import com.newcld.fitlog.workout.SessionDetailResponse;
import com.newcld.fitlog.workout.SetLog;
import com.newcld.fitlog.workout.WorkoutSession;
import com.newcld.fitlog.workout.WorkoutSessionRepository;


@Service
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionRepository sessionRepository;

    public RoutineService(RoutineRepository routineRepository,
                          ExerciseRepository exerciseRepository,
                          WorkoutSessionRepository sessionRepository) {
        this.routineRepository = routineRepository;
        this.exerciseRepository = exerciseRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<RoutineSummaryResponse> findAll() {
        return routineRepository.findAllByOrderByIdDesc().stream()
                .map(RoutineSummaryResponse::from)
                .toList();
    }

    public RoutineDetailResponse findDetail(Long id) {
        return RoutineDetailResponse.from(getRoutine(id));
    }

    @Transactional
    public RoutineDetailResponse create(RoutineRequest request) {
        Routine routine = new Routine(request.name().trim(), request.memo(), null);
        fillItems(routine, request.items());
        routineRepository.save(routine);
        routineRepository.flush();
        return RoutineDetailResponse.from(routine);
    }

    @Transactional
    public RoutineDetailResponse createFromSession(Long sessionId, RoutineFromSessionRequest request) {
        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("운동 기록을 찾을 수 없어요."));
        if (session.getExerciseLogs().isEmpty()) {
            throw new BadRequestException("동작이 없는 운동 기록으로는 루틴을 만들 수 없어요.");
        }

        Routine routine = new Routine(request.name().trim(), null, session.getId());
        for (ExerciseLog log : session.getExerciseLogs()) {
            SetLog topSet = log.getSets().stream()
                    .max(Comparator.comparingDouble(s -> s.getWeightKg() == null ? 0 : s.getWeightKg()))
                    .orElse(null);

            routine.addItem(log.getExercise(),
                    Math.max(log.getSets().size(), 1),
                    topSet == null ? null : topSet.getReps(),
                    topSet == null ? null : topSet.getWeightKg(),
                    log.getFeedback());
        }

        routineRepository.save(routine);
        routineRepository.flush();
        return RoutineDetailResponse.from(routine);
    }

    @Transactional
    public RoutineDetailResponse update(Long id, RoutineRequest request) {
        Routine routine = getRoutine(id);
        routine.update(request.name().trim(), request.memo());
        routine.clearItems();
        fillItems(routine, request.items());
        routineRepository.flush();
        return RoutineDetailResponse.from(routine);
    }

    @Transactional
    public void delete(Long id) {
        routineRepository.delete(getRoutine(id));
    }

    @Transactional
    public SessionDetailResponse start(Long id, StartRoutineRequest request) {
        Routine routine = getRoutine(id);
        WorkoutSession session = WorkoutSession.fromRoutine(
                request.sessionDate(), routine.getId(), routine.getName());

        for (RoutineItem item : routine.getItems()) {
            ExerciseLog log = session.addExerciseLog(item.getExercise(), null);
            if (item.getTargetReps() != null) {
                for (int i = 0; i < item.getTargetSets(); i++) {
                    log.addSet(item.getTargetWeightKg(), item.getTargetReps());
                }
            }
        }

        sessionRepository.save(session);
        sessionRepository.flush();
        return SessionDetailResponse.from(session);
    }

    private void fillItems(Routine routine, List<RoutineItemRequest> items) {
        for (RoutineItemRequest item : items) {
            Exercise exercise = exerciseRepository.findById(item.exerciseId())
                    .orElseThrow(() -> new NotFoundException("동작을 찾을 수 없어요."));
            routine.addItem(exercise, item.targetSets(), item.targetReps(),
                    item.targetWeightKg(), item.note());
        }
    }

    private Routine getRoutine(Long id) {
        return routineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("루틴을 찾을 수 없어요."));
    }
}