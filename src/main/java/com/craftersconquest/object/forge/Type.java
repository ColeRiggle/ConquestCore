package com.craftersconquest.object.forge;

import org.bukkit.ChatColor;

public enum Type {
    GRAIN(ChatColor.GOLD + "Grain"),
    CRYSTAL(ChatColor.DARK_AQUA + "Crystal"),
    METAL(ChatColor.GREEN + "Metal"),
    ESSENCE(ChatColor.LIGHT_PURPLE + "Essence");

    private static final Type[] values = Type.values();

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Type[] getValues() {
        return values;
    }
}
