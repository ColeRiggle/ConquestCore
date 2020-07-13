package com.craftersconquest.gui.menu.guild;

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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class GuildMembersInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final Guild guild;

    private static final int MEMBER_START_COLUMN = 3;

    public GuildMembersInventory(ConquestCore instance, SmartInventory parent, Guild guild) {
        this.instance = instance;
        this.guild = guild;
        inventory = SmartInventory.builder()
                .id("guildMembersInventory")
                .provider(this)
                .size(4, 9)
                .title(ChatColor.DARK_GRAY + "Guild Members")
                .parent(parent)
                .build();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        baseInitialization(player, inventoryContents);

        if (guild.getOwner().equals(player.getUniqueId())) {
            ownerInitialization(player, inventoryContents);
        }
    }

    private void baseInitialization(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 3);

        inventoryContents.set(1, 1, ClickableItem.empty(getInformationItemStack()));

        List<UUID> members = guild.getMembers();

        int memberColumn = MEMBER_START_COLUMN;
        for (UUID memberUUID : members) {
            final int currentColumn = memberColumn;

            Bukkit.getScheduler().runTaskAsynchronously(instance,
                    () -> {
                        ItemStack head = instance.getItemManager().getPlayerHeadCache().getPlayerHead(memberUUID);
                        ItemStack member = getMemberItemStack(memberUUID, head);
                        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(memberUUID);
                        Bukkit.getScheduler().runTask(instance,
                                () -> inventoryContents.set(1, currentColumn,
                                        ClickableItem.of(getMemberItemStack(memberUUID, head),
                                                event -> new MemberManagementInventory(instance, inventory, guild, conquestPlayer, member).getInventory().open(player))));

                    });

            memberColumn++;
        }
    }

    private void ownerInitialization(Player player, InventoryContents inventoryContents) {
        inventoryContents.set(1, MEMBER_START_COLUMN + guild.getMembers().size(),
                ClickableItem.of(getAddPlayerItemStack(), e -> openInvitePlayerInventory(player)));
    }

    private void openInvitePlayerInventory(Player player) {
        new InvitePlayerInventory(instance, inventory, guild).getInventory().open(player);
    }


    private ItemStack getInformationItemStack() {
        String displayName = ChatColor.BLUE + "Information";
        List<String> lore = InventoryUtil.createLore("",
                "As a guild leader, click on a player head",
                "to manage their guild status or promote",
                "a player to owner.");
        return new ItemBuilder(instance.getItemManager().getItem("gui_information")).
                setDisplayName(displayName).
                setLore(lore).
                build();
    }

    private ItemStack getAddPlayerItemStack() {
        String displayName = ChatColor.YELLOW + "Invite Player";
        List<String> lore = InventoryUtil.createLore("",
                "Click to invite an online player to your guild");
        return new ItemBuilder(instance.getItemManager().getItem("gui_create")).
                setDisplayName(displayName).
                setLore(lore).
                build();
    }

    private ItemStack getMemberItemStack(UUID memberUUID, ItemStack memberHead) {
        String displayName = ChatColor.YELLOW + memberHead.getItemMeta().getDisplayName();

        String position = memberUUID.equals(guild.getOwner()) ? ChatColor.RED + "Leader" : ChatColor.BLUE + "Member";
        List<String> lore = InventoryUtil.createLore("", position);

        return new ItemBuilder(memberHead).
                setDisplayName(displayName).
                setLore(lore).
                build();
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
