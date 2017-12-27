package com.transactrules.core.model;

public enum ScheduleFrequency {
    Daily("Daily"),
    Monthly("Monthly");

    private final String desc;

    ScheduleFrequency(String desc) {
        this.desc = desc; }

    public String desc() {
        return desc;
    }

}