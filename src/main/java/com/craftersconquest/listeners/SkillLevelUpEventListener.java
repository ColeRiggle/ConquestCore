package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.skills.SkillLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SkillLevelUpEventListener implements Listener {

    private final ConquestCore instance;

    public SkillLevelUpEventListener(ConquestCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSkillLevelUpEvent(SkillLevelUpEvent event) {
        Bukkit.getLogger().info("SKill level up event: " + event.getNewLevel());

        Player player = event.getPlayer();
        player.sendMessage(event.getSkill().getType().getName() + " Leveled up to " + event.getNewLevel());
        player.sendMessage(event.getSkill().getType().getAbility() + " now higher");

    }
}