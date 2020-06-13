package com.craftersconquest.object;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private final ItemStack item;
    private final boolean custom;
    private final Cost cost;

    public ShopItem(ItemStack item, boolean custom, Cost cost) {
        this.item = item;
        this.custom = custom;
        this.cost = cost;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isCustom() {
        return custom;
    }

    public Cost getCost() {
        return cost;
    }
}
