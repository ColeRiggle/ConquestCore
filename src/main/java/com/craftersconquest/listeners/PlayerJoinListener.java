package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.menu.MenuInventory;
import com.craftersconquest.object.shop.Shop;
import com.craftersconquest.object.shop.currency.Coins;
import com.craftersconquest.object.shop.currency.Currency;
import com.craftersconquest.object.shop.item.Cost;
import com.craftersconquest.object.shop.item.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final ConquestCore instance;

    public PlayerJoinListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                instance.getCacheManager().addToCache(event.getUniqueId());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        instance.getScoreboardManager().addPlayer(player);

        Shop shop = new Shop("Sqi's corner");

        Currency currency = new Coins(instance);
        Cost cost = Cost.fromCurrencyAndPrice(currency, 100);

        ShopItem apple = ShopItem.ofTypeWithCost("Apple", Material.APPLE, cost);
        ShopItem spruce = ShopItem.ofTypeWithCost("Spruce Log", Material.SPRUCE_LOG, cost);
        ShopItem oak = ShopItem.ofTypeWithCost("Oak Log", Material.OAK_LOG, cost);
        ShopItem acacia = ShopItem.ofTypeWithCost("Acacia Log", Material.ACACIA_LOG, cost);
        ShopItem birch = ShopItem.ofTypeWithCost("Birch Log", Material.BIRCH_LOG, cost);
        ShopItem darkOak = ShopItem.ofTypeWithCost("Dark Oak Log", Material.DARK_OAK_LOG, cost);
        ShopItem jungle = ShopItem.ofTypeWithCost("Jungle Log", Material.JUNGLE_LOG, cost);
        ShopItem boat = ShopItem.ofTypeWithCost("Oak Boat", Material.OAK_BOAT, cost);

        ShopItem blue = ShopItem.ofTypeWithCost("Blue", Material.BLUE_WOOL, cost);
        ShopItem red = ShopItem.ofTypeWithCost("Red", Material.RED_WOOL, cost);
        ShopItem green = ShopItem.ofTypeWithCost("Green", Material.GREEN_WOOL, cost);
        ShopItem orange = ShopItem.ofTypeWithCost("Orange", Material.ORANGE_WOOL, cost);
        ShopItem yellow = ShopItem.ofTypeWithCost("Yellow", Material.YELLOW_WOOL, cost);
        ShopItem purple = ShopItem.ofTypeWithCost("Purple", Material.PURPLE_WOOL, cost);
        ShopItem black = ShopItem.ofTypeWithCost("Black", Material.BLACK_WOOL, cost);

        shop.addItem(apple);
        shop.addItem(spruce);
        shop.addItem(oak);
        shop.addItem(acacia);
        shop.addItem(birch);
        shop.addItem(darkOak);
        shop.addItem(jungle);
        shop.addItem(boat);

        shop.addItem(blue);
        shop.addItem(red);
        shop.addItem(green);
        shop.addItem(orange);
        shop.addItem(yellow);
        shop.addItem(purple);
        shop.addItem(black);

//        ShopInventory inventory = new ShopInventory(instance, shop);
//        inventory.getInventory().open(player);

        MenuInventory inventory = new MenuInventory(instance);
        inventory.getInventory().open(player);

        instance.getMenuManager().givePlayerMenu(player);
    }
}
