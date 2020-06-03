package com.craftersconquest.player;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.objects.Component;
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
        for (Player player : Bukkit.getOnlinePlayers()) {
            cache.removePlayer(player.getUniqueId());
        }
    }

    public void addToCache(UUID playerUUID) {
        Bukkit.getLogger().info("Adding: " + playerUUID + " to the cache.");
        cache.addPlayer(playerUUID);
    }

    public void removeFromCache(UUID playerUUID) {
        cache.removePlayer(playerUUID);
    }

}
