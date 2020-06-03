package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.Skill;

import java.util.UUID;

public abstract class ConquestPlayer {

    public abstract UUID getUUID();

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
