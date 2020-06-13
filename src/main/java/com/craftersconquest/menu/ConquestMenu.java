package com.craftersconquest.menu;

import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConquestMenu implements InventoryProvider {

    public final static SmartInventory INVENTORY = SmartInventory.builder()
            .id("conquestMeny")
            .provider(new ConquestMenu())
            .size(3, 9)
            .title(ChatColor.RED + "Conquest Menu")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
