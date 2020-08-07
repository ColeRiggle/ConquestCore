package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.InvalidFlagFormat;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SetFlagCommand implements SelectionSubcommand {

    private final RegionCommandManager regionCommandManager;

    public SetFlagCommand(RegionCommandManager regionCommandManager) {
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length >= 2) {
            String flagName = args[0];

            StringBuilder flagValue = new StringBuilder();
            String[] flagValues = Arrays.copyOfRange(args, 1, args.length);
            for (String value : flagValues) {
                flagValue.append(value).append(" ");
            }
            flagValue = new StringBuilder(flagValue.toString().trim());

            Flag<?> flag = Flags.matchFlag(flagName);

            if (flag == null) {
                Messaging.sendErrorMessage(sender, flagName + " is not a valid flag.");
                return true;
            }

            Region region = regionCommandManager.getSelectedRegion(sender);

            try {
                setFlag(region, flag, flag.parseInput(flagValue.toString()));
            } catch (InvalidFlagFormat exception) {
                Messaging.sendErrorMessage(sender, flagValue + " is not a valid flag value.");
                return true;
            }

            Messaging.sendPlayerSpecificMessage(sender, flag.getName() + " set to " + flagValue + " for region " + region.getName());

            return true;
        }

        return false;
    }

    private <T> void setFlag(Region region, Flag<T> flag, Object value) {
        region.setFlag(flag, (T) value);
    }

    @Override
    public String getUsage() {
        return "setflag <flag> <value>";
    }

    @Override
    public String getDescription() {
        return "Sets the value of the flag to the specified value for the selected region.";
    }

    @Override
    public boolean requiresSelection() {
        return true;
    }
}
