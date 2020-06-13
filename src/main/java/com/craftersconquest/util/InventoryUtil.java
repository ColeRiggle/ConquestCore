package com.craftersconquest.util;

import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static final ItemStack DEFAULT_ITEM =
            new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).
                    setDisplayName(ChatColor.DARK_GRAY + "Crafter's Conquest").
                    build();

    public static void fillSmartInventory(InventoryContents inventoryContents) {
        inventoryContents.fill(ClickableItem.empty(DEFAULT_ITEM));
    }
}
