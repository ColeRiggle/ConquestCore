package com.craftersconquest.object.shop;

import org.bukkit.inventory.ItemStack;

public class ItemStackShopItem extends ShopItem {

    private final ItemStack item;

    public ItemStackShopItem(Cost cost, ItemStack item) {
        super(cost);
        this.item = item;
    }

    @Override
    public ItemStack getDisplayItemStack(int quantity) {
        return item;
    }

    @Override
    public ItemStack getPurchaseItemStack(int quantity) {
        return item;
    }
}
