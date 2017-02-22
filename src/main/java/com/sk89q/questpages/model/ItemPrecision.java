package com.sk89q.questpages.model;

public enum ItemPrecision {

    PRECISE("Precise"),
    NBT_FUZZY("NBT Fuzzy"),
    FUZZY("Fuzzy"),
    ORE_DICTIONARY("Oredict");

    private final String name;

    ItemPrecision(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
