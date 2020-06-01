package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.Skill;

import java.util.List;

public class OfflineConquestPlayer extends ConquestPlayer {

    private List<Skill> skills;
    private Guild guild;
    private Bounty bounty;

    public OfflineConquestPlayer(List<Skill> skills, Guild guild, Bounty bounty) {
        this.skills = skills;
        this.guild = guild;
        this.bounty = bounty;
    }

    @Override
    public List<Skill> getSkills() {
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
