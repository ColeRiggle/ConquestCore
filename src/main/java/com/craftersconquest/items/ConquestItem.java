package com.craftersconquest.items;

import org.bukkit.inventory.ItemStack;

public class ConquestItem {

    private final String id;
    private final ItemStack item;

    private ConquestItem(String id, ItemStack item) {
        this.id = id;
        this.item = item;
    }

    public static ConquestItem fromIdAndItemStack(String id, ItemStack item) {
        return new ConquestItem(id, item);
    }

    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }
}
