package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryMoveItemListener implements Listener {

    private final ConquestCore instance;

    public InventoryMoveItemListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        ItemStack item = event.getItem();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();
        if (item != null && item.equals(menu)) {
            event.setCancelled(true);
        }
    }
}
