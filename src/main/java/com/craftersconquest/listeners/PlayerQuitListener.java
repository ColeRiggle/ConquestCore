package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final ConquestCore instance;

    public PlayerQuitListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        onPlayerDisconnect(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKickEvent(PlayerKickEvent event) {
        onPlayerDisconnect(event.getPlayer());
    }

    private void onPlayerDisconnect(Player player) {
        instance.getScoreboardManager().removePlayer(player);
        instance.getHorseManager().endPlayerRides(player);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(instance,
                () -> instance.getCacheManager().removeFromCache(player.getUniqueId()));
    }

}
