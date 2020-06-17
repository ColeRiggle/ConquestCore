package com.craftersconquest.items;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.items.heads.HeaddatabaseLoader;
import com.craftersconquest.object.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager implements Component {

    private final ConquestCore instance;
    private final HeaddatabaseLoader headsLoader;
    private final ItemConverter converter;
    private final HashMap<String, ItemStack> items;
    private final ItemCommandExecutor commandExecutor;

    private final static List<String> REGULAR_ITEMS =
            new ArrayList<>(List.of(
                    "gui_confirmation",
                    "gui_insufficient",
                    "gui_increase",
                    "gui_decrease",
                    "gui_information"));

    private static final Map<String,String> HEAD_DATABASE_ITEMS = Map.ofEntries(
            Map.entry("gui_confirmation", "26419"),
            Map.entry("gui_insufficient", "26417"),
            Map.entry("gui_increase", "10209"),
            Map.entry("gui_decrease", "9351"),
            Map.entry("gui_information", "24498")
    );

    public ItemManager(ConquestCore instance) {
        this.instance = instance;
        headsLoader = new HeaddatabaseLoader();
        converter = new ItemConverter();
        items = new HashMap<>();
        commandExecutor = new ItemCommandExecutor(instance);
    }

    public ItemStack getItem(String id) {
        return items.get(id).clone();
    }

    private void load() {
        for (String id : REGULAR_ITEMS) {
            items.put(id, loadItem(id));
        }
    }

    private ItemStack loadItem(String id) {
        try {
            return converter.fromBase64(instance.getDataSource().getStoredItem(id));
        } catch (IOException ioException) {
            Bukkit.getLogger().info("Error occurred while loading item: " + id + ": " + ioException.toString());
        }

        return null;
    }

    public void generate() {
        Bukkit.getLogger().info("Generating items...");
        items.clear();
        generateHeaddatabaseItems();
        saveItems();
        Bukkit.getLogger().info("Items generated.");
    }

    private void generateHeaddatabaseItems() {
        for (Map.Entry<String, String> entry: HEAD_DATABASE_ITEMS.entrySet()) {
            String id = entry.getKey();
            String headdatabaseId = entry.getValue();
            ItemStack headItem = headsLoader.generateItem(headdatabaseId);
            items.put(id, headItem);
        }
    }

    private void saveItems() {
        for (Map.Entry<String, ItemStack> entry: items.entrySet()) {
            String id = entry.getKey();
            ItemStack item = entry.getValue();
            saveItem(id, item);
        }
    }

    private void saveItem(String id, ItemStack item) {
        try {
            String data = converter.toBase64(item);
            instance.getDataSource().addStoredItem(id, data);
        } catch (IOException ioException) {
            Bukkit.getLogger().info("Error occurred while saving item: " + id + ": " + ioException.toString());
        }
    }

    @Override
    public void onEnable() {
        instance.getCommand("ccadmingenerate").setExecutor(commandExecutor);
        load();
    }

    @Override
    public void onDisable() {

    }
}
