package com.newcld.fitlog.workout;

public record SetLogResponse(Long id, int setNo, Double weightKg, int reps) {
    public static SetLogResponse from(SetLog s) {
        return new SetLogResponse(s.getId(), s.getSetNo(), s.getWeightKg(), s.getReps());
    }    
}
