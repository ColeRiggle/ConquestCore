package com.craftersconquest.gui.forge;

import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class MainForgeInventory implements ConquestInventory, InventoryProvider {

    private final Forge forge;
    private final SmartInventory inventory;

    public MainForgeInventory(SmartInventory parent, Forge forge) {
        this.forge = forge;
        inventory = SmartInventory.builder()
                .id("forge")
                .provider(this)
                .size(4, 9)
                .title(forge.getType().getDisplayName())
                .parent(parent)
                .build();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        baseInitialization(player ,inventoryContents);
    }

    private void baseInitialization(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 3);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
