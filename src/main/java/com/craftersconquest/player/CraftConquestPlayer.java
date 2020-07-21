package com.craftersconquest.player;

import com.craftersconquest.object.Bounty;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.skill.Skill;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CraftConquestPlayer extends ConquestPlayer {

    private UUID playerUUID;
    private List<Skill> skills;
    private Guild guild;
    private Bounty bounty;
    private Location lastLocation;

    public CraftConquestPlayer(UUID playerUUID, List<Skill> skills, Guild guild, Bounty bounty) {
        this.playerUUID = playerUUID;
        this.skills = skills;
        this.guild = guild;
        this.bounty = bounty;
    }

    @Override
    public UUID getUUID() {
        return playerUUID;
    }

    @Override
    public Skill getMiningSkill() {
        return skills.get(0);
    }

    @Override
    public Skill getFarmingSkill() {
        return skills.get(1);
    }

    @Override
    public Skill getForagingSkill() {
        return skills.get(2);
    }

    @Override
    public Skill getEnchantingSkill() {
        return skills.get(3);
    }

    @Override
    public Skill getCombatSkill() {
        return skills.get(4);
    }

    @Override
    public boolean hasGuild() {
        return guild != null;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    @Override
    public Bounty getBounty() {
        return null;
    }

    @Override
    public Optional<Location> getLastLocation() {
        return Optional.ofNullable(lastLocation);
    }

    @Override
    public void setLastLocation(Location location) {
        this.lastLocation = location;
    }
}
