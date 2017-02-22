package com.sk89q.questpages.model;

public enum RepeatType {

    NONE("One time quest"),
    INSTANT("Repeatable instantly"),
    INTERVAL("Repeatable at interval"),
    TIME("Repeatable after cooldown");

    private final String name;

    RepeatType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean usesTime() {
        return this == TIME || this == INTERVAL;
    }

}
