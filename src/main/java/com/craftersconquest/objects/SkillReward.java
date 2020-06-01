package com.craftersconquest.objects;

public class SkillReward {

    private final int level;
    private final String description;

    public SkillReward(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

}
