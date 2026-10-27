package com.newcld.fitlog.workout;

public enum BodyPart {
    CHEST("가슴"),
    BACK("등"),
    SHOULDER("어깨"),
    LEGS("하체"),
    ARMS("팔"),
    CORE("코어"),
    CARDIO("유산소"),
    ECT("기타");

    private final String label;

    BodyPart(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}