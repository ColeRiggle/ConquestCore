package com.craftersconquest.items.heads;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.UUID;

public class PlayerHeadCache {

    private final ConquestCore instance;
    private final HashMap<UUID, ItemStack> heads;

    public PlayerHeadCache(ConquestCore instance) {
        this.instance = instance;
        heads = new HashMap<>();
    }

    public ItemStack getPlayerHead(UUID playerUUID) {
        if (heads.containsKey(playerUUID)) {
            return heads.get(playerUUID);
        }

        ItemStack playerSkull = generateSkullItemStack(playerUUID);
        heads.put(playerUUID, playerSkull);
        return playerSkull;
    }

    private ItemStack generateSkullItemStack(UUID playerUUID) {
        OfflinePlayer headOwner = Bukkit.getOfflinePlayer(playerUUID);

        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwningPlayer(headOwner);
        meta.setDisplayName(headOwner.getName());
        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
