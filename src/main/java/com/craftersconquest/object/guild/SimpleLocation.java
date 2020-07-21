package com.craftersconquest.object.guild;

import org.bukkit.Location;
import org.bukkit.World;

public class SimpleLocation {

    private final int x;
    private final int y;
    private final int z;

    public SimpleLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static SimpleLocation fromLocation(Location location) {
        return new SimpleLocation((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }

    public Location getLocation(World world) {
        return new Location(world, x, y , z);
    }

    @Override
    public String toString() {
        return x + "%" + y + "%" + z;
    }
}
