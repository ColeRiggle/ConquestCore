package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final ConquestCore instance;

    public InventoryClickListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();

        if (event.getInventory() instanceof HorseInventory) {
            if (!player.hasPermission(Settings.ADMIN_PERMISSION)) {
                event.setCancelled(true);
            }
        }

        if (item != null) {
            if (item.equals(menu)) {
                event.setCancelled(true);
            }
        }
    }
}
