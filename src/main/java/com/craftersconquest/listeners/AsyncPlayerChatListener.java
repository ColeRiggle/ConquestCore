package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private final ConquestCore instance;

    public AsyncPlayerChatListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        instance.getInputManager().onAsyncPlayerChat(event);
    }

}
