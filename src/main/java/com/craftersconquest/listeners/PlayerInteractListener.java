package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.menu.MenuInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final ConquestCore instance;

    public PlayerInteractListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack item = event.getItem();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();

        if (instance.getRegionManager().onPlayerInteract(event)) {
            return;
        }

        if (item != null) {
            if (item.equals(menu)) {
                event.setCancelled(true);
                new MenuInventory(instance).getInventory().open(player);
            } else if (item.getType() == Material.SADDLE) {
                instance.getHorseManager().onHorseClick(event);
            }
        }

        instance.getForgeManager().onPlayerInteractEvent(event);
    }
}
