package com.craftersconquest.visual;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.visual.scoreboard.ConquestScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardManager implements Component {

    private final ConquestCore instance;

    private ConquestScoreboard baseScoreboard;

    private final static int UPDATE_INTERVAL = 80;

    public ScoreboardManager(ConquestCore instance) {
        this.instance = instance;
    }

    private void updateScoreboards() {
        baseScoreboard.update();
    }

    public void addPlayer(Player player) {
        baseScoreboard.addPlayer(player);
    }

    public void removePlayer(Player player) {
        baseScoreboard.removePlayer(player);
    }

    @Override
    public void onEnable() {
        scheduleAutomaticUpdates();
        setupScoreboards();
        addOnlinePlayers();
    }

    private void scheduleAutomaticUpdates() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                updateScoreboards();
            }
        }, 0 , UPDATE_INTERVAL);
    }


    private void setupScoreboards() {
        baseScoreboard = ConquestScoreboard.getBaseScoreboard(instance);
    }

    private void addOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }

    @Override
    public void onDisable() {

    }
}