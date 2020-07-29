package com.craftersconquest.gui.forge;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.forges.ForgeUtil;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainForgeInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final Forge forge;
    private final SmartInventory inventory;

    public MainForgeInventory(ConquestCore instance, SmartInventory parent, Forge forge) {
        this.instance = instance;
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
        inventoryContents.set(1, 1, ClickableItem.empty(getForgeItemStack()));
        inventoryContents.set(1, 3, getUpgradeClickableItem());
    }

    private void baseInitialization(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 3);
    }

    private ItemStack getForgeItemStack() {
        ItemStack baseItemStack = ForgeUtil.getForgeConquestItem(forge).getItemStack();
        ItemMeta itemMeta= baseItemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        lore.remove(0);
        lore.remove(0);

        itemMeta.setLore(lore);
        baseItemStack.setItemMeta(itemMeta);

        return baseItemStack;
    }

    private ClickableItem getUpgradeClickableItem() {
        if (forge.getTier() == Tier.getMax()) {
            return ClickableItem.empty(getCannotUpgradeItemStack());
        } else {
            return ClickableItem.of(getUpgradeItemStack(), event -> onUpgradeClick((Player) event.getWhoClicked()));
        }
    }

    private ItemStack getUpgradeItemStack() {
        String displayName = ChatColor.GREEN + "Upgrade forge to Tier " + forge.getTier().next().toString();
        List<String> lore = InventoryUtil.createLore("", "Cost: Free");
        return new ItemBuilder(instance.getItemLoader().getItem("gui_up_arrow")).
                setDisplayName(displayName).
                setLore(lore).
                build();
    }

    private ItemStack getCannotUpgradeItemStack() {
        String displayName = ChatColor.RED + "Already at max tier";
        return new ItemBuilder(instance.getItemLoader().getItem("gui_insufficient")).
                setDisplayName(displayName).
                build();
    }

    private void onUpgradeClick(Player player) {
        forge.setTier(forge.getTier().next());
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        instance.getForgeManager().refreshHolograms(guild);
        player.getOpenInventory().close();
        new MainForgeInventory(instance, null, forge).getInventory().open(player);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
