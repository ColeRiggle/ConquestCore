package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private ConquestCore instance;

    public PlayerRespawnListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        instance.getMenuManager().givePlayerMenu(player);
    }
}
