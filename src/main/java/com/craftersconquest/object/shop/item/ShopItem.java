package com.craftersconquest.object.shop.item;

import com.craftersconquest.util.NumbersUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ShopItem {

    private final Cost cost;
    private final ItemStack item;

    public ShopItem(Cost cost, ItemStack item) {
        this.cost = cost;
        this.item = item;
    }

    public static ShopItem ofTypeWithCost(Material material, Cost cost) {
        return new MaterialShopItem(cost, material);
    }

    public static ShopItem ofItemStackWithCost(ItemStack item, Cost cost) {
        return new ItemStackShopItem(cost, item);
    }

    public Cost getCost() {
        return cost;
    }

    protected ItemStack getItemStack() {
        return item;
    }

    public abstract List<String> getBaseLore();

    public ItemStack getDisplayItemStack() {
        item.setAmount(1);
        ItemBuilder displayItemBuilder = new ItemBuilder(item);
        displayItemBuilder.setLore(getDisplayLore());

        return displayItemBuilder.build();
    }

    private List<String> getDisplayLore() {
        List<String> baseLore = getBaseLore();
        baseLore.add("");

        String unit = cost.getCurrency().getUnit(cost.getPrice());

        baseLore.add(ChatColor.GRAY + "Buy price: " + ChatColor.RED +
                NumbersUtil.formatDouble(cost.getPrice()) + " " + unit);

        baseLore.add("");

        baseLore.add(ChatColor.GRAY + "Left-click to purchase");

        return baseLore;
    }

    public ItemStack getPurchaseItemStack(int quantity) {
        item.setAmount(quantity);
        return item;
    }
}
