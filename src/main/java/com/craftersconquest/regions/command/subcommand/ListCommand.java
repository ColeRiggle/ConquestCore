package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class ListCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public ListCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {

            Messaging.sendLineMessage(sender);

            for (Region region : instance.getRegionManager().getRegions()) {
                sender.sendMessage(region.getName());
            }

            Messaging.sendLineMessage(sender);

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all regions.";
    }
}
