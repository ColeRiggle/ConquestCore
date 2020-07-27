package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.entity.Player;

public abstract class ConquestScoreboard {

    public abstract void update();

    public abstract void addPlayer(Player player);

    public abstract void removePlayer(Player player);

    public static ConquestScoreboard getBaseScoreboard(ConquestCore instance) {
        return new BaseScoreboard(instance);
    }

    public static ConquestScoreboard getGuildScoreboard(ConquestCore instance) {
        return new GuildScoreboard(instance);
    }
}
