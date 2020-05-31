package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;

public abstract class ConquestPlayer {

    public abstract boolean hasGuild();

    public abstract Guild getGuild();

    public abstract Bounty getBounty();
}
