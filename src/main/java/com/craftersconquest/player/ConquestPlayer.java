package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;
import com.craftersconquest.objects.skill.Skill;

import java.util.List;

public abstract class ConquestPlayer {

    public abstract List<Skill> getSkills();
    public abstract boolean hasGuild();
    public abstract Guild getGuild();
    public abstract Bounty getBounty();
}
