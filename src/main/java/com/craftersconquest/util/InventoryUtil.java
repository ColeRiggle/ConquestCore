package com.craftersconquest.util;

import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryUtil {

    public static final ItemStack DEFAULT_ITEM =
            new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).
                    setDisplayName(ChatColor.DARK_GRAY + "Crafter's Conquest").
                    build();

    public static final ItemStack LAST_PAGE_BUTTON =
            new ItemBuilder(Material.ARROW, 1).
            setDisplayName(ChatColor.RED + "Last page").
            build();

    public static final ItemStack NEXT_PAGE_BUTTON =
            new ItemBuilder(Material.ARROW, 1).
                    setDisplayName(ChatColor.RED + "Next page").
                    build();

    public static final ItemStack CLOSE =
            new ItemBuilder(Material.BARRIER, 1).
                    setDisplayName(ChatColor.RED + "Close").
                    build();

    public static final ItemStack BACK =
            new ItemBuilder(Material.OAK_DOOR, 1).
                    setDisplayName(ChatColor.RED + "Back").
                    build();

    public static void fillSmartInventory(InventoryContents inventoryContents) {
        inventoryContents.fill(ClickableItem.empty(DEFAULT_ITEM));
    }

    public static ArrayList<String> createLore(String... lore) {
        ArrayList<String> finalLore = new ArrayList<>();
        for (String line : lore) {
            finalLore.add(ChatColor.GRAY + line);
        }
        return finalLore;
    }

    public static SkullMeta createSkullMeta(ItemStack item, String displayName, String... lore) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));

        return meta;
    }
}
