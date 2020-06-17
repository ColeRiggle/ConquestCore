package com.craftersconquest.gui;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.shop.item.Cost;
import com.craftersconquest.object.shop.item.ShopItem;
import com.craftersconquest.util.Formatting;
import com.craftersconquest.util.InventoryUtil;
import com.craftersconquest.util.NumbersUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TransactionInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final ShopItem item;
    private final static SlotPos ITEM_POSITION = SlotPos.of(2, 4);
    private final static SlotPos CONFIRMATION_POSITION = SlotPos.of(4, 4);

    public TransactionInventory(ConquestCore instance, SmartInventory parent, ShopItem item) {
        this.instance = instance;
        this.item = item;
        inventory = SmartInventory.builder()
                .id("transactionInventory")
                .provider(this)
                .size(5, 9)
                .title(ChatColor.BLUE + "Purchase: " + item.getName())
                .parent(parent)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        setQuantity(inventoryContents, 1);
        addDisplayItem(inventoryContents);
        addNavigationButtons(inventoryContents);
        addAppropriateQuantityAdjustmentButtons(inventoryContents);
        addConfirmationButton(player, inventoryContents);
    }

    private void addDisplayItem(InventoryContents inventoryContents) {
        inventoryContents.set(ITEM_POSITION, ClickableItem.empty(item.getPurchaseItemStack(1)));
    }

    private void addNavigationButtons(InventoryContents inventoryContents) {
        inventoryContents.set(4, 4, ClickableItem.of(InventoryUtil.CLOSE,
                e -> e.getWhoClicked().closeInventory()));

        if (inventory.getParent().isPresent()) {
            inventoryContents.set(4, 0, ClickableItem.of(InventoryUtil.BACK,
                    e -> inventory.getParent().get().open((Player) e.getWhoClicked())));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        int state = inventoryContents.property("state", 0);
        if (state % 3 == 0) {
            ItemStack transactionItem = item.getTransactionItemStack(getQuantity(inventoryContents));
            inventoryContents.set(ITEM_POSITION, ClickableItem.empty(transactionItem));
            addAppropriateQuantityAdjustmentButtons(inventoryContents);
            addConfirmationButton(player, inventoryContents);
        }
        inventoryContents.setProperty("state", state + 1);
    }

    private void addAppropriateQuantityAdjustmentButtons(InventoryContents inventoryContents) {
        addIncreaseButtons(inventoryContents);
        addDecreaseButtons(inventoryContents);
    }

    private void addIncreaseButtons(InventoryContents inventoryContents) {
        int maxQuantity = item.getMaxQuantity();

        if (getQuantity(inventoryContents) < maxQuantity) {
            inventoryContents.set(1, 6, ClickableItem.of(createIncreaseQuantityButton(1),
                    e -> changeQuantity(inventoryContents, 1)));
            inventoryContents.set(2, 6, ClickableItem.of(createIncreaseQuantityButton(maxQuantity / 4),
                    e -> changeQuantity(inventoryContents, maxQuantity / 4)));
            inventoryContents.set(3, 6, ClickableItem.of(createIncreaseQuantitySetButton(maxQuantity),
                    e -> changeQuantity(inventoryContents, 64)));
        } else {
            inventoryContents.set(1, 6, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
            inventoryContents.set(2, 6, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
            inventoryContents.set(3, 6, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
        }
    }

    private void addDecreaseButtons(InventoryContents inventoryContents) {
        int maxQuantity = item.getMaxQuantity();

        if (getQuantity(inventoryContents) != 1) {
            inventoryContents.set(1, 2, ClickableItem.of(createDecreaseQuantityButton(1),
                    e -> changeQuantity(inventoryContents, -1)));
            inventoryContents.set(2, 2, ClickableItem.of(createDecreaseQuantityButton(maxQuantity / 4),
                    e -> changeQuantity(inventoryContents, - (maxQuantity / 4))));
            inventoryContents.set(3, 2, ClickableItem.of(createDecreaseQuantitySetButton(1),
                    e -> changeQuantity(inventoryContents, -64)));
        } else {
            inventoryContents.set(1, 2, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
            inventoryContents.set(2, 2, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
            inventoryContents.set(3, 2, ClickableItem.empty(InventoryUtil.DEFAULT_ITEM));
        }
    }

    private ItemStack createIncreaseQuantityButton(int quantity) {
        ItemStack item = getIncreaseButton(quantity);
        String displayName = ChatColor.GREEN + "Increase quantity by " + quantity;
        item.setItemMeta(InventoryUtil.createSkullMeta(item, displayName));
        return item;
    }

    private ItemStack getIncreaseButton(int quantity) {
        ItemStack item = instance.getItemManager().getItem("gui_increase");
        item.setAmount(quantity);
        return item;
    }

    private ItemStack createIncreaseQuantitySetButton(int quantity) {
        ItemStack item = getIncreaseButton(quantity);
        String displayName = ChatColor.GREEN + "Change quantity to " + quantity;
        item.setItemMeta(InventoryUtil.createSkullMeta(item, displayName));
        return item;
    }

    private ItemStack createDecreaseQuantityButton(int quantity) {
        ItemStack item = getDecreaseButton(quantity);
        String displayName = ChatColor.RED + "Decrease quantity by " + quantity;
        item.setItemMeta(InventoryUtil.createSkullMeta(item, displayName));
        return item;
    }

    private ItemStack getDecreaseButton(int quantity) {
        ItemStack item = instance.getItemManager().getItem("gui_decrease");
        item.setAmount(quantity);
        return item;
    }

    private ItemStack createDecreaseQuantitySetButton(int quantity) {
        ItemStack item = getDecreaseButton(quantity);
        String displayName = ChatColor.RED + "Change quantity to " + quantity;
        item.setItemMeta(InventoryUtil.createSkullMeta(item, displayName));
        return item;
    }

    private void changeQuantity(InventoryContents inventoryContents, int amount) {
        int baseQuantity = getQuantity(inventoryContents);
        int potentialQuantity = baseQuantity + amount;

        int maxStackSize = item.getMaxQuantity();

        if (potentialQuantity < 1) {
            potentialQuantity = 1;
        } else if (potentialQuantity >= maxStackSize) {
            potentialQuantity = maxStackSize;
        }

        setQuantity(inventoryContents, potentialQuantity);
    }

    private void setQuantity(InventoryContents inventoryContents, int amount) {
        inventoryContents.setProperty("quantity", amount);
    }

    private int getQuantity(InventoryContents inventoryContents) {
        return inventoryContents.property("quantity");
    }

    private void addConfirmationButton(Player player, InventoryContents inventoryContents) {
        if (canAffordTransaction(player, inventoryContents)) {
            inventoryContents.set(CONFIRMATION_POSITION,
                    ClickableItem.of(getConfirmationButton(), e -> completeTransaction(player, inventoryContents)));
        } else {
            inventoryContents.set(CONFIRMATION_POSITION, ClickableItem.empty(getInsufficientFundsButton()));
        }
    }

    private boolean canAffordTransaction(Player player, InventoryContents inventoryContents) {
        Cost cost = item.getCost();

        double balance = cost.getCurrency().getBalance(player);
        double total = cost.getPrice() * getQuantity(inventoryContents);

        return balance >= total;
    }

    private ItemStack getInsufficientFundsButton() {
        ItemStack item = instance.getItemManager().getItem("gui_insufficient");
        SkullMeta meta = InventoryUtil.createSkullMeta(item, ChatColor.RED + "Insufficient funds");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getConfirmationButton() {
        ItemStack item = instance.getItemManager().getItem("gui_confirmation");
        SkullMeta meta = InventoryUtil.createSkullMeta(item, ChatColor.GREEN + "Confirm purchase");
        item.setItemMeta(meta);
        return item;
    }

    private void completeTransaction(Player player, InventoryContents inventoryContents) {
        int quantity = getQuantity(inventoryContents);
        ItemStack purchasedItem = item.getPurchaseItemStack(quantity);
        player.getInventory().addItem(purchasedItem);

        double totalCost = quantity * item.getCost().getPrice();
        item.getCost().getCurrency().withdraw(player, totalCost);
        Messaging.sendPlayerSpecificMessage(player, "Successfully completed purchase. " +
                Messaging.messageEconomicAssetColor + NumbersUtil.formatDouble(totalCost) + " " +
                item.getCost().getCurrency().getUnit(totalCost) + Messaging.baseColorPlayerSpecific +
                Formatting.getPlural(totalCost, " was", " were") + " taken.");
        player.getOpenInventory().close();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }
}
