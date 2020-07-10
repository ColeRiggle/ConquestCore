package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    private final ConquestCore instance;

    public PlayerDeathListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack menu = instance.getMenuManager().getMenuItemStack();
        event.getDrops().remove(menu);
        instance.getHorseManager().endPlayerRides(player);
    }
}
