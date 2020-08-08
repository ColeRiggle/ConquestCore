package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class DeleteCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public DeleteCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Region region = regionCommandManager.getSelectedRegion(sender);
            instance.getRegionManager().remove(region);
            regionCommandManager.setSelectedRegion(sender, null);
            Messaging.sendPlayerSpecificMessage(sender, "Region " + region.getName() + " deleted.");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes the selected region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
