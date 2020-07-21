package com.craftersconquest.teleporter;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleporter {

    private final ConquestCore instance;

    public Teleporter(ConquestCore instance) {
        this.instance = instance;
    }

    public void teleport(Player player, Location destination, boolean withDelay) {
        instance.getCacheManager().getConquestPlayer(player).setLastLocation(player.getLocation());
        player.teleport(destination);
    }

    public void teleportToGuild(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Messaging.sendPlayerSpecificMessage(player, "Teleporting to your guild...");
        Location destination = new Location(instance.getGuildManager().getWorld(conquestPlayer.getGuild()), 0, 28.5, 0);
        instance.getTeleporter().teleport(player, destination, false);

        Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
            @Override
            public void run() {
                instance.getForgeManager().setupGuildHologramsForPlayer(player);
            }
        }, 100L);
    }
}
