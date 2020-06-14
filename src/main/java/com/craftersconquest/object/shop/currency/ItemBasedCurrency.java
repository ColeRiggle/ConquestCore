package com.craftersconquest.object.shop.currency;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ItemBasedCurrency extends Currency {

    private final ItemStack item;

    public ItemBasedCurrency(ItemStack item) {
        this.item = item;
    }

    protected ItemStack getItem() {
        return item;
    }

    public abstract boolean isSameItem(ItemStack comparisonItem);

    @Override
    public double getBalance(Player player) {
        Inventory playerInventory = player.getInventory();
        double amount = 0;

        for (int index = 0; index < playerInventory.getSize(); index++) {
            ItemStack item = playerInventory.getItem(index);
            if (isSameItem(item)) {
                amount += item.getAmount();
            }
        }

        return amount;
    }

    @Override
    public void withdraw(Player player, double amount) {
        int amountToRemove = (int) Math.floor(amount);

        Inventory playerInventory = player.getInventory();

        for (int index = 0; index < playerInventory.getSize(); index++) {
            ItemStack item = playerInventory.getItem(index);
            if (isSameItem(item)) {
                int itemAmount = item.getAmount();
                if (amountToRemove < itemAmount) {
                    item.setAmount(itemAmount - amountToRemove);
                    amountToRemove = 0;
                } else if (itemAmount <= amountToRemove) {
                    playerInventory.setItem(index, null);
                    amountToRemove -= itemAmount;
                }
            }
        }
    }

    @Override
    public void deposit(Player player, double amount) {
        int oldAmount = item.getAmount();
        int amountToGive = (int) Math.floor(amount);
        item.setAmount(amountToGive);

        Inventory playerInventory = player.getInventory();
        playerInventory.addItem(item);

        item.setAmount(oldAmount);
    }
}
