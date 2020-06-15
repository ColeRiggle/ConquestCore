package com.craftersconquest.object.shop.currency;

import com.craftersconquest.object.Unit;
import org.bukkit.inventory.ItemStack;

public class ItemStackCurrency extends ItemBasedCurrency {

    public ItemStackCurrency(ItemStack item, Unit unit) {
        super(item, unit);
    }

    @Override
    public boolean isSameItem(ItemStack comparisonItem) {
        return comparisonItem != null &&
                comparisonItem.getType().equals(super.getItem().getType()) &&
                comparisonItem.getItemMeta() != null &&
                comparisonItem.getItemMeta().getDisplayName().contains(super.getItem().getItemMeta().getDisplayName());
    }
}
