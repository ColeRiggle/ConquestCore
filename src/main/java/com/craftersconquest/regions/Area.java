package com.craftersconquest.regions;

import org.bukkit.util.BlockVector;

public class Area {

    private final String name;
    private String worldName;
    private BlockVector pos1;
    private BlockVector pos2;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private int maxZ;
    private int minZ;

    public Area(String name, String worldName, BlockVector pos1, BlockVector pos2) {
        this.worldName = worldName;
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        calculateMinMax();
    }

    public String getName() {
        return name;
    }

    public BlockVector getPos1() {
        return pos1;
    }

    public void setPos1(BlockVector pos1) {
        this.pos1 = pos1;
        calculateMinMax();
    }

    public BlockVector getPos2() {
        return pos2;
    }

    public void setPos2(BlockVector pos2) {
        this.pos2 = pos2;
        calculateMinMax();
    }

    private void calculateMinMax() {
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());

        maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        minY = Math.min(pos1.getBlockY(), pos2.getBlockY());

        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public boolean contains(String world, int x, int y, int z) {
        return (world.equals(worldName)
                && (x <= maxX)
                && (y <= maxY)
                && (z <= maxZ)
                && (x >= minX)
                && (y >= minY)
                && (z >= minZ));
    }
}
