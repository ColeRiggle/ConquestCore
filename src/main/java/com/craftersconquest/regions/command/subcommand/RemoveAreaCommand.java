package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Area;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class RemoveAreaCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public RemoveAreaCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String areaName = args[0];
            Region region = regionCommandManager.getSelectedRegion(sender);

            Area area = null;

            for (Area regionArea : region.getAreas()) {
                if (regionArea.getName().equals(areaName)) {
                    area = regionArea;
                    break;
                }
            }

            if (area == null) {
                Messaging.sendErrorMessage(sender, "The selected region does not have an area with the name " + areaName + ".");
                return true;
            }

            region.removeArea(area);
            Messaging.sendPlayerSpecificMessage(sender, "Area " + areaName + " successfully removed.");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "removearea <area>";
    }

    @Override
    public String getDescription() {
        return "Removes an area from the selected region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
