package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();

        if (item != null) {
            if (item.equals(menu)) {
                event.setCancelled(true);
            } else if (item.getType().equals(Material.SADDLE)) {
                instance.getHorseManager().onSaddleDrop(player, item);
            }
        }
    }
}
