package com.craftersconquest.gui.menu.guild;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.guild.Stockpile;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MainGuildInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final Guild guild;

    public MainGuildInventory(ConquestCore instance, SmartInventory parent, Guild guild) {
        this.instance = instance;
        this.guild = guild;
        inventory = SmartInventory.builder()
                .id("mainGuildInventory")
                .provider(this)
                .size(4, 9)
                .title(ChatColor.DARK_GRAY + "Guild: " + guild.getName())
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

        inventoryContents.set(1, 1, ClickableItem.empty(getGuildInfoItemStack()));
        inventoryContents.set(1, 3, ClickableItem.of(getMembersItemStack(),
                (event) -> openMembersInventory(player, guild)));
        inventoryContents.set(1, 5, ClickableItem.empty(getUpgradesItemStack()));
        inventoryContents.set(1, 7, ClickableItem.empty(getStockpileItemStack()));
        inventoryContents.set(2, 7, ClickableItem.of(getStockpileItemStack(),
                (event) -> instance.getTeleporter().teleportToGuild(player)));
        inventoryContents.set(3, 8, ClickableItem.empty(getLeaveItemStack()));
    }

    private ItemStack getGuildInfoItemStack() {
        String displayName = ChatColor.YELLOW + guild.getName();
        List<String> lore = InventoryUtil.createLore("", ChatColor.RED + "Elo: " + 1000);
        return new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName(displayName).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
    }

    private ItemStack getMembersItemStack() {
        String displayName = ChatColor.YELLOW + "Members";
        List<String> lore = InventoryUtil.createLore("", "Click to view all members of the guild.");
        return new ItemBuilder(Material.PLAYER_HEAD).setDisplayName(displayName).setLore(lore).build();
    }

    private ItemStack getUpgradesItemStack() {
        String displayName = ChatColor.YELLOW + "Upgrades";
        List<String> lore = InventoryUtil.createLore("", "Click to view current upgrades.");
        return new ItemBuilder(Material.ANVIL).setDisplayName(displayName).setLore(lore).build();
    }

    private ItemStack getStockpileItemStack() {
        String displayName = ChatColor.YELLOW + "Stockpile";

        Stockpile stockpile = guild.getStockpile();

        String grain = stockpile.getGrain() + " / " + stockpile.getResourceCapacity() + " " + Type.GRAIN.getDisplayName();
        String metal = stockpile.getMetal() + " / " + stockpile.getResourceCapacity() + " " + Type.METAL.getDisplayName();
        String crystal = stockpile.getCrystal() + " / " + stockpile.getResourceCapacity() + " " + Type.CRYSTAL.getDisplayName();
        String essence = stockpile.getEssence() + " / " + stockpile.getEssenceCapacity() + " " + Type.ESSENCE.getDisplayName();

        List<String> lore = InventoryUtil.createLore("", grain, metal, crystal, essence, ChatColor.AQUA + "Tier I");
        return new ItemBuilder(Material.CHEST).setDisplayName(displayName).setLore(lore).build();
    }

    private ItemStack getLeaveItemStack() {
        String displayName = ChatColor.RED + "Leave Guild";
        List<String> lore = InventoryUtil.createLore("", "Click to leave the guild.");
        return new ItemBuilder(Material.OAK_TRAPDOOR).setDisplayName(displayName).setLore(lore).build();
    }

    private void openMembersInventory(Player player, Guild guild) {
        new GuildMembersInventory(instance, inventory, guild).getInventory().open(player);
    }

    private void onLeaveClick(Player player) {
        if (guild.getOwner().equals(player.getUniqueId()) && guild.getMembers().size() != 1) {
            player.getOpenInventory().close();
            Messaging.sendErrorMessage(player, "You must promote another member of your guild before leaving.");
        } else {
            ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
            conquestPlayer.setGuild(null);
            guild.removeMember(player.getUniqueId());
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}

