package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.RegionManager;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.StateFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerMoveListener implements Listener {

    private final ConquestCore instance;

    public PlayerMoveListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getWorld().getName().equals(Settings.RPG_WORLD_NAME)) {
            if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
                return;
            }
        }

        Player player = event.getPlayer();
        RegionManager regionManager = instance.getRegionManager();

        Region fromRegion = instance.getRegionManager().getRegionAt(from);
        Region toRegion = instance.getRegionManager().getRegionAt(to);

        if (fromRegion != toRegion) {
            boolean shouldNotifyOnEnterForToRegion = regionManager.getFlagValueAt(Flags.NOTIFY_ENTER, to);

            Bukkit.getLogger().info("FROM: " + fromRegion.getName() + " TO: " + toRegion.getName() + " SN: " + shouldNotifyOnEnterForToRegion);

            if (shouldNotifyOnEnterForToRegion) {
                String toMessage = regionManager.getFlagValueAt(Flags.GREET_MESSAGE, to);
                instance.getVisualsManager().sendTitleBarMessage(player, ChatColor.AQUA + toMessage, ChatColor.RED + "Recommended level: 10");
            } else {
                if (instance.getRegionManager().getFlagValueAt(Flags.NOTIFY_LEAVE, from)) {
                    instance.getVisualsManager().sendTitleBarMessage(player, ChatColor.GRAY + "[Leaving " + fromRegion.getFlag(Flags.PRETTY_NAME) + "]");
                }
            }
        }


    }
}
