package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import org.bukkit.command.CommandSender;

public class FlagsCommand implements SelectionSubcommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Messaging.sendLineMessage(sender);

            for (Flag flag : Flags.getFlags()) {
                sender.sendMessage(flag.getName());
            }

            Messaging.sendLineMessage(sender);

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "flags";
    }

    @Override
    public String getDescription() {
        return "Lists all the potential flags a region can have.";
    }
}
