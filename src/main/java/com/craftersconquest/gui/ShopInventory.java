package com.craftersconquest.gui;

import com.craftersconquest.object.shop.Shop;
import com.craftersconquest.object.shop.item.ShopItem;
import com.craftersconquest.util.InventoryUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.*;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopInventory implements ConquestInventory, InventoryProvider {

    private final SmartInventory inventory;
    private final Shop shop;

    public ShopInventory(Shop shop) {
        this.shop = shop;

        inventory = SmartInventory.builder()
                .id(shop.getName())
                .provider(this)
                .size(getOptimalRowCount(), 9)
                .title(shop.getName())
                .build();
    }

    private int getOptimalRowCount() {
        return shop.getItems().size() <= 7 ? 3 : 4;
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        Pagination pagination = inventoryContents.pagination();

        fillWithItems(pagination);
        addNavigationButtons(player, pagination, inventoryContents);
        configurePagination(pagination, inventoryContents);
    }

    private void fillWithItems(Pagination pagination) {
        List<ShopItem> shopItems = shop.getItems();

        ClickableItem[] clickableItems = new ClickableItem[shopItems.size()];

        for (int index = 0; index < clickableItems.length; index++) {
            clickableItems[index] = ClickableItem.empty(shopItems.get(index).getDisplayItemStack());
        }

        pagination.setItems(clickableItems);
    }

    private void addNavigationButtons(Player player, Pagination pagination, InventoryContents inventoryContents) {
        if (pagination.getPage() != 0) {
            inventoryContents.set(3, 3, ClickableItem.of(InventoryUtil.LAST_PAGE_BUTTON,
                    e -> inventory.open(player, pagination.previous().getPage())));
        }

        if (pagination.getPageItems().length > 14) {
            inventoryContents.set(3, 5, ClickableItem.of(InventoryUtil.NEXT_PAGE_BUTTON,
                    e -> inventory.open(player, pagination.next().getPage())));
        }

        inventoryContents.set(3, 4, ClickableItem.of(InventoryUtil.CLOSE,
                e -> e.getWhoClicked().closeInventory()));
    }

    private void configurePagination(Pagination pagination, InventoryContents inventoryContents) {
        pagination.setItemsPerPage(14);
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

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
