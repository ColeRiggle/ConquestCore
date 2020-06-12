package com.craftersconquest.visual;

import com.craftersconquest.core.ConquestCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class VisualsManager {

    private final ConquestCore instance;

    public VisualsManager(ConquestCore instance) {
        this.instance = instance;
    }

    public void sendActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
