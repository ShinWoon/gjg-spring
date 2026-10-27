package com.newcld.fitlog.workout;

public record ExerciseLogRepsonse(Long id, int orderNo, Long exerciseId, String exerciseName, String feedback, boolean pinned) {
    public static ExerciseLogRepsonse from(ExerciseLog log) {
        return new ExerciseLogRepsonse(log.getId(), log.getOrderNo(), log.getExercise().getId(), log.getExercise().getName(), log.getFeedback(), log.isPinned());
    }
}
