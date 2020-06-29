package com.craftersconquest.object.guild;

import com.craftersconquest.player.ConquestPlayer;

import java.util.Date;
import java.util.List;

public class Guild {

    private final String name;

    public Guild(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() { return name; }

    public ConquestPlayer geAtOwner() {
        return null;
    }

    public List<ConquestPlayer> getMembers() {
        return null;
    }

    public Stockpile getStockpile() {
        return null;
    }

    public Upgrades getUpgrades() {
        return null;
    }

    public int getElo() {
        return 0;
    }

    public Date getLastWar() {
        return null;
    }
}
