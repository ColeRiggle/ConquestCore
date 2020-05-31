package com.craftersconquest.player;

import com.craftersconquest.objects.Bounty;
import com.craftersconquest.objects.Guild;

public class OfflineConquestPlayer extends ConquestPlayer {

    private Guild guild;
    private Bounty bounty;

    private OfflineConquestPlayer(Guild guild, Bounty bounty) {
        this.guild = guild;
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
