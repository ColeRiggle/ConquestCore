package com.craftersconquest.object.shop.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MaterialShopItem extends ShopItem {

    public MaterialShopItem(String name, Cost cost, Material material) {
        super(name, cost, new ItemStack(material));
    }

    @Override
    public List<String> getBaseLore() {
        return new ArrayList<>();
    }
}
