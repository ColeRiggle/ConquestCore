package com.craftersconquest.player;

import com.craftersconquest.object.Bounty;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.skill.Skill;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return skills;
    }

    public abstract Skill getMiningSkill();
    public abstract Skill getFarmingSkill();
    public abstract Skill getForagingSkill();
    public abstract Skill getEnchantingSkill();
    public abstract Skill getCombatSkill();

    public abstract boolean hasGuild();
    public abstract Guild getGuild();
    public abstract void setGuild(Guild guild);
    public abstract Bounty getBounty();

    public abstract Optional<Location> getLastLocation();
    public abstract void setLastLocation(Location location);
}
