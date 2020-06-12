package com.craftersconquest.skills;

import com.craftersconquest.object.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillXpGainEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Skill skill;
    private final double xpChange;

    public SkillXpGainEvent(Player player, Skill skill, double xpChange) {
        this.player = player;
        this.skill = skill;
        this.xpChange = xpChange;
    }

    public Player getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public double getXpChange() {
        return xpChange;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
