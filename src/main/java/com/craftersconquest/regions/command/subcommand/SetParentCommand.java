package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class SetParentCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public SetParentCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 1) {
            String parentRegionName = args[0];
            Region parentRegion = instance.getRegionManager().getRegionByName(parentRegionName);

            if (parentRegion == null) {
                Messaging.sendErrorMessage(sender, "The parent region " + parentRegionName + " was not found.");
                return true;
            }

            regionCommandManager.getSelectedRegion(sender).setParent(parentRegion);
            Messaging.sendPlayerSpecificMessage(sender, "Parent region changed to " + parentRegionName + ".");

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "parent <region>";
    }

    @Override
    public String getDescription() {
        return "Sets the parent of the selected region";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
