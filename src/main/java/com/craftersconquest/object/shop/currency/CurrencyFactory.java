package com.craftersconquest.object.shop.currency;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CurrencyFactory {

    private final ConquestCore instance;

    private final Coins coins;

    public CurrencyFactory(ConquestCore instance) {
        this.instance = instance;
        coins = new Coins(instance);
    }

    public Currency getCoinsCurrency() {
        return coins;
    }

    public Currency getMaterialCurrency(Material material) {
        return new MaterialCurrency(material);
    }

    public Currency getItemStackCurrency(ItemStack item) {
        return new ItemStackCurrency(item);
    }

}
