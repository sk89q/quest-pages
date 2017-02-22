package com.sk89q.questpages.model;

public enum TriggerType {

    NONE("Always shown"),
    QUEST_TRIGGER("Never shown"),
    TASK_TRIGGER("Shown after some tasks are done"),
    ANTI_TRIGGER("Shown when parents are completed");

    private final String name;

    TriggerType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean usesTaskCount() {
        return this == TASK_TRIGGER;
    }
}
