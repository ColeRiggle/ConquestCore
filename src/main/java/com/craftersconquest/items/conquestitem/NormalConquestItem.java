package com.craftersconquest.items.conquestitem;

import org.bukkit.inventory.ItemStack;

public class NormalConquestItem extends CraftConquestItem {

    public NormalConquestItem(String id, Category category, Rarity rarity, ItemStack baseItemStack) {
        super(id, category, rarity, baseItemStack);
    }

    @Override
    public ItemStack getItemStack() {
        return getBaseItemStack();
    }
}
