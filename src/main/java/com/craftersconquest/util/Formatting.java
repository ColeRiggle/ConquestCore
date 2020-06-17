package com.craftersconquest.util;

import org.bukkit.ChatColor;

public final class Formatting {

    private Formatting() { }

    public static String getPlural(double quantity, String singular, String plural) {
        if (quantity == 1) {
            return singular;
        }
        return plural;
    }

    public static String getBarRepresentation(int val, int maxVal, int characterCount, ChatColor filled, ChatColor empty) {

        String bar = empty + "[";

        final double valPerBar = (double) maxVal / (characterCount - 2);

        for (double x = 0; x < maxVal - (valPerBar * 2); x += valPerBar) {
            if (((double) val / maxVal) > (x / maxVal)) {
                bar += filled + "■";
            } else {
                bar += empty + "■";
            }
        }

        bar += empty + "■]";

        return bar;
    }

    public static String getPartsAndTotal(int val, int maxVal, ChatColor primary, ChatColor emphasis) {
        return ChatColor.GRAY + "(" + emphasis + val + primary + " / " + emphasis +
                maxVal + ChatColor.GRAY + ")";
    }
}
