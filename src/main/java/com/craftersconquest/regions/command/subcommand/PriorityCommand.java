package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;

public class PriorityCommand implements SelectionSubcommand {

    private final RegionCommandManager regionCommandManager;

    public PriorityCommand(RegionCommandManager regionCommandManager) {
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            try {
                int priority = Integer.parseInt(args[0]);
                Region region = regionCommandManager.getSelectedRegion(sender);

                region.setPriority(priority);
                Messaging.sendPlayerSpecificMessage(sender, "Priority successfully set to " + priority + ".");
            } catch (NumberFormatException exception) {
                Messaging.sendErrorMessage(sender, "Priority must be an integer.");
            }

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "priority <number>";
    }

    @Override
    public String getDescription() {
        return "Sets the region's priority. A higher number corresponds to a higher priority.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
