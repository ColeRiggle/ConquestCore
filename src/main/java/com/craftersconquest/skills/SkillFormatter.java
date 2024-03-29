package com.craftersconquest.skills;

import com.craftersconquest.util.Formatting;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.skill.Reward;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.util.NumbersUtil;
import org.bukkit.ChatColor;

import java.util.AbstractQueue;
import java.util.List;

public class SkillFormatter {

    public String getXpGainMessage(Skill skill, double xpGain) {
        return ChatColor.GREEN + "+" + NumbersUtil.formatDouble(xpGain) + " " +
                skill.getType().getName() + " XP (" + NumbersUtil.formatDouble(skill.getXp()) +
                "/" + NumbersUtil.formatDouble(skill.getRequiredXpForNextLevel()) + ")";
    }

    public String getLevelDescription(Skill skill, int level) {
        StringBuilder message = new StringBuilder();

        final double previousMultiplier = skill.getMultiplierForLevel(level - 1);
        final double currentMultiplier = skill.getMultiplierForLevel(level);
        final String abilityName = skill.getType().getAbility().getAbilityName();

        message.append(ChatColor.GREEN + "  " + ChatColor.BOLD + "Rewards" + ChatColor.RESET + "\n");
        message.append(Messaging.INDENT + ChatColor.YELLOW).append(abilityName).append(" ").append(Skill.getRomanizedLevel(level)).append("\n");
        message.append(Messaging.INDENT + " " + ChatColor.GRAY).append(previousMultiplier).append("►").append(ChatColor.GREEN).append(currentMultiplier).append("%").append(ChatColor.GRAY).append(" ").append(skill.getType().getAbility().getAbilityDescription()).append("\n");
        message.append(Messaging.INDENT + ChatColor.GRAY + "+").append(Messaging.messageEconomicAssetColor).append(skill.getCoinReward(level)).append(" Coins").append("\n");


        List<Reward> rewards = skill.getType().getRewardsForLevel(level);
        if (rewards.size() != 0) {
            message.append("\n");
            message.append(ChatColor.DARK_AQUA + "  " + ChatColor.BOLD + "New ").append(Formatting.getPlural(rewards.size(), "Ability", "Abilities")).append("\n");
            for (Reward reward : rewards) {
                message.append(Messaging.INDENT + ChatColor.WHITE).append(reward.getDescription()).append(ChatColor.RESET);
                message.append("\n");
            }
        }

        return message.toString();
    }

    public String getProgressBar(Skill skill) {
        double currentXp = skill.getXp();
        double necessaryXp = skill.getRequiredXpForNextLevel();

        String bar = "";
        bar += Formatting.getBarRepresentation((int) currentXp, (int) necessaryXp,
                18, ChatColor.AQUA, ChatColor.GRAY);
        bar += " " + Formatting.getPartsAndTotal((int) currentXp, (int) necessaryXp,
                ChatColor.GRAY, ChatColor.AQUA);
        return bar;
    }
}
