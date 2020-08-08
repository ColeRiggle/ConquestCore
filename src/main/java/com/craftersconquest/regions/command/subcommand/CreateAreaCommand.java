package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Area;
import com.craftersconquest.regions.command.RegionCommandManager;
import com.craftersconquest.regions.tool.RegionSelection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

public class CreateAreaCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public CreateAreaCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Player player = (Player) sender;

            String areaName = args[0];
            RegionSelection selection = instance.getRegionManager().getToolSelectionManager().getSelection(player);

            if (!selection.isComplete()) {
                Messaging.sendErrorMessage(player, "You must select two points before creating an area.");
                return true;
            }

            if (regionCommandManager.getPendingAreaByName(areaName) != null) {
                Messaging.sendErrorMessage(player, "A pending area by that name already exists.");
                return true;
            }

            String worldName = selection.getPos1().getWorld().getName();
            BlockVector pos1 = new BlockVector(
                    selection.getPos1().getX(),
                    selection.getPos1().getY(),
                    selection.getPos1().getZ());
            BlockVector pos2 = new BlockVector(
                    selection.getPos2().getX(),
                    selection.getPos2().getY(),
                    selection.getPos2().getZ());
            Area area = new Area(areaName, worldName, pos1, pos2);

            regionCommandManager.addPendingArea(area);
            Messaging.sendPlayerSpecificMessage(player, "Area " + area.getName() + " created.");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "createarea <name>";
    }

    @Override
    public String getDescription() {
        return "Creates an area with the specified name from your current selection.";
    }

    @Override
    public boolean inGameOnly() {
        return true;
    }

    @Override
    public boolean requiresSelection() {
        return false;
    }
}
