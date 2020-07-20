package com.craftersconquest.items.conquestitem;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ExistingConquestItem extends CraftConquestItem {

    private ExistingConquestItem(String id, Category category, Rarity rarity, ItemStack itemStack) {
        super(id, category, rarity, itemStack);
    }

    public static Optional<ConquestItem> fromItemStack(ItemStack itemStack) {
        Optional<Item> potentialItem = Item.fromItemStack(itemStack);
        if (potentialItem.isPresent()) {
            ConquestItem baseItem = potentialItem.get().getConquestItem();
            return Optional.of(new ExistingConquestItem(baseItem.getId(), baseItem.getCategory(), baseItem.getRarity(), itemStack));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ItemStack getItemStack() {
        return getBaseItemStack();
    }
}
