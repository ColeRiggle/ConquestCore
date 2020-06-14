package com.craftersconquest.object.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ShopItem {

    private final Cost cost;

    public ShopItem(Cost cost) {
        this.cost = cost;
    }

    public static ShopItem ofTypeWithCost(Material material, Cost cost) {
        return new MaterialShopItem(cost, material);
    }

    public static ShopItem ofItemStackWithCost(ItemStack item, Cost cost) {
        return new ItemStackShopItem(cost, item);
    }

    public Cost getCost() {
        return cost;
    }

    public abstract ItemStack getDisplayItemStack(int quantity);

    public abstract ItemStack getPurchaseItemStack(int quantity);
}
