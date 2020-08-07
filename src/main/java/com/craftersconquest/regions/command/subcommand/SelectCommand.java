package com.craftersconquest.regions.command.subcommand;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.RegionCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectCommand implements SelectionSubcommand {

    private final ConquestCore instance;
    private final RegionCommandManager regionCommandManager;

    public SelectCommand(ConquestCore instance, RegionCommandManager regionCommandManager) {
        this.instance = instance;
        this.regionCommandManager = regionCommandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String regionName = args[0];
            Region region = instance.getRegionManager().getRegionByName(regionName);
            if (region == null) {
                Messaging.sendErrorMessage(sender, "Could not find region: " + regionName + ".");
                return true;
            }

            Player player = (Player) sender;
            regionCommandManager.setSelectedRegion(player, region);

            Messaging.sendPlayerSpecificMessage(sender, "Region " + regionName + " selected.");

            return true;
        }

        return false;
    }

    @Override
    public boolean inGameOnly() {
        return true;
    }

    @Override
    public String getUsage() {
        return "select <region>";
    }

    @Override
    public String getDescription() {
        return "Selects a region for further modification.";
    }
}
