package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class UniqueConquestItem extends CraftConquestItem {

    public UniqueConquestItem(String id, Category category, Rarity rarity, ItemStack baseItemStack) {
        super(id, category, rarity, baseItemStack);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack uniqueItemStack = getBaseItemStack().clone();
        configureUniqueItemStack(uniqueItemStack);
        return uniqueItemStack;
    }

    private void configureUniqueItemStack(ItemStack itemStack) {
        String itemUUID = UUID.randomUUID().toString();
//        int halfWay = itemUUID.length() / 2;
//        String firstHalf = itemUUID.substring(0, halfWay);
//        String secondHalf = itemUUID.substring(halfWay);
        NBTEditor.set(itemStack, ConquestItem.UUID_NBT_LOCATION, itemUUID);
//        NBTEditor.set(itemStack, ConquestItem.UUID_2_NBT_LOCATION, secondHalf);
    }
}
