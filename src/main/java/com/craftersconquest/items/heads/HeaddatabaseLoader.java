package com.craftersconquest.items.heads;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HeaddatabaseLoader {

    private final HeadDatabaseAPI headDatabaseAPI;

    public HeaddatabaseLoader() {
        headDatabaseAPI = new HeadDatabaseAPI();
    }

    public ItemStack generateItem(String id) {
        try {
            return headDatabaseAPI.getItemHead(id);
        } catch (Exception exception){
            Bukkit.getLogger().info("Error while loading: " + id + ". " +
                    exception.toString());
        }

        return null;
    }
}
