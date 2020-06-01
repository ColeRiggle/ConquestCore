package com.craftersconquest.objects.skill;

public class SkillReward {

    private final int level;
    private final String description;

    private SkillReward(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public static SkillReward fromLevelAndDescription(int level, String description) {
        return new SkillReward(level, description);
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

}
