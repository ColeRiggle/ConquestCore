package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.Settings;
import org.bukkit.command.CommandSender;

public interface Subcommand {

    boolean onCommand(CommandSender sender, String[] args);

    String getUsage();

    String getDescription();

    default boolean inGameOnly() {
        return false;
    }

    default String getPermission() {
        return Settings.ADMIN_PERMISSION;
    }
}
