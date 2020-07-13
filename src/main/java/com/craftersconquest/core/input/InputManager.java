package com.craftersconquest.core.input;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InputManager implements Component {

    private final ConquestCore instance;
    private final ConcurrentHashMap<Player, Consumer<String>> pendingInputs;
    private final ConcurrentHashMap<Player, SelectionRequest> pendingSelectionRequests;

    private final static int TIMEOUT = 350;

    public InputManager(ConquestCore instance) {
        this.instance = instance;
        pendingInputs = new ConcurrentHashMap<>();
        pendingSelectionRequests = new ConcurrentHashMap<>();
    }

    public void removePendingSelectionRequest(Player player) {
        pendingSelectionRequests.remove(player);
    }

    public Optional<SelectionRequest> getPendingSelectionRequest(Player player) {
        return Optional.ofNullable(pendingSelectionRequests.get(player));
    }

    public void addPendingSelectionRequest(Player player, SelectionRequest selectionRequest) {
        pendingSelectionRequests.put(player, selectionRequest);

        Messaging.sendLineMessage(player, org.bukkit.ChatColor.GRAY);
        player.sendMessage("");

        TextComponent message = new TextComponent(ChatColor.GRAY + selectionRequest.getPrompt() + "   ");
        TextComponent accept =
                new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "✔" + ChatColor.GRAY + "]");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept"));
        message.addExtra(accept);
        message.addExtra("   ");

        TextComponent decline =
                new TextComponent(ChatColor.GRAY + "[" + ChatColor.RED + "✘" + ChatColor.GRAY + "]");
        decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/decline"));
        message.addExtra(decline);

        player.spigot().sendMessage(message);

        player.sendMessage("");
        Messaging.sendLineMessage(player, org.bukkit.ChatColor.GRAY);

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (pendingSelectionRequests.containsKey(player) &&
                    pendingSelectionRequests.get(player) == selectionRequest) {
                pendingSelectionRequests.remove(player);
                Messaging.sendErrorMessage(player, "Confirmation request timed out.");
            }
        }, TIMEOUT);
    }

    public void addPendingInputRequest(Player player, Consumer<String> consumer) {
        pendingInputs.put(player, consumer);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (pendingInputs.containsKey(player) && pendingInputs.get(player) == consumer) {
                pendingInputs.remove(player);
                Messaging.sendErrorMessage(player, "Input request timed out.");
            }
        }, TIMEOUT);
    }

    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (pendingInputs.containsKey(player)) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(instance, () -> {
                pendingInputs.get(player).accept(event.getMessage());
                pendingInputs.remove(player);
            });
        }
    }

    @Override
    public void onEnable() {
        InputCommandExecutor commandExecutor = new InputCommandExecutor(instance);

        instance.getCommand("accept").setExecutor(commandExecutor);
        instance.getCommand("decline").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {

    }
}
