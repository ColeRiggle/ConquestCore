package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import static com.craftersconquest.items.conquestitem.ConquestItem.*;

public class ItemUtil {

    public static final Category getCategory(ItemStack itemStack) {
        String categoryString = NBTEditor.getString(itemStack, CATEGORY_NBT_LOCATION);

        if (categoryString == null) {
            return Category.VANILLA;
        }

        try {
            Category category = Category.valueOf(categoryString);
            return category;
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
