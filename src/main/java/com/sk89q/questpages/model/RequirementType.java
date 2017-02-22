package com.sk89q.questpages.model;

public enum RequirementType {

    REQUIRES_ALL("When all parents are completed"),
    REQUIRES_SOME("After some parents are completed");

    private final String name;

    RequirementType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean usesRequirementCount() {
        return this == REQUIRES_SOME;
    }

}
