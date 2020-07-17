package com.craftersconquest.items.conquestitem;

import org.bukkit.ChatColor;

public enum Rarity {
    NONE("None"),
    COMMON(ChatColor.GRAY + "Common"),
    RARE(ChatColor.BLUE  + "Rare"),
    STELLAR(ChatColor.LIGHT_PURPLE + "Stellar"),
    RELIC(ChatColor.GOLD + "Relic"),
    MYTHIC(ChatColor.AQUA + "Mythic");

    private final String text;

    Rarity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
