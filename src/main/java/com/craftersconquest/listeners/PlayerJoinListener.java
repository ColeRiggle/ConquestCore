package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerJoinListener implements Listener {

    private final ConquestCore instance;

    public PlayerJoinListener(ConquestCore instance) {
        this.instance = instance;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        instance.getCacheManager().addToCache(event.getUniqueId());
    }
}
