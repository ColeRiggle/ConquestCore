package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.items.conquestitem.Item;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final ConquestCore instance;

    public BlockPlaceListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Material type = event.getBlockPlaced().getType();

        instance.getBlocklist().addIfNecessary(type, event.getBlockPlaced().getLocation());
        instance.getForgeManager().onBlockPlace(event);
    }
}
