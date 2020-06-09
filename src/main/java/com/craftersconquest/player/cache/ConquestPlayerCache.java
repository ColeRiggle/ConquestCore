package com.craftersconquest.player.cache;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConquestPlayerCache {

    private final ConquestCore instance;
    private final ConcurrentHashMap<UUID, ConquestPlayer> cachedPlayers;

    public ConquestPlayerCache(ConquestCore instance) {
        this.instance = instance;
        cachedPlayers = new ConcurrentHashMap<>();
        scheduleAutoSave();
    }

    private void scheduleAutoSave() {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(instance, new Runnable() {
            public void run() {
                Bukkit.getLogger().info("Saving player cache");
                for (ConquestPlayer player : cachedPlayers.values()) {
                    savePlayer(player);
                }
                Bukkit.getLogger().info("Successfully saved player cache");
            }
        }, 200, 3600);
    }

    public ConquestPlayer getCachedConquestPlayer(UUID playerUUID) {
        if (!cachedPlayers.containsKey(playerUUID)) {
            addPlayer(playerUUID);
        }

        return cachedPlayers.get(playerUUID);
    }

    public void addPlayer(UUID playerUUID) {
        Bukkit.getLogger().info("Adding: " + playerUUID + " to the cache.");
        ConquestPlayer player = instance.getDataSource().loadPlayer(playerUUID);
        cachedPlayers.putIfAbsent(playerUUID, player);
        Bukkit.getLogger().info("Size: " + cachedPlayers.size());
    }

    public void removePlayer(UUID playerUUID) {
        if (cachedPlayers.contains(playerUUID)) {
            savePlayer(getCachedConquestPlayer(playerUUID));
            cachedPlayers.remove(playerUUID);
        }
    }

    private void savePlayer(ConquestPlayer player) {
        instance.getDataSource().savePlayer(player);
    }
}
