package com.craftersconquest.object.currency;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialCurrency extends ItemBasedCurrency {

    public MaterialCurrency(Material material) {
        super(getItemFromMaterial(material));
    }

    private static ItemStack getItemFromMaterial(Material material) {
        return new ItemStack(material);
    }

    @Override
    public boolean isSameItem(ItemStack comparisonItem) {
        // Vanilla items (no name changes) have a display name of '' that is non-null

        return comparisonItem != null &&
                comparisonItem.getType().equals(super.getItem().getType()) &&
                comparisonItem.getItemMeta() != null &&
                comparisonItem.getItemMeta().getDisplayName().equals("");
    }
}