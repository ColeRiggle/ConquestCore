package com.craftersconquest.object.shop.item;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackShopItem extends ShopItem {

    public ItemStackShopItem(Cost cost, ItemStack item) {
        super(cost, item);
    }

    @Override
    public List<String> getBaseLore() {
        if (getItemStack().getItemMeta() != null && getItemStack().getItemMeta().getLore() != null) {
            return getItemStack().getItemMeta().getLore();
        }

        return new ArrayList<>();
    }
}
