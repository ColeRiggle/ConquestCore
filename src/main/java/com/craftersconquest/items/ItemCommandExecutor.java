package com.craftersconquest.items;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.items.conquestitem.ConquestItem;
import com.craftersconquest.items.conquestitem.Item;
import com.craftersconquest.messaging.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommandExecutor implements CommandExecutor {

    private final ConquestCore instance;

    public ItemCommandExecutor(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("com.craftersconquest.admin")) {
            Messaging.sendErrorMessage(sender, "No permission.");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("ccadmingenerate")) {
            instance.getItemLoader().generate();
            sender.sendMessage("Generated items.");
        } else if (cmd.getName().equalsIgnoreCase("ccitemgive")) {
            if (args.length != 1) {
                Messaging.sendErrorMessage(sender, "Please enter an item name.");
            } else {
                if (args[0].equalsIgnoreCase("List")) {
                    String list = "";
                    for (Item item : Item.values()) {
                        list += item.toString() + ", ";
                    }
                    Messaging.sendPlayerSpecificMessage(sender, list);
                } else {
                    try {
                        ConquestItem item = Item.valueOf(args[0].toUpperCase()).getConquestItem();
                        Player player = (Player) sender;
                        player.getInventory().addItem(item.getItemStack());
                        Messaging.sendPlayerSpecificMessage(sender, "Item given.");
                    } catch (IllegalArgumentException exception) {
                        Messaging.sendErrorMessage(sender, "Item not found.");
                    }
                }

            }
        }

        return true;
    }
}
