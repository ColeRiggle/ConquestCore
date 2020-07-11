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

        Bukkit.getLogger().info("Init called.");

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

    private ClickableItem createClickableItemFromPlayer(Player player) {
        ItemStack playerHead = instance.getItemManager().getPlayerHeadCache().getPlayerHead(player.getUniqueId());
        String displayName = ChatColor.YELLOW + playerHead.getItemMeta().getDisplayName();
        List<String> lore = InventoryUtil.createLore("", ChatColor.RED + "No guild", "",
                ChatColor.GREEN + "Click to invite this player");
        ItemStack modifiedPlayerHead = new ItemBuilder(playerHead).setDisplayName(displayName).setLore(lore).build();

        return ClickableItem.empty(modifiedPlayerHead);
    }

    private void addNavigationButtons(Player player, Pagination pagination, InventoryContents inventoryContents, int playerCount) {
        int page = pagination.getPage();
        if (page != 0) {
            inventoryContents.set(5, 3, ClickableItem.of(InventoryUtil.LAST_PAGE_BUTTON,
                    e -> attemptPageOpen(player, pagination, false)));
        }

        if (playerCount - (page * 28) > 28) {
            inventoryContents.set(5, 5, ClickableItem.of(InventoryUtil.NEXT_PAGE_BUTTON,
                    e -> attemptPageOpen(player, pagination, true)));
        }
    }

    private void attemptPageOpen(Player player, Pagination pagination, boolean nextPage) {
        List<Player> players = getOnlinePlayersWithoutAGuild();
        if (pagination.getPageItems().length != players.size()) {
            fillWithPlayers(pagination, players);
        }
        Pagination page = nextPage ? pagination.next() : pagination.previous();

        Bukkit.getLogger().info("C: " + (page.getPage() * 28));
        Bukkit.getLogger().info("C: " + (players.size()));

        if (page != null &&  players.size() > (page.getPage() + 1) * 28) {
            inventory.open(player, page.getPage());
        }
    }

    private void baseInitialization(InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 5);
    }

    private List<Player> getOnlinePlayersWithoutAGuild() {
        List<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
            if (conquestPlayer.getGuild() == null) {
                for (int x = 0; x < 200; x++) {
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
