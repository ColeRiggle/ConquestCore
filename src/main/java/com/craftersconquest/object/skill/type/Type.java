package com.craftersconquest.object.skill.type;

import com.craftersconquest.object.skill.Ability;
import com.craftersconquest.object.skill.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Type {

    private final String name;
    private final Ability ability;
    private final List<Reward> rewards;
    private final Map<Material, Double> trackedMaterials;

    protected Type(String name, Ability ability, List<Reward> rewards, Map<Material, Double> trackedMaterials) {
        this.name = name;
        this.ability = ability;
        this.rewards = rewards;
        this.trackedMaterials = trackedMaterials;
    }

    public String getName() {
        return name;
    }

    public Ability getAbility() {
        return ability;
    }

    public List<Reward> getRewardsForLevel(int level) {
        List<Reward> levelRewards = new ArrayList<>();
        for (Reward reward : rewards) {
            if (reward.getLevel() == level) {
                levelRewards.add(reward);
            }
        }
        return levelRewards;
    }

    public boolean tracks(Material material) {
        return trackedMaterials.containsKey(material);
    }

    public double getValue(Material material) {
        return trackedMaterials.get(material);
    }

}

