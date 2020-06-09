package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
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
        instance.getBlocklist().addIfNecessary(event.getBlockPlaced().getType(), event.getBlockPlaced().getLocation());
    }
}
