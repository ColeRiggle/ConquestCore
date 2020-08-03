package com.craftersconquest.regions.flags;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationFlag extends Flag<Location>{

    public LocationFlag(String name) {
        super(name);
    }

    @Override
    public Location parseInput(String input) throws InvalidFlagFormat {
        String[] components = input.split(":");
        try {
            World world = Bukkit.getWorld(components[0]);
            if (world == null) {
                throw new InvalidFlagFormat("World not found");
            }
            double x = Double.parseDouble(components[1]);
            double y = Double.parseDouble(components[2]);
            double z = Double.parseDouble(components[3]);
            float yaw = Float.parseFloat(components[4]);
            float pitch = Float.parseFloat(components[5]);
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException exception) {
            throw new InvalidFlagFormat("Provided coordinates not valid");
        }
    }

    @Override
    public String serialize(Location value) {
        return value.getWorld() + ":" + value.getX() + ":" + value.getY() + ":" + value.getZ() + ":" + value.getYaw() + ":" + value.getPitch();
    }
}
