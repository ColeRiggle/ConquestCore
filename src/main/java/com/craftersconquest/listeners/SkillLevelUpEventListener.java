package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.skills.SkillFormatter;
import com.craftersconquest.skills.SkillLevelUpEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SkillLevelUpEventListener implements Listener {

    private final ConquestCore instance;
    private final SkillFormatter formatter;

    public SkillLevelUpEventListener(ConquestCore instance) {
        this.instance = instance;
        this.formatter = new SkillFormatter();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSkillLevelUpEvent(SkillLevelUpEvent event) {
        Skill skill = event.getSkill();
        Player player = event.getPlayer();
        int newLevel = event.getNewLevel();

        Messaging.sendLineMessage(player, ChatColor.AQUA);

        player.sendMessage(ChatColor.AQUA + "  " + ChatColor.BOLD +
                "Skill Level Up " + skill.getType().getName() + " " +
                ChatColor.GRAY + "" + ChatColor.BOLD + Skill.getRomanizedLevel(newLevel - 1) +
                "►" + Skill.getRomanizedLevel(newLevel));
        player.sendMessage("");

        for (String line : formatter.getLevelDescription(skill, newLevel).split("\n")) {
            player.sendMessage(line);
        }

        Messaging.sendLineMessage(player, ChatColor.AQUA);
    }
}
