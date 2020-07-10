package com.craftersconquest.gui.menu;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainGuildInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;

    public MainGuildInventory(ConquestCore instance, SmartInventory parent) {
        this.instance = instance;
        inventory = SmartInventory.builder()
                .id("mainGuildInventory")
                .provider(this)
                .size(4, 9)
                .title(ChatColor.DARK_GRAY + "Guild")
                .parent(parent)
                .build();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 3);

        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player.getUniqueId());
        Guild guild = conquestPlayer.getGuild();

        ItemStack item = new ItemBuilder(Material.ACACIA_BOAT).setDisplayName(guild.getName()).build();

        inventoryContents.set(1, 4, ClickableItem.empty(item));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}

