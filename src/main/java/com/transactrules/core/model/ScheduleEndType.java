package com.transactrules.core.model;

public enum ScheduleEndType {
    NoEnd("NoEnd"),
    EndDate("EndDate"),
    Repeats("Repeats");

    private final String desc;

    ScheduleEndType(String desc) {
        this.desc = desc; }

    public String desc() {
        return desc;
    }

}