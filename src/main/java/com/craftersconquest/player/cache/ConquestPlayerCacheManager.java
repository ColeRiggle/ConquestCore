package com.craftersconquest.player.cache;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConquestPlayerCacheManager implements Component {

    private final ConquestCore instance;
    private ConquestPlayerCache cache;

    public ConquestPlayerCacheManager(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public void onEnable() {
        cache = new ConquestPlayerCache(instance);
        for (Player player : Bukkit.getOnlinePlayers()) {
            cache.addPlayer(player.getUniqueId());
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Saving player cache on disable...");
        for (Player player : Bukkit.getOnlinePlayers()) {
            cache.removePlayer(player.getUniqueId());
        }
        Bukkit.getLogger().info("Save complete.");
    }

    public void addToCache(UUID playerUUID) {
        cache.addPlayer(playerUUID);
    }

    public void removeFromCache(UUID playerUUID) {
        cache.removePlayer(playerUUID);
    }

    public boolean isCached(UUID playerUUID) {
        return cache.isCached(playerUUID);
    }

    public ConquestPlayer getConquestPlayer(UUID playerUUID) { return cache.getCachedConquestPlayer(playerUUID); }

    public ConquestPlayer getConquestPlayer(Player player) { return getConquestPlayer(player.getUniqueId()); }

}
