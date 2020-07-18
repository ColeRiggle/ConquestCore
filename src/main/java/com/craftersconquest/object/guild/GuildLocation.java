package com.craftersconquest.object.guild;

import org.bukkit.Location;
import org.bukkit.World;

public class GuildLocation {

    private final int x;
    private final int y;
    private final int z;

    public GuildLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location getLocation(World world) {
        return new Location(world, x, y , z);
    }
}
