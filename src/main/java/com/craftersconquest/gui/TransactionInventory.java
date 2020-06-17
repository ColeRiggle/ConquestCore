package com.craftersconquest.gui;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.shop.item.ShopItem;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TransactionInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final ShopItem item;
    private final static SlotPos ITEM_POSITION = SlotPos.of(2, 4);

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
        addNavigationButtons(inventoryContents);
        addQuantityAdjustmentButtons(inventoryContents);
        inventoryContents.set(ITEM_POSITION, ClickableItem.empty(item.getPurchaseItemStack(1)));
    }

    private void addNavigationButtons(InventoryContents inventoryContents) {
        inventoryContents.set(4, 4, ClickableItem.of(InventoryUtil.CLOSE,
                e -> e.getWhoClicked().closeInventory()));

        if (inventory.getParent().isPresent()) {
            inventoryContents.set(4, 0, ClickableItem.of(InventoryUtil.BACK,
                    e -> inventory.getParent().get().open((Player) e.getWhoClicked())));
        }

        inventoryContents.set(4, 0, ClickableItem.of(InventoryUtil.BACK,
                e -> inventory.getParent().get().open((Player) e.getWhoClicked())));
    }

    private void addQuantityAdjustmentButtons(InventoryContents inventoryContents) {
        addIncreaseButtons(inventoryContents);
        addDecreaseButtons(inventoryContents);
    }

    private void addIncreaseButtons(InventoryContents inventoryContents) {
        int maxStackSize = item.getDisplayItemStack().getType().getMaxStackSize();

        Bukkit.getLogger().info("CQ: " + getCurrentQuantity(inventoryContents));
        Bukkit.getLogger().info(inventoryContents.get(ITEM_POSITION).get().getItem().toString());

        if (getCurrentQuantity(inventoryContents) < maxStackSize) {
            Bukkit.getLogger().info("Should be adding");
            inventoryContents.set(1, 2, createIncreaseClickableItem(1));
            inventoryContents.set(2, 2, createIncreaseClickableItem(16));
            if (maxStackSize > 16) {
                inventoryContents.set(3, 2, createIncreaseClickableItem(maxStackSize));
            }
        } else {
            Bukkit.getLogger().info("Shouldn't be adding");
            inventoryContents.set(1, 2, ClickableItem.empty(new ItemStack(Material.AIR)));
            inventoryContents.set(2, 2, ClickableItem.empty(new ItemStack(Material.AIR)));
            inventoryContents.set(3, 2, ClickableItem.empty(new ItemStack(Material.AIR)));
        }
    }

    private int getCurrentQuantity(InventoryContents inventoryContents) {
        return inventoryContents.get(ITEM_POSITION).get().getItem().getAmount();
    }

    private ClickableItem createIncreaseClickableItem(int quantity) {
        return ClickableItem.of(getIncreaseButton(quantity),
                e -> onQuantityChangeClick(e, quantity));
    }

    private void onQuantityChangeClick(InventoryClickEvent event, int amount) {
        ItemStack item = event.getClickedInventory().getItem(22);
        int potentialAmount = item.getAmount() + amount;
        int maxStackSize = item.getType().getMaxStackSize();

        if (potentialAmount <= 0) {
            potentialAmount = 1;
        } else if (potentialAmount >= maxStackSize) {
            potentialAmount = maxStackSize;
        }

        item.setAmount(potentialAmount);
    }

    private ItemStack getIncreaseButton(int quantity) {
        return getButton("increase", "Increase", quantity);
    }

    private void addDecreaseButtons(InventoryContents inventoryContents) {
        if (getCurrentQuantity(inventoryContents) != 0) {
            inventoryContents.set(1, 6, createDecreaseClickableItem(1));
            inventoryContents.set(2, 6, createDecreaseClickableItem(16));

            int maxStackSize = item.getDisplayItemStack().getType().getMaxStackSize();
            if (maxStackSize > 16) {
                inventoryContents.set(3, 6, createDecreaseClickableItem(maxStackSize));
            }
        } else {
            inventoryContents.set(1, 6, null);
            inventoryContents.set(2, 6, null);
            inventoryContents.set(3, 6, null);
        }
    }

    private ClickableItem createDecreaseClickableItem(int quantity) {
        return ClickableItem.of(getDecreaseButton(quantity),
                e -> onQuantityChangeClick(e, -quantity));
    }

    private ItemStack getDecreaseButton(int quantity) {
        return getButton("decrease", "Decrease", quantity);
    }

    private ItemStack getButton(String type, String verb, int quantity) {
        ItemStack button = instance.getItemManager().getItem("gui_" + type);
        SkullMeta meta =
                InventoryUtil.createSkullMeta(button, ChatColor.GREEN +
                        verb + " quantity by " + quantity);
        button.setItemMeta(meta);
        button.setAmount(quantity);
        return button;
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        int state = inventoryContents.property("state", 0);
        inventoryContents.setProperty("state", state + 1);

        if (state % 4 == 0) {
            addQuantityAdjustmentButtons(inventoryContents);
        }
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }
}
