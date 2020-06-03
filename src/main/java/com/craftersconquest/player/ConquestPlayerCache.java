package com.craftersconquest.player;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class ConquestPlayerCache {

    private final ConquestCore instance;
    private final HashMap<UUID, ConquestPlayer> cachedPlayers;

    public ConquestPlayerCache(ConquestCore instance) {
        this.instance = instance;
        cachedPlayers = new HashMap<>();
        scheduleAutoSave();
    }

    private void scheduleAutoSave() {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(instance, new Runnable() {
            public void run() {
                for (ConquestPlayer player : cachedPlayers.values()) {
                    savePlayer(player);
                }
            }
        }, 200, 3600);
    }

    public ConquestPlayer getCachedConquestPlayer(UUID playerUUID) {
        if (!cacheContains(playerUUID)) {
            addPlayer(playerUUID);
        }

        return cachedPlayers.get(playerUUID);
    }

    public void addPlayer(UUID playerUUID) {
        if (!cacheContains(playerUUID)) {
            cachedPlayers.put(playerUUID, instance.getDataSource().loadPlayer(playerUUID));
        }
    }

    private boolean cacheContains(UUID playerUUID) {
        return cachedPlayers.containsKey(playerUUID);
    }

    public void removePlayer(UUID playerUUID) {
        savePlayer(getCachedConquestPlayer(playerUUID));
        cachedPlayers.remove(playerUUID);
    }

    private void savePlayer(ConquestPlayer player) {
        instance.getDataSource().savePlayer(player);
    }
}
