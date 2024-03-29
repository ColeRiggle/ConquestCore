package com.craftersconquest.gui.menu;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.gui.menu.guild.CreateGuildInventory;
import com.craftersconquest.gui.menu.guild.MainGuildInventory;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class MenuInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final MenuIconGenerator menuIconGenerator;

    public MenuInventory(ConquestCore instance) {
        this.instance = instance;
        inventory = SmartInventory.builder()
                .id("conquestMenu")
                .provider(this)
                .size(6, 9)
                .title("Conquest Menu")
                .build();
        menuIconGenerator = new MenuIconGenerator();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);

        inventoryContents.set(1, 4, ClickableItem.empty(menuIconGenerator.getProfileIcon(player)));

        inventoryContents.set(2, 2, ClickableItem.of(menuIconGenerator.getSkillsIcon(),
                e -> openSkillsInventory(player)));
        inventoryContents.set(2, 3, ClickableItem.empty(menuIconGenerator.getQuestsIcon()));
        inventoryContents.set(2, 4, ClickableItem.of(menuIconGenerator.getGuildIcon(),
                e-> openGuildInventory(player)));
        inventoryContents.set(2, 5, ClickableItem.empty(menuIconGenerator.getStreaksIcon()));
        inventoryContents.set(2, 6, ClickableItem.empty(menuIconGenerator.getHelpIcon()));
        inventoryContents.set(3, 4, ClickableItem.of(menuIconGenerator.getWorkbenchIcon(),
                e -> player.openWorkbench(null, true)));

        inventoryContents.set(5, 4, ClickableItem.of(InventoryUtil.CLOSE,
                e -> e.getWhoClicked().closeInventory()));
    }

    private void openSkillsInventory(Player player) {
        (new SkillsInventory(instance, inventory)).getInventory().open(player);
    }

    private void openGuildInventory(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player.getUniqueId());
        Guild guild = conquestPlayer.getGuild();

        if (guild == null) {
            new CreateGuildInventory(instance, inventory).getInventory().open(player);
        } else {
            new MainGuildInventory(instance, inventory, guild).getInventory().open(player);
        }
//        new CreateGuildInventory(instance, inventory).getInventory().open(player);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
