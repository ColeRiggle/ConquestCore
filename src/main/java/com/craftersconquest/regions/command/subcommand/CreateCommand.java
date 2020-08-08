package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class CreateCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public CreateCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 2) {
            String newRegionName = args[0];
            String priorityString = args[1];

            if (instance.getRegionManager().getRegionByName(newRegionName) != null) {
                Messaging.sendErrorMessage(sender, "A region with the name " + newRegionName + " already exists.");
                return true;
            }

            try {
                int priority = Integer.parseInt(priorityString);
                Region newRegion = new Region(newRegionName, priority);
                instance.getRegionManager().add(newRegion);
                regionCommandManager.setSelectedRegion(sender, newRegion);
                Messaging.sendPlayerSpecificMessage(sender, "Region " + newRegionName + " created.");

            } catch (NumberFormatException exception) {
                Messaging.sendErrorMessage(sender, "Priority must be an integer.");
            }

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "create <name> <priority>";
    }

    @Override
    public String getDescription() {
        return "Creates a new region with the specified name and priority.";
    }
}
