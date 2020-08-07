package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Area;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.flags.Flag;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class InfoCommand implements SelectionSubcommand {

    private final ConquestCore instance;

    public InfoCommand(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 1) {

            ChatColor prefixColor = ChatColor.AQUA;
            ChatColor valueColor = ChatColor.GRAY;

            String regionName = args[0];
            Region region = instance.getRegionManager().getRegionByName(regionName);

            if (region == null) {
                Messaging.sendErrorMessage(sender, "Could not find region: " + regionName + ".");
                return true;
            }

            Messaging.sendLineMessage(sender);

            sender.sendMessage(prefixColor + "Name: " + valueColor + region.getName());
            sender.sendMessage(prefixColor + "Priority: " + valueColor + region.getPriority());
            sender.sendMessage(prefixColor + "Parent: " + valueColor + (region.getParent() == null ? "none" : region.getParent().getName()));


            StringBuilder flags = new StringBuilder();
            for (Map.Entry<Flag<?>, Object> entry : region.getFlags().entrySet()) {
                Flag<?> flag = entry.getKey();
                flags.append(getNameValueOfFlag(flag, entry.getValue()));
            }
            sender.sendMessage(prefixColor + "Flags: " + valueColor + flags.toString());

            StringBuilder areas = new StringBuilder();
            for (Area area : region.getAreas()) {
                areas.append(area.getName() + ", ");
            }

            sender.sendMessage(prefixColor + "Areas: " + valueColor + areas.toString());

            Messaging.sendLineMessage(sender);

            return true;
        }

        return false;
    }

    private <T> String getNameValueOfFlag(Flag<T> flag, Object value) {
        return flag.getName() + ": " + flag.serialize((T) value) + ", ";
    }

    @Override
    public String getUsage() {
        return "info <region>";
    }

    @Override
    public String getDescription() {
        return "Shows useful information about a given region.";
    }
}
