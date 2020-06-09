package com.craftersconquest.skills;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TrackedBlockBreak {

    private final Player player;
    private final Material material;
    private final Location location;

    public TrackedBlockBreak(Player player, Material material, Location location) {
        this.player = player;
        this.material = material;
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public Material getMaterial() {
        return material;
    }

    public Location getLocation() { return location; }
}
