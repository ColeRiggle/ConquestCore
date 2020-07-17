package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

public abstract class CraftConquestItem extends ConquestItem {

    private final String id;
    private final Category category;
    private final Rarity rarity;
    private final ItemStack baseItemStack;

    public CraftConquestItem(String id, Category category, Rarity rarity, ItemStack baseItemStack) {
        this.id = id;
        this.category = category;
        this.rarity = rarity;
        this.baseItemStack = baseItemStack;
        configureBaseItemStack();
    }

    private void configureBaseItemStack() {
        NBTEditor.set(baseItemStack, ConquestItem.ID_NBT_LOCATION, id);
        NBTEditor.set(baseItemStack, ConquestItem.CATEGORY_NBT_LOCATION, category.toString());
        NBTEditor.set(baseItemStack, ConquestItem.RARITY_NBT_LOCATION, rarity.toString());
    }

    public ItemStack getBaseItemStack() {
        return baseItemStack;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }
}
