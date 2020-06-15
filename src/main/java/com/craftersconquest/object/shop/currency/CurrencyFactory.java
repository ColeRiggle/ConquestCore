package com.craftersconquest.object.shop.currency;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Unit;
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

    public Currency getMaterialCurrency(Material material, Unit unit) {
        return new MaterialCurrency(material, unit);
    }

    public Currency getItemStackCurrency(ItemStack item, Unit unit) {
        return new ItemStackCurrency(item, unit);
    }
}
