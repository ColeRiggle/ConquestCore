package com.craftersconquest.core;

import com.craftersconquest.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InputManager {

    private final ConquestCore instance;
    private final ConcurrentHashMap<Player, Consumer<String>> pendingInputs;

    public InputManager(ConquestCore instance) {
        this.instance = instance;
        pendingInputs = new ConcurrentHashMap<>();
    }

    public void addPendingInput(Player player, Consumer<String> consumer) {
        pendingInputs.put(player, consumer);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (pendingInputs.containsKey(player)) {
                pendingInputs.remove(player);
                Messaging.sendErrorMessage(player, "Input request timed out.");
            }
        }, 300L);
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

}
