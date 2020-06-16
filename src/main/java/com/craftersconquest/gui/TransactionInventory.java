package com.craftersconquest.gui;

import com.craftersconquest.object.shop.item.ShopItem;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TransactionInventory implements ConquestInventory, InventoryProvider {

    private final SmartInventory inventory;
    private final ShopItem item;
    private final static SlotPos ITEM_POSITION = SlotPos.of(2, 4);

    public TransactionInventory(SmartInventory parent, ShopItem item) {
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

    private void addQuantityAdjustmentButtons(InventoryContents inventoryContents) {

    }


    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }
}
