package com.craftersconquest.gui.menu.guild;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.input.SelectionRequest;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MemberManagementInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final Guild guild;
    private final ConquestPlayer member;
    private final ItemStack playerHead;
    private final String memberName;

    private final SmartInventory inventory;

    public MemberManagementInventory(ConquestCore instance, SmartInventory parent, Guild guild, ConquestPlayer member, ItemStack playerHead) {
        this.instance = instance;
        this.guild = guild;
        this.member = member;
        this.playerHead = playerHead;
        this.memberName = ChatColor.stripColor(playerHead.getItemMeta().getDisplayName());
        String inventoryTitle = ChatColor.DARK_GRAY + "Member: " + memberName;
        inventory = SmartInventory.builder()
                .id("memberManagementInventory")
                .provider(this)
                .size(4, 9)
                .title(inventoryTitle)
                .parent(parent)
                .build();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        baseInitialization(inventoryContents);

        inventoryContents.set(1, 2, ClickableItem.empty(playerHead));
        inventoryContents.set(1, 4, ClickableItem.of(getPromoteItemStack(), e -> onPromote(player)));
        inventoryContents.set(1, 6, ClickableItem.of(getKickItemStack(), e -> onKick(player)));

    }

    private void baseInitialization(InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 3);
    }

    private ItemStack getPromoteItemStack() {
        ItemStack baseItemStack = instance.getItemLoader().getItem("gui_up_arrow");
        String displayName = ChatColor.YELLOW + "Promote to leader";
        List<String> lore = InventoryUtil.createLore("", "This will transfer ownership of", "the guild to this player.");
        return new ItemBuilder(baseItemStack).setDisplayName(displayName).setLore(lore).build();
    }

    private ItemStack getKickItemStack() {
        String displayName = ChatColor.RED + "Kick";
        List<String> lore = InventoryUtil.createLore("", "This will remove this player", "from the guild.");
        return new ItemBuilder(Material.HOPPER).setDisplayName(displayName).setLore(lore).build();
    }

    private void onPromote(Player player) {
        SelectionRequest selectionRequest = new SelectionRequest("Would you like to promote " + memberName + " to leader?",
                () -> onPromoteAccept(player, member),
                () -> onPromoteDecline(player));
        instance.getInputManager().addPendingSelectionRequest(player, selectionRequest);
        player.getOpenInventory().close();
    }

    private void onPromoteAccept(Player player, ConquestPlayer newOwner) {
        Messaging.sendPlayerSpecificMessage(player, "Leadership successfully transferred.");

        UUID newOwnerUUID = newOwner.getUUID();
        guild.setOwner(newOwnerUUID);

        Player owner = Bukkit.getPlayer(newOwnerUUID);
        if (owner != null) {
            Messaging.sendPlayerSpecificMessage(owner, "You are now the leader of your guild.");
        }
    }

    private void onPromoteDecline(Player player) {
        Messaging.sendPlayerSpecificMessage(player, "Leadership retained.");
    }

    private void onKick(Player player) {
        SelectionRequest selectionRequest = new SelectionRequest("Would you like to kick " + memberName + "?",
                () -> onKickAccept(player, member),
                () -> onKickDecline(player));
        instance.getInputManager().addPendingSelectionRequest(player, selectionRequest);
        player.getOpenInventory().close();
    }

    private void onKickAccept(Player player, ConquestPlayer member) {
        Messaging.sendPlayerSpecificMessage(player, "Kick successful");
        member.setGuild(null);
        guild.removeMember(member.getUUID());

        Player kickedPlayer = Bukkit.getPlayer(member.getUUID());
        if (kickedPlayer != null) {
            Messaging.sendPlayerSpecificMessage(kickedPlayer, "You were kicked from your guild.");
        }
    }

    private void onKickDecline(Player player) {
        Messaging.sendPlayerSpecificMessage(player, "Kick cancelled.");
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
