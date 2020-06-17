package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropItemListener implements Listener {

    private final ConquestCore instance;

    public PlayerDropItemListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();
        if (item.equals(menu)) {
            event.setCancelled(true);
        }
    }
}
