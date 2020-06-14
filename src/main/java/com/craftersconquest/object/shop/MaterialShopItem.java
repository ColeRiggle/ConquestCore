package com.craftersconquest.object.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialShopItem extends ShopItem {

    private final Material material;

    public MaterialShopItem(Cost cost, Material material) {
        super(cost);
        this.material = material;
    }

    @Override
    public ItemStack getDisplayItemStack(int quantity) {
        return null;
    }

    @Override
    public ItemStack getPurchaseItemStack(int quantity) {
        ItemStack purchasedItemStack = new ItemStack(material);
        purchasedItemStack.setAmount(quantity);
        return purchasedItemStack;
    }
}
