package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.StateFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final ConquestCore instance;

    public BlockBreakListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();
        Player player = event.getPlayer();

        if (instance.getRegionManager().isSupportedWorld(location.getWorld())) {
            if (instance.getRegionManager().getFlagValueAt(Flags.BLOCK_BREAK, location) == StateFlag.State.DENY) {
                if (!player.hasPermission(Settings.ADMIN_PERMISSION)) {
                    Messaging.sendErrorMessage(player, "You cannot build here.");
                    event.setCancelled(true);
                    return;
                }
            }
        }


        instance.getSkillsManager().onValidBlockBreak(event);
        instance.getForgeManager().onBlockBreak(event);
    }
}
