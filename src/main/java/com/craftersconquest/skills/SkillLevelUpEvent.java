package com.craftersconquest.skills;

import com.craftersconquest.object.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillLevelUpEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Skill skill;
    private final int newLevel;

    public SkillLevelUpEvent(Player player, Skill skill, int newLevel) {
        this.player = player;
        this.skill = skill;
        this.newLevel = newLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getNewLevel() {
        return newLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
