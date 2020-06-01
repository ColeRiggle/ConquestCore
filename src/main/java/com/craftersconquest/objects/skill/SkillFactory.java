package com.craftersconquest.objects.skill;

import java.util.ArrayList;
import java.util.List;

public class SkillFactory {

    public Skill getSkill(String type, double xp, int level) {
        if (type.equals("mining")) {
            return new Skill(xp, level, getMiningRewards(), SkillAbility.EXCAVATOR);
        }

        return null;
    }

    private List<SkillReward> getMiningRewards() {
        List<SkillReward> rewards = new ArrayList<>();
        rewards.add(SkillReward.fromLevelAndDescription(2, "Quarry Floor 2"));
        rewards.add(SkillReward.fromLevelAndDescription(5, "Quarry Floor 3"));
        rewards.add(SkillReward.fromLevelAndDescription(8, "Quarry Floor 4"));
        return rewards;
    }
}
