package com.craftersconquest.regions;

import com.craftersconquest.regions.flags.Flag;
import org.bukkit.Location;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Region {

    private String name;
    private Region parent;
    private int priority;
    private Map<Flag<?>, Object> flags;
    private Set<Area> areas;

    public Region(String name, int priority) {
        this(name, priority, new HashMap<>(), new HashSet<>());
    }

    public Region(String name, int priority, Map<Flag<?>, Object> flags, Set<Area> areas) {
        this.name = name;
        this.priority = priority;
        this.flags = flags;
        this.areas = areas;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public Set<Region> getParents() {
        Set<Region> parents = new HashSet<>();
        Region parent = this.parent;
        while (parent != null) {
            parents.add(parent);
            parent = parent.parent;
        }
        return parents;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void removeFlag(Flag<?> flag) {
        flags.remove(flag);
    }

    public <T extends Flag<V>, V> V getFlag(T flag) {
        Object object = flags.get(flag);
        V val;

        if (object != null) {
            val = (V) object;
        } else {
            return null;
        }

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

    public Set<Area> getAreas() {
        return areas;
    }

    public void addArea(Area area) {
        areas.add(area);
    }

    public void removeArea(Area area) {
        areas.remove(area);
    }
}
