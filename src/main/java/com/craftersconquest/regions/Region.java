package com.craftersconquest.regions;

import com.craftersconquest.regions.flags.Flag;
import org.bukkit.Location;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Region {

    private String name;
    private int priority;
    private ConcurrentHashMap flags;
    private Set<Area> areas;

    public Region(String name, int priority) {
        this.name = name;
        this.priority = priority;
        flags = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public <T extends Flag<V>, V> V getFlag(T flag) {
        Object object = flags.get(flag);
        V val;

        val = (V) object;

        return val;
    }

    public <T extends Flag<V>, V> void setFlag(T flag, @Nullable V val) {
        if (val == null) {
            flags.remove(flag);
        } else {
            flags.put(flag, val);
        }
    }

    public Map<Flag<?>, Object> getFlags() {
        return flags;
    }

    public void setFlags(Map<Flag<?>, Object> flags) {
        this.flags = new ConcurrentHashMap<>(flags);
    }

    public boolean contains(Location location) {
        for (Area area : areas) {
            String worldName = location.getWorld().getName();
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();

            if (area.contains(worldName, x, y, z)) {
                return true;
            }
        }

        return false;
    }
}
