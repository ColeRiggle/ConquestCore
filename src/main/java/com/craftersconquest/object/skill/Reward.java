package com.craftersconquest.object.skill;

public class Reward {

    private final int level;
    private final String description;

    private Reward(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public static Reward fromLevelAndDescription(int level, String description) {
        return new Reward(level, description);
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

}
