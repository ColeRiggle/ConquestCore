package com.craftersconquest.items.conquestitem;

import org.bukkit.ChatColor;

public enum Rarity {
    NONE("None"),
    COMMON(ChatColor.GRAY + "" + ChatColor.BOLD + "Common"),
    RARE(ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"),
    STELLAR(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Stellar"),
    RELIC(ChatColor.GOLD + "" + ChatColor.BOLD + "Relic"),
    MYTHIC(ChatColor.AQUA + "" + ChatColor.BOLD + "Mythic");

    private final String text;

    Rarity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
