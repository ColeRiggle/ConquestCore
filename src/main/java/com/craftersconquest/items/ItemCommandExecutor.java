package com.craftersconquest.items;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ItemCommandExecutor implements CommandExecutor {

    private final ConquestCore instance;

    public ItemCommandExecutor(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("com.craftersconquest.admin")) {
            instance.getItemManager().generate();
            sender.sendMessage("Generated items.");
            return true;
        }

        return false;
    }
}
