package com.craftersconquest.skills;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.objects.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class SkillsManager {

    private final ConquestCore instance;

    public SkillsManager(ConquestCore instance) {
        this.instance = instance;
    }

    public void onValidBlockBreak(BlockBreakEvent event) {
        TrackedBlockBreak trackedBlockBreakEvent =
                new TrackedBlockBreak(event.getPlayer(), event.getBlock().getType(), event.getBlock().getLocation());

        Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                if (!isBlocked(trackedBlockBreakEvent.getLocation())) {
                    onNonBlocklistBlockBreak(trackedBlockBreakEvent);
                }
            }
        });
    }

    private boolean isBlocked(Location location) {
        return instance.getBlocklist().contains(location);
    }

    private void onNonBlocklistBlockBreak(TrackedBlockBreak event) {
        Player player = event.getPlayer();
        //List<Skill> skills = instance.getCacheManager().getConquestPlayer(player.getUniqueId()).getSkills();

        Skill skill = instance.getCacheManager().getConquestPlayer(player.getUniqueId()).getMiningSkill();

//        for (Skill skill : skills) {
            Material material = event.getMaterial();
            addRewardIfProper(event, skill);
//        }
    }

    private void addRewardIfProper(TrackedBlockBreak event, Skill skill) {
        if (skill.isTrackedMaterial(event.getMaterial())) {
            addSkillXpAndCheckForLevelChanges(event, skill);
        }
    }

    private void addSkillXpAndCheckForLevelChanges(TrackedBlockBreak event, Skill skill) {
        Material material = event.getMaterial();
        final double gainedXp = skill.getMaterialWorth(material);

        final int initialLevel = skill.getLevel();
        skill.addXp(gainedXp);
        final int newLevel = skill.getLevel();

        for (int level = initialLevel; level < newLevel; level++) {
            SkillLevelUpEvent skillLevelUpEvent = new SkillLevelUpEvent(event.getPlayer(), skill, level + 1);
            Bukkit.getScheduler().runTask(instance, () -> Bukkit.getPluginManager().callEvent(skillLevelUpEvent));
        }
    }
}
