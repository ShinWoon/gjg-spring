package com.newcld.fitlog.workout;

import java.util.List;

public record ExerciseLogResponse(Long id, int orderNo, Long exerciseId, String exerciseName, String feedback, boolean pinned, List<SetLogResponse> sets) {
    public static ExerciseLogResponse from(ExerciseLog log) {
        return new ExerciseLogResponse(log.getId(), log.getOrderNo(), log.getExercise().getId(), log.getExercise().getName(), log.getFeedback(), log.isPinned(), log.getSets().stream().map(SetLogResponse::from).toList());
    }
}
