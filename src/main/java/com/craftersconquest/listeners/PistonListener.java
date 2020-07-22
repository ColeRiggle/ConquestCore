package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.List;

public class PistonListener implements Listener {

    private final ConquestCore instance;

    public PistonListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        onPistonEvent(event, event.getBlocks());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        onPistonEvent(event, event.getBlocks());
    }

    private void onPistonEvent(BlockPistonEvent event, List<Block> blocks) {
        instance.getForgeManager().onPistonEvent(event, blocks);
    }

}
