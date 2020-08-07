package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import org.bukkit.command.CommandSender;

public class GetFlagCommand implements SelectionSubcommand {

    private final RegionCommandManager regionCommandManager;

    public GetFlagCommand(RegionCommandManager regionCommandManager) {
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

            Messaging.sendPlayerSpecificMessage(sender, getNameValueOfFlag(flag, region.getFlag(flag)));

            return true;
        }

        return false;
    }

    private <T> String getNameValueOfFlag(Flag<T> flag, Object value) {
        return flag.getName() + ": " + flag.serialize((T) value) + ", ";
    }

    @Override
    public String getUsage() {
        return "getflag <flag>";
    }

    @Override
    public String getDescription() {
        return "Returns the current value of the specified flag for the region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
