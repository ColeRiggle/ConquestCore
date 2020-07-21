package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CraftConquestItem extends ConquestItem {

    private final String id;
    private final Category category;
    private final Rarity rarity;
    private ItemStack baseItemStack;

    public CraftConquestItem(String id, Category category, Rarity rarity, ItemStack baseItemStack) {
        this.id = id;
        this.category = category;
        this.rarity = rarity;
        this.baseItemStack = baseItemStack;
        configureBaseItemStack();
    }

    private void configureBaseItemStack() {
        baseItemStack = NBTEditor.set(baseItemStack, id, ConquestItem.ID_NBT_LOCATION);
        baseItemStack = NBTEditor.set(baseItemStack, category.toString(), ConquestItem.CATEGORY_NBT_LOCATION);
        baseItemStack = NBTEditor.set(baseItemStack, rarity.toString(), ConquestItem.RARITY_NBT_LOCATION);
        addRarityToLore();
    }

    private void addRarityToLore() {
        List<String> baseLore = baseItemStack.getItemMeta().getLore();
        List<String> modified = baseLore == null ? List.of("") : baseLore;

        if (!modified.contains(rarity.getText())) {
            modified.add("");
            modified.add(rarity.getText());
            ItemMeta modifiedItemMeta = baseItemStack.getItemMeta();
            modifiedItemMeta.setLore(modified);
            baseItemStack.setItemMeta(modifiedItemMeta);
        }
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
