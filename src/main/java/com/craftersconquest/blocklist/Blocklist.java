package com.craftersconquest.blocklist;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.StateFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Blocklist {

    private final ConquestCore instance;
    private final BlocklistMaterialTracker tracker;

    public Blocklist(ConquestCore instance) {
        this.instance = instance;
        tracker = new BlocklistMaterialTracker();
    }

    public boolean contains(Location location) {
        return instance.getDataSource().blocklistContains(location);
    }

    public void addIfNecessary(Material material, Location location) {
        if (isTracked(material)) {
            if (instance.getRegionManager().getFlagValueAt(Flags.BLOCKLIST, location) == StateFlag.State.ALLOW) {
                Bukkit.getServer().getScheduler().runTaskAsynchronously(instance, () -> {
                    if (isTracked(material) && !location.getWorld().getName().equals("Badlands")) {
                        add(location);
                    }
                });
            }
        }
    }

    public boolean isTracked(Material material) {
        return tracker.contains(material);
    }

    private void add(Location location) {
        instance.getDataSource().addToBlocklist(location);
    }

    public void remove(Location location) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(instance,
                () -> instance.getDataSource().removeFromBlocklist(location));
    }
}
