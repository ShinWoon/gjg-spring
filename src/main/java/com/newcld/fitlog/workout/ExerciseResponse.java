package com.newcld.fitlog.workout;

public record ExerciseResponse(Long id, String name, BodyPart bodyPart, String bodyPartLabel) {
    public static ExerciseResponse from(Exercise e) {
        return new ExerciseResponse(e.getId(), e.getName(), e.getBodyPart(), e.getBodyPart().getLabel());
    }
}
