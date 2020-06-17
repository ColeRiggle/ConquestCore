package com.craftersconquest.object.shop.item;

import com.craftersconquest.util.NumbersUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ShopItem {

    private final String name;
    private final Cost cost;
    private final ItemStack item;
    private final int maxQuantity;

    public ShopItem(String name, Cost cost, ItemStack item) {
        this(name ,cost, item, item.getType().getMaxStackSize());
    }

    public ShopItem(String name, Cost cost, ItemStack item, int maxQuantity) {
        this.name = name;
        this.cost = cost;
        this.item = item;
        this.maxQuantity = maxQuantity;
    }

    public static ShopItem ofTypeWithCostAndMaxQuantity(String name, Material material, Cost cost, int maxQuantity) {
        return new MaterialShopItem(name, cost, material, maxQuantity);
    }

    public static ShopItem ofTypeWithCost(String name, Material material, Cost cost) {
        return new MaterialShopItem(name, cost, material);
    }

    public static ShopItem ofItemStackWithCostAndMaxQuantity(String name, ItemStack item, Cost cost, int maxQuantity) {
        return new ItemStackShopItem(name, cost, item, maxQuantity);
    }

    public static ShopItem ofItemStackWithCost(String name, ItemStack item, Cost cost) {
        return new ItemStackShopItem(name, cost, item);
    }

    public String getName() {
        return name;
    }

    public Cost getCost() {
        return cost;
    }

    public int getMaxQuantity() {
        return maxQuantity;
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

        baseLore.add(ChatColor.GRAY + "Buy price: " + ChatColor.GOLD +
                NumbersUtil.formatDouble(cost.getPrice()) + " " + unit);

        baseLore.add("");

        baseLore.add(ChatColor.GRAY + "Left-click to purchase");

        return baseLore;
    }

    public ItemStack getTransactionItemStack(int quantity) {
        item.setAmount(quantity);
        ItemBuilder transactionItemBuilder = new ItemBuilder(item);
        transactionItemBuilder.setLore(getTransactionLore(quantity));

        return transactionItemBuilder.build();
    }

    private List<String> getTransactionLore(int quantity) {
        List<String> baseLore = getBaseLore();
        baseLore.add("");

        String unit = cost.getCurrency().getUnit(cost.getPrice());

        baseLore.add(ChatColor.GRAY + "Unit price: " + ChatColor.GOLD +
                NumbersUtil.formatDouble(cost.getPrice()) + " " + unit);

        baseLore.add(ChatColor.GRAY + "Total price: " +
                ChatColor.GOLD + NumbersUtil.formatDouble(cost.getPrice() * quantity) + " " + unit);

        return baseLore;
    }

    public ItemStack getPurchaseItemStack(int quantity) {
        item.setAmount(quantity);
        return item;
    }
}
