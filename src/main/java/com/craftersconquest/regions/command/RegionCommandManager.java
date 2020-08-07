package com.craftersconquest.regions.command;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.Component;
import com.craftersconquest.regions.Region;
import com.craftersconquest.regions.command.subcommand.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class RegionCommandManager implements TabExecutor, Component {

    private final ConquestCore instance;
    private final Map<String, SelectionSubcommand> commands = new HashMap<>();
    private final Map<CommandSender, Region> selectedRegions = new HashMap<>();

    public RegionCommandManager(ConquestCore instance) {

        this.instance = instance;
        commands.put("list", new ListCommand(instance, this));
        commands.put("flags", new FlagsCommand());
        commands.put("info", new InfoCommand(instance));
        commands.put("select", new SelectCommand(instance, this));
        commands.put("priority", new PriorityCommand(this));
        commands.put("setflag", new SetFlagCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Messaging.sendErrorMessage(sender, "Please specify a subcommand. For a list of all subcommands, do /region help");
            return true;
        }

        SelectionSubcommand command = commands.get(args[0]);

        if (command == null) {
            Messaging.sendErrorMessage(sender, "Unknown command. Type /region help for a list of all commands.");
            return true;
        }

        if (command.inGameOnly() && !(sender instanceof Player)) {
            Messaging.sendErrorMessage(sender, "This command can only be run in-game");
            return true;
        }

        if (!sender.hasPermission(command.getPermission())) {
            Messaging.sendErrorMessage(sender, "You do not have permission to perform this command.");
            return true;
        }

        if (command.requiresSelection() && !selectedRegions.containsKey(sender)) {
            Messaging.sendErrorMessage(sender, "You must select a region first");
            return true;
        }

        String[] subCommandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subCommandArgs, 0, subCommandArgs.length);

        if (!command.onCommand(sender, subCommandArgs)) {
            Messaging.sendErrorMessage(sender, "Command usage: " + command.getUsage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> toReturn = null;
        final String typed = args[0].toLowerCase();

        if (args.length == 1) {
            for (Map.Entry<String, SelectionSubcommand> entry : commands.entrySet()) {
                final String name = entry.getKey();
                final Subcommand subcommand = entry.getValue();

                if (name.startsWith(typed) && !subcommand.getPermission().equals("")
                        && (sender.hasPermission(subcommand.getPermission()) || sender.hasPermission("swm.*"))) {

                    if (name.equalsIgnoreCase("goto") && (sender instanceof ConsoleCommandSender)) {
                        continue;
                    }

                    if (toReturn == null) {
                        toReturn = new LinkedList<>();
                    }

                    toReturn.add(name);
                }
            }
        }

        return toReturn == null ? Collections.emptyList() : toReturn;
    }

    public void setSelectedRegion(CommandSender sender, Region region) {
        selectedRegions.put(sender, region);
    }

    public Region getSelectedRegion(CommandSender sender) {
        return selectedRegions.get(sender);
    }

    @Override
    public void onEnable() {
        instance.getCommand("region").setExecutor(this);
    }

    @Override
    public void onDisable() {

    }
}
