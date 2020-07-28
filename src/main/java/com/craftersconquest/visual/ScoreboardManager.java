package com.craftersconquest.visual;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.visual.scoreboard.BaseScoreboard;
import com.craftersconquest.visual.scoreboard.ConquestScoreboard;
import com.craftersconquest.visual.scoreboard.GuildScoreboard;
import com.craftersconquest.visual.scoreboard.format.DefaultFormatBehavior;
import com.craftersconquest.visual.scoreboard.format.FormatBehavior;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager implements Component {

    private final ConquestCore instance;
    private final Map<Player, ConquestScoreboard> players;
    private final FormatBehavior formatBehavior;

    private ConquestScoreboard baseScoreboard;
    private ConquestScoreboard guildScoreboard;

    private final static int UPDATE_INTERVAL = 80;

    public ScoreboardManager(ConquestCore instance) {
        this.instance = instance;
        this.players = new HashMap<>();
        formatBehavior = new DefaultFormatBehavior();
    }

    private void updateScoreboards() {
        for (Map.Entry<Player, ConquestScoreboard> entry : players.entrySet()) {
            entry.getValue().updatePlayer(entry.getKey());
        }
    }

    public void addPlayer(Player player) {
        showBaseScoreboard(player);
    }

    public void removePlayer(Player player) {
        players.get(player).removePlayer(player);
        players.remove(player);
    }

    public void showGuildScoreboard(Player player) {
        removeFromScoreboardIfApplicable(player);
        players.put(player, guildScoreboard);
        guildScoreboard.setupPlayer(player);
    }

    private void removeFromScoreboardIfApplicable(Player player) {
        if (players.containsKey(player)) {
            players.get(player).removePlayer(player);
        }
    }

    public void showBaseScoreboard(Player player) {
        removeFromScoreboardIfApplicable(player);
        players.put(player, baseScoreboard);
        baseScoreboard.setupPlayer(player);
    }

    @Override
    public void onEnable() {
        setupScoreboards();
        addOnlinePlayers();
        scheduleAutomaticUpdates();
    }

    private void scheduleAutomaticUpdates() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,
                () -> updateScoreboards(), 0 , UPDATE_INTERVAL);
    }


    private void setupScoreboards() {
        baseScoreboard = new BaseScoreboard(instance, formatBehavior);
        guildScoreboard = new GuildScoreboard(instance, formatBehavior);
    }

    private void addOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }

    @Override
    public void onDisable() {
        for (Map.Entry<Player, ConquestScoreboard> entry : players.entrySet()) {
            entry.getValue().removePlayer(entry.getKey());
        }
    }
}
