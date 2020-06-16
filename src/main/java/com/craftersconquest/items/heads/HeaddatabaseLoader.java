package com.craftersconquest.items.heads;

import com.craftersconquest.items.ConquestItem;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HeaddatabaseLoader {

    private final List<HeadsdatabaseItem> items;
    private final HeadDatabaseAPI headDatabaseAPI;

    public HeaddatabaseLoader() {
        items = new ArrayList<>();
        headDatabaseAPI = new HeadDatabaseAPI();
        addItems();
    }

    private void addItems() {
        items.add(HeadsdatabaseItem.fromIdAndHeaddatabaseId("gui_confirmation", "26419"));
        items.add(HeadsdatabaseItem.fromIdAndHeaddatabaseId("gui_insufficient", "26417"));
        items.add(HeadsdatabaseItem.fromIdAndHeaddatabaseId("gui_increase", "10209"));
        items.add(HeadsdatabaseItem.fromIdAndHeaddatabaseId("gui_decrease", "9351"));
        items.add(HeadsdatabaseItem.fromIdAndHeaddatabaseId("gui_information", "24498"));
    }

    public List<ConquestItem> generate() {
        List<ConquestItem> loadedItems = new ArrayList<>();
        for (HeadsdatabaseItem item : items) {
            loadedItems.add(ConquestItem.fromIdAndItemStack(item.getId(), generateHeadsdatabaseItem(item)));
        }
        return loadedItems;
    }

    private ItemStack generateHeadsdatabaseItem(HeadsdatabaseItem item) {
        try {
            return headDatabaseAPI.getItemHead(item.getHeaddatabaseId());
        } catch (Exception exception){
            Bukkit.getLogger().info("Error while loading: " + item.getHeaddatabaseId() + ". " +
                    exception.toString());
        }

        return null;
    }
}
