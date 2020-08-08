package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class CopyCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public CopyCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 2) {
            String templateRegionName = args[0];
            String newRegionName = args[1];

            Region templateRegion = instance.getRegionManager().getRegionByName(templateRegionName);
            if (templateRegion == null ) {
                Messaging.sendErrorMessage(sender, "Could not find region " + templateRegionName + ".");
                return true;
            }

            if (instance.getRegionManager().getRegionByName(newRegionName) != null) {
                Messaging.sendErrorMessage(sender, "A region with the name " + newRegionName + " already exists.");
                return true;
            }

            Region newRegion = new Region(newRegionName, templateRegion.getPriority(), templateRegion.getFlags(), templateRegion.getAreas());
            newRegion.setParent(templateRegion.getParent());
            instance.getRegionManager().add(newRegion);
            regionCommandManager.setSelectedRegion(sender, newRegion);
            Messaging.sendPlayerSpecificMessage(sender, "Region " + newRegionName + " created as a copy of " + templateRegionName + ".");
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "copy <template> <name>";
    }

    @Override
    public String getDescription() {
        return "Create a new region based on the specified template";
    }
}
