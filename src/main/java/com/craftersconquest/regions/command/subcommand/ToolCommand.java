package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.items.conquestitem.Item;
import com.craftersconquest.messaging.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToolCommand implements SelectionSubcommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            player.getInventory().addItem(Item.REGION_TOOL.getConquestItem().getItemStack());
            Messaging.sendPlayerSpecificMessage(player, "Region tool added to inventory.");

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "tool";
    }

    @Override
    public String getDescription() {
        return "Gives the player a region selection too.";
    }

    @Override
    public boolean inGameOnly() {
        return true;
    }
}
