package com.craftersconquest.player;

import com.craftersconquest.object.Bounty;
import com.craftersconquest.object.Guild;
import com.craftersconquest.object.skill.Skill;

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
    public Bounty getBounty() {
        return null;
    }
}
