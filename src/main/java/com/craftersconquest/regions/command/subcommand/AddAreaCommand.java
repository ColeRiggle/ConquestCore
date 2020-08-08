package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Area;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class AddAreaCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public AddAreaCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String areaName = args[0];
            Area area = regionCommandManager.getPendingAreaByName(areaName);

            if (area == null) {
                Messaging.sendErrorMessage(sender, "An area with the name " + areaName + " was not found.");
                return true;
            }

            Region region = regionCommandManager.getSelectedRegion(sender);
            region.addArea(area);
            Messaging.sendPlayerSpecificMessage(sender, "Area " + areaName + " added to " + region.getName() + ".");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "addarea <area>";
    }

    @Override
    public String getDescription() {
        return "Assigns an area to the selected region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
