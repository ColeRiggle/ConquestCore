package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.visual.scoreboard.format.FormatBehavior;
import org.bukkit.entity.Player;

public abstract class ConquestScoreboard {

    private final FormatBehavior formatBehavior;

    protected ConquestScoreboard(FormatBehavior formatBehavior) {
        this.formatBehavior = formatBehavior;
    }

    public FormatBehavior getFormatBehavior() {
        return formatBehavior;
    }

    public abstract void setupPlayer(Player player);

    public abstract void updatePlayer(Player player);

    public abstract void removePlayer(Player player);
}
