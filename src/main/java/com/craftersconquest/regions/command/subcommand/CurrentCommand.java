package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CurrentCommand implements SelectionSubcommand {

    private final ConquestCore instance;

    public CurrentCommand(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;

            List<Region> regions = instance.getRegionManager().getRegionsAt(player);

            if (regions.size() == 0) {
                Messaging.sendPlayerSpecificMessage(player, "You are not in any regions.");
            }

            for (Region region : regions) {
                Messaging.sendPlayerSpecificMessage(player, region.getName());
            }

            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "current";
    }

    @Override
    public String getDescription() {
        return "Returns a list of the current regions at the player's location.";
    }

    @Override
    public boolean inGameOnly() {
        return true;
    }
}
