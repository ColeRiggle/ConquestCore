package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import static com.craftersconquest.items.conquestitem.ConquestItem.*;

public class ItemUtil {

    public static final boolean isSameType(ItemStack firstItemStack, ItemStack secondItemStack) {
        String firstId = NBTEditor.getString(firstItemStack, ID_NBT_LOCATION);
        String secondId = NBTEditor.getString(secondItemStack, ID_NBT_LOCATION);

        if (firstId != null && secondId != null) {
            return firstId == secondId;
        } else if ((firstId != null && secondId == null) || (firstId == null && secondId != null)) {
            return false;
        } else {
            return firstItemStack.equals(secondItemStack);
        }
    }

    public static final boolean isSame(ItemStack firstItemStack, ItemStack secondItemStack) {
        if (isSameType(firstItemStack, secondItemStack)) {
            String firstUUID = NBTEditor.getString(firstItemStack, UUID_NBT_LOCATION);
            String secondUUID = NBTEditor.getString(secondItemStack, UUID_NBT_LOCATION);

            if (firstUUID != null && secondUUID != null) {
                return firstUUID.equalsIgnoreCase(secondUUID);
            } else if ((firstUUID != null && secondUUID == null) || (firstUUID == null && secondUUID != null)) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public static final Category getCategory(ItemStack itemStack) {
        String category = NBTEditor.getString(CATEGORY_NBT_LOCATION);
        try {
            return Category.valueOf(category);
        } catch (IllegalArgumentException exception) {
            return Category.VANILLA;
        }
    }

    public static final Rarity getRarity(ItemStack itemStack) {
        String category = NBTEditor.getString(RARITY_NBT_LOCATION);
        try {
            return Rarity.valueOf(category);
        } catch (IllegalArgumentException exception) {
            return Rarity.NONE;
        }
    }
}
