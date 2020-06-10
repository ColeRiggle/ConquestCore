package com.craftersconquest.messaging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Messaging {

    private Messaging() {

    }

    // ERROR: &c&l[!] &r&c
    // PLAYER MESSAGE: &e&l[!] &r&e
    // SERVER MESSAGE: &9&l[!] &r&9

    public static void sendLineMessage(CommandSender recipient) {
        sendLineMessage(recipient, ChatColor.GRAY);
    }

    public static final String INDENT = "     ";

    public static void sendLineMessage(CommandSender recipient, ChatColor color) {
        recipient.sendMessage(ChatColor.RESET + "" + color + ChatColor.STRIKETHROUGH +
                "----------------------------------------------------");
    }

    public static void sendErrorMessage(CommandSender recipient, String message) {
        recipient.sendMessage(baseColorError + ChatColor.BOLD + messagePrefix + baseColorError + message);
    }

    public static void sendPlayerSpecificMessage(CommandSender recipient, String message) {
        recipient.sendMessage(baseColorPlayerSpecific + ChatColor.BOLD + messagePrefix + baseColorPlayerSpecific + message);
    }

    public static void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(baseColorBroadcast + ChatColor.BOLD + messagePrefix + baseColorBroadcast + message);
        }
    }

    public static void sendNoPermissionMessage(CommandSender recipient) {
        sendErrorMessage(recipient, "You don't have permission.");
    }
    public static void sendUpgradableNoPermissionMessage(CommandSender recipient) {
        sendErrorMessage(recipient, "You don't have currently permission. Upgrade your rank to gain access.");
    }

    public static void sendUnknownCommandMessage(CommandSender recipient) {
        sendErrorMessage(recipient, "Unknown command.");
    }

    private static String messagePrefix = "[!] ";

    public static String baseColorError = ChatColor.RESET + "" + ChatColor.RED + "";
    public static String baseColorPlayerSpecific = ChatColor.RESET + "" + ChatColor.YELLOW + "";
    public static String baseColorBroadcast = ChatColor.RESET + "" + ChatColor.BLUE + "";

    public static String messagePlayerColor = ChatColor.RESET + "" + ChatColor.WHITE + "";
    public static String messageEconomicAssetColor = ChatColor.RESET + "" + ChatColor.GOLD + "";
}
