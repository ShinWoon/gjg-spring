package com.newcld.fitlog.workout;

public enum SessionType {
    PT("PT"),
    PERSONAL("개인운동");

    private final String label;

    SessionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
