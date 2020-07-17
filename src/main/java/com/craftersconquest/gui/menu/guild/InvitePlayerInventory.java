package com.craftersconquest.gui.menu.guild;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
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
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvitePlayerInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final Guild guild;

    public InvitePlayerInventory(ConquestCore instance, SmartInventory parent, Guild guild) {
        this.instance = instance;
        this.guild = guild;
        inventory = SmartInventory.builder()
                .id("guildInvitationInventory")
                .provider(this)
                .size(6, 9)
                .title(ChatColor.DARK_GRAY + "Invite players to guild")
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
        Pagination pagination = inventoryContents.pagination();

        List<Player> applicablePlayers = getOnlinePlayersWithoutAGuild();

        fillWithPlayers(pagination, applicablePlayers);
        addNavigationButtons(player, pagination, inventoryContents, applicablePlayers.size());
        configurePagination(pagination, inventoryContents);

        inventoryContents.set(0, 4, ClickableItem.of(getRefreshItemStack(),
                e -> onRefreshClick(player, inventoryContents)));
    }

    private void fillWithPlayers(Pagination pagination, List<Player> players) {

        ClickableItem[] clickableItems = new ClickableItem[players.size()];

        for (int index = 0; index < clickableItems.length; index++) {
            clickableItems[index] = createClickableItemFromPlayer(players.get(index));
        }

        pagination.setItems(clickableItems);
    }

    @SuppressWarnings("ConstantConditions")
    private ClickableItem createClickableItemFromPlayer(Player player) {
        ItemStack playerHead = instance.getItemLoader().getPlayerHeadCache().getPlayerHead(player.getUniqueId());
        String displayName = ChatColor.YELLOW + playerHead.getItemMeta().getDisplayName();
        List<String> lore = InventoryUtil.createLore("", ChatColor.RED + "No guild", "",
                ChatColor.GREEN + "Click to invite this player");
        ItemStack modifiedPlayerHead = new ItemBuilder(playerHead).setDisplayName(displayName).setLore(lore).build();

        return ClickableItem.of(modifiedPlayerHead, event -> attemptPlayerInvite((Player) event.getWhoClicked(), player));
    }

    private void attemptPlayerInvite(Player sender, Player recipient) {
        if (!recipient.isOnline()) {
            Messaging.sendErrorMessage(sender, "That player is no longer online.");

            return;
        } else {
            ConquestPlayer targetPlayer = instance.getCacheManager().getConquestPlayer(recipient);
            ConquestPlayer sendingPlayer = instance.getCacheManager().getConquestPlayer(sender);
            Guild guild = sendingPlayer.getGuild();

            if (targetPlayer.getGuild() != null) {
                Messaging.sendErrorMessage(sender, "That player is already a member of a guild.");
            } else if (guild == null) {
                Messaging.sendErrorMessage(sender, "You are not a member of a guild.");
            } else if (guild.getMembers().size() >= Settings.MAX_GUILD_MEMBERS) {
                Messaging.sendErrorMessage(sender, "Your guild is already full.");
            } else {
                sendGuildInvitation(sender, recipient);
            }
        }
    }

    private void sendGuildInvitation(Player sender, Player recipient) {
        String prompt = sender.getName() + " invited you to join " + guild.getName() + ".";
        SelectionRequest request =
                new SelectionRequest(prompt, () -> onAccept(sender, recipient), () -> onDecline(sender, recipient));
        instance.getInputManager().addPendingSelectionRequest(recipient, request);
    }

    private void onAccept(Player sender, Player recipient) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(recipient);

        if (guild.getMembers().size() >= Settings.MAX_GUILD_MEMBERS) {
            Messaging.sendErrorMessage(recipient, "The guild is already full.");
        } else if (conquestPlayer.getGuild() != null) {
            Messaging.sendErrorMessage(recipient, "You are already a member of a guild.");
        } else {
            guild.addMember(recipient.getUniqueId());
            conquestPlayer.setGuild(guild);
            Messaging.sendPlayerSpecificMessage(recipient, "You joined " + guild.getName() + ".");
            Messaging.sendPlayerSpecificMessage(sender, recipient.getName() + " joined the guild.");
        }
    }

    private void onDecline(Player sender, Player recipient) {
        Messaging.sendPlayerSpecificMessage(sender, recipient.getName() + " declined your request.");
        Messaging.sendPlayerSpecificMessage(recipient, "You declined the request.");
    }

    private void addNavigationButtons(Player player, Pagination pagination, InventoryContents inventoryContents, int playerCount) {
        int page = pagination.getPage();
        if (page != 0) {
            inventoryContents.set(5, 3, ClickableItem.of(InventoryUtil.LAST_PAGE_BUTTON,
                    e -> attemptPageOpen(player, inventoryContents, pagination, false)));
        }

        if (playerCount - (page * 28) > 28) {
            inventoryContents.set(5, 5, ClickableItem.of(InventoryUtil.NEXT_PAGE_BUTTON,
                    e -> attemptPageOpen(player, inventoryContents, pagination,true)));
        }
    }

    private void attemptPageOpen(Player player, InventoryContents inventoryContents, Pagination pagination, boolean nextPage) {
        List<Player> players = getOnlinePlayersWithoutAGuild();
        fillWithPlayers(pagination, players);

        Pagination page = nextPage ? pagination.next() : pagination.previous();
        if (page != null && players.size() > (page.getPage() + 1) * 28) {
            inventory.open(player, page.getPage());
        } else {
            onRefreshClick(player, inventoryContents);
        }
    }

    private void baseInitialization(InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 5);
    }

    private List<Player> getOnlinePlayersWithoutAGuild() {
        List<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (instance.getCacheManager().isCached(player.getUniqueId())) {
                ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
                if (conquestPlayer.getGuild() == null) {
                    players.add(player);
                }
            }
        }

        return players;
    }

    private void configurePagination(Pagination pagination, InventoryContents inventoryContents) {
        pagination.setItemsPerPage(28);
        pagination.addToIterator(getOptimalIterator(inventoryContents));
    }

    private SlotIterator getOptimalIterator(InventoryContents inventoryContents) {
        SlotIterator iterator = inventoryContents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);

        for (int row = 0; row < inventoryContents.inventory().getRows(); row++) {
            iterator.blacklist(row, 0);
            iterator.blacklist(row, 8);
        }

        return iterator;
    }

    private ItemStack getRefreshItemStack() {
        String displayName = ChatColor.YELLOW + "Refresh player list";
        return new ItemBuilder(Material.BELL).setDisplayName(displayName).build();
    }

    private void onRefreshClick(Player player, InventoryContents inventoryContents) {
        init(player, inventoryContents);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
