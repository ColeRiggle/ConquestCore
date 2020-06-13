package com.craftersconquest.time;

import org.bukkit.ChatColor;

public class TimeFormatter {

    public String fromWorldTickets(long ticks) {
        String ending = "";
        String symbol = "";

        if (ticks <= 18000) {
            ticks += 6000;
        } else {
            ticks -= 18000;
        }

        long hours = (int) (ticks / 1000);
        long minutes = (int) ((ticks - (1000 * hours)) / 20);

        minutes = formatMinutes(minutes);

        if (isNight(hours)) {
            symbol += ChatColor.AQUA + "☽";
        } else {
            symbol += ChatColor.GOLD + "☀";
        }

        if (hours > 12) {
            hours -= 12;
            ending = "pm ";
        } else {
            ending = "am ";
        }

        String minString = String.valueOf(minutes);
        if (minString.equals("0")) {
            minString = "00";
        }

        return ChatColor.GRAY + "" + hours + ":" + minString + ending + symbol;
    }

    private long formatMinutes(long minutes) {
        return Math.round(minutes / 10.0) * 10;
    }

    private boolean isNight(long hours) {
        return (hours < 6 || hours > 22);
    }
}
