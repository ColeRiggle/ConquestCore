package com.craftersconquest.gui.itembuyer;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.InventoryListener;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ItemBuyerInventory implements ConquestInventory, InventoryProvider, Listener {

    private final ConquestCore instance;
    private final SmartInventory inventory;

    public ItemBuyerInventory(ConquestCore instance) {
        this.instance = instance;


        InventoryListener<InventoryOpenEvent> listener =
                new InventoryListener<InventoryOpenEvent>(InventoryOpenEvent.class,
                        (InventoryOpenEvent event) -> Bukkit.getLogger().info("Test"));


        inventory = SmartInventory.builder()
                .id("itemBuyer")
                .provider(this)
                .size(6, 9)
                .title(ChatColor.GREEN + "Item Buyer")
                .listener(listener)
                .build();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.borderSmartInventory(inventoryContents);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
