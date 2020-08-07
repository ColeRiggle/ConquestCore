package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import org.bukkit.command.CommandSender;

public class RemoveFlagCommand implements SelectionSubcommand {

    private final RegionCommandManager regionCommandManager;

    public RemoveFlagCommand(RegionCommandManager regionCommandManager) {
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 1) {
            String flagName = args[0];

            Flag<?> flag = Flags.matchFlag(flagName);

            if (flag == null) {
                Messaging.sendErrorMessage(sender, flagName + " is not a valid flag.");
                return true;
            }

            Region region = regionCommandManager.getSelectedRegion(sender);

            if (!region.getFlags().containsKey(flag)) {
                Messaging.sendErrorMessage(sender, "Region " + region.getName() + " does not have a value for " + flagName + ".");
                return true;
            }

            region.removeFlag(flag);
            Messaging.sendPlayerSpecificMessage(sender, "Flag " + flagName + " removed from region " + region.getName() + ".");

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "removeflag <flag>";
    }

    @Override
    public String getDescription() {
        return "Removes a flag from the selected region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
