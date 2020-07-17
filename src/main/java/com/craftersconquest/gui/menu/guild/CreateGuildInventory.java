package com.craftersconquest.gui.menu.guild;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.util.InventoryUtil;
import com.craftersconquest.util.StringUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGuildInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;

    public CreateGuildInventory(ConquestCore instance, SmartInventory parent) {
        this.instance = instance;
        inventory = SmartInventory.builder()
                .id("createGuildInventory")
                .provider(this)
                .size(4, 9)
                .title(ChatColor.DARK_GRAY + "Create or join a Guild")
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

        ItemStack information = getInformationItemStack();
        ItemStack create = getCreateItemStack();
        ItemStack join = getJoinItemStack();

        inventoryContents.set(1, 2, ClickableItem.empty(information));
        inventoryContents.set(1, 4, ClickableItem.of(create, e -> onCreateClick(player)));
        inventoryContents.set(1, 6, ClickableItem.empty(join));
    }

    private void onCreateClick(Player player) {
        Messaging.sendPlayerSpecificMessage(player, "Please type in the name of the guild you'd like to create.");
        player.closeInventory();
        instance.getInputManager().addPendingInputRequest(player, message -> attemptGuildCreation(player, message));
    }

    private void attemptGuildCreation(Player player, String name) {
        if (name.contains(" ")) {
            Messaging.sendErrorMessage(player, "Guild names must be one word.");
        } else if (!StringUtil.isAlphaNumeric(name)) {
            Messaging.sendErrorMessage(player, "Guild names can only include numbers and letters.");
        } else if (instance.getGuildManager().getGuild(name) != null) {
            Messaging.sendErrorMessage(player, "The name " + name + " is already taken.");
        } else if (name.length() > Settings.MAX_GUILD_NAME_LENGTH) {
            Messaging.sendErrorMessage(player, "Guild names cannot exceed " +
                    Settings.MAX_GUILD_NAME_LENGTH + " characters.");
        } else {
            Messaging.sendPlayerSpecificMessage(player, "Creating guild: " + name + ".");

            List<UUID> memberUUIDs = new ArrayList<>();
            memberUUIDs.add(player.getUniqueId());

            Guild guild = Guild.builder().name(name).formattedName(name).ownerUUID(player.getUniqueId()).memberUUIDs(memberUUIDs).build();

            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                instance.getGuildManager().createWorld(guild);

                Bukkit.getScheduler().runTask(instance, () -> {
                    ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player.getUniqueId());
                    conquestPlayer.setGuild(guild);
                    Messaging.sendPlayerSpecificMessage(player, "Teleporting to your guild...");
                    instance.getGuildManager().teleportPlayerToGuild(player, guild);
                });

            });
        }
    }

    private ItemStack getInformationItemStack() {
        ItemStack item = instance.getItemLoader().getItem("gui_information");
        return new ItemBuilder(item).
                setDisplayName(ChatColor.BLUE + "Information").
                setLore(InventoryUtil.createLore("",
                    "Create or join a guild",
                    "with up to 4 players and",
                    "compete for server dominance.")).
                build();
    }

    private ItemStack getCreateItemStack() {
        ItemStack item = instance.getItemLoader().getItem("gui_create");
        return new ItemBuilder(item).
                setDisplayName(ChatColor.RED + "Create a guild").
                setLore(InventoryUtil.createLore("",
                        "Create a new guild, invite players",
                        "and rise to the top.")).
                build();
    }

    private ItemStack getJoinItemStack() {
        ItemStack item = instance.getItemLoader().getItem("gui_confirmation");
        return new ItemBuilder(item).
                setDisplayName(ChatColor.YELLOW + "Join a guild").
                setLore(InventoryUtil.createLore("",
                        "To join an existing guild, have the",
                        "guild owner send you an invite.")).
                build();
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
