package com.craftersconquest.core.input;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InputCommandExecutor implements CommandExecutor {

    private final ConquestCore instance;

    public InputCommandExecutor(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Optional<SelectionRequest> possibleSelectionRequest =
                instance.getInputManager().getPendingSelectionRequest(player);

        if (!possibleSelectionRequest.isPresent()) {
            Messaging.sendErrorMessage(player, "You have no pending request.");
            return false;
        }

        String commandName = command.getName();
        SelectionRequest selectionRequest = possibleSelectionRequest.get();
        if (commandName.equalsIgnoreCase("accept")) {
            selectionRequest.getAcceptAction().run();
        } else if (commandName.equalsIgnoreCase("decline")) {
            selectionRequest.getDeclineAction().run();
        }

        instance.getInputManager().removePendingSelectionRequest(player);
        return true;
    }
}
