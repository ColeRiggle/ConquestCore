package com.craftersconquest.menu;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class MenuManager {

    private final ConquestCore instance;

    private final static int MENU_LOCATION = 8;
    private final ItemStack menuItem =
            new ItemBuilder(Material.NETHER_STAR).
                    setDisplayName(ChatColor.AQUA + "Conquest Menu").
                    setLore(InventoryUtil.createLore("" + "Click to open the menu")).
                    addEnchant(Enchantment.ARROW_DAMAGE, 1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).build();

    public MenuManager(ConquestCore instance) {
        this.instance = instance;
    }

    public void givePlayerMenu(Player player) {
        player.getInventory().setItem(MENU_LOCATION, menuItem);
    }

    public ItemStack getMenuItemStack() {
        return menuItem;
    }
}
