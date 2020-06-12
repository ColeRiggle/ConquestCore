package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.entity.Player;

public abstract class ConquestScoreboard {

    public static ConquestScoreboard getBaseScoreboard(ConquestCore instance) {
        return new BaseScoreboard(instance);
    }

    public abstract void update();

    public abstract void addPlayer(Player player);

    public abstract void removePlayer(Player player);
}
