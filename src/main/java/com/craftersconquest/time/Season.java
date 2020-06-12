package com.craftersconquest.time;

public enum Season {
    SUMMER("Summer"),
    FALL("Fall"),
    WINTER("Winter"),
    SPRING("Spring");

    private final String name;

    Season(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
