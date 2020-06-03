package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.Skill;

import java.util.List;
import java.util.UUID;

public class OfflineConquestPlayer extends ConquestPlayer {

    private UUID playerUUID;
    private List<Skill> skills;
    private Guild guild;
    private Bounty bounty;

    public OfflineConquestPlayer(UUID playerUUID, List<Skill> skills, Guild guild, Bounty bounty) {
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
        return null;
    }

    @Override
    public Skill getFarmingSkill() {
        return null;
    }

    @Override
    public Skill getForagingSkill() {
        return null;
    }

    @Override
    public Skill getEnchantingSkill() {
        return null;
    }

    @Override
    public Skill getCombatSkill() {
        return null;
    }

    @Override
    public Skill getCrimeSkill() {
        return null;
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
    public Bounty getBounty() {
        return null;
    }
}
