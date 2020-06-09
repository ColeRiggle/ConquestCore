package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ConquestPlayer {

    public abstract UUID getUUID();

    public List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(getMiningSkill());
        skills.add(getFarmingSkill());
        skills.add(getForagingSkill());
        skills.add(getEnchantingSkill());
        skills.add(getCombatSkill());
        skills.add(getCrimeSkill());
        return skills;
    }

    public abstract Skill getMiningSkill();
    public abstract Skill getFarmingSkill();
    public abstract Skill getForagingSkill();
    public abstract Skill getEnchantingSkill();
    public abstract Skill getCombatSkill();
    public abstract Skill getCrimeSkill();

    public abstract boolean hasGuild();
    public abstract Guild getGuild();
    public abstract Bounty getBounty();
}
