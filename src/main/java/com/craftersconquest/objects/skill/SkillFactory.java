package com.craftersconquest.objects.skill;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillFactory {

    public Skill getSkill(String type, double xp, int level) {
        if (type.equals("mining")) {
            return new Skill("mining", xp, level, getMiningRewards(), SkillAbility.EXCAVATOR, getMiningTracked());
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

    private HashMap<Material, Double> getMiningTracked() {
        HashMap<Material, Double> tracked = new HashMap<>();
        tracked.put(Material.STONE, 0.2);
        tracked.put(Material.COAL_ORE, 1.0);
        tracked.put(Material.IRON_ORE, 2.0);
        tracked.put(Material.LAPIS_ORE, 2.5);
        tracked.put(Material.REDSTONE_ORE, 3.0);
        tracked.put(Material.NETHERRACK, 1.0);
        tracked.put(Material.NETHER_QUARTZ_ORE, 4.0);
        tracked.put(Material.GOLD_ORE, 5.0);
        tracked.put(Material.DIAMOND_ORE, 10.0);
        tracked.put(Material.OBSIDIAN, 15.0);
        tracked.put(Material.EMERALD_ORE, 15.0);
        return tracked;
    }

    public List<String> getTypes() {
        List<String> types = new ArrayList<>();
        types.add("mining");
        types.add("farming");
        types.add("foraging");
        types.add("combat");
        types.add("enchanting");
        types.add("crime");
        return types;
    }
}
