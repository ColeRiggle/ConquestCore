package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.skills.SkillFormatter;
import com.craftersconquest.skills.SkillXpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SkillXpGainEventListener implements Listener {

    private final ConquestCore instance;
    private final SkillFormatter formatter;

    public SkillXpGainEventListener(ConquestCore instance, SkillFormatter formatter) {
        this.instance = instance;
        this.formatter = formatter;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSkillXPGainEvent(SkillXpGainEvent event) {
        Player player = event.getPlayer();
        Skill skill = event.getSkill();
        Double xpGained = event.getXpChange();
        instance.getVisualsManager().sendActionBarMessage(player, formatter.getXpGainMessage(skill, xpGained));
    }
}
