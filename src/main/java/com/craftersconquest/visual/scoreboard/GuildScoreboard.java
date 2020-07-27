package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.visual.scoreboard.format.FormatBehavior;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class GuildScoreboard extends ConquestScoreboard {

    private final ConquestCore instance;

    public GuildScoreboard(ConquestCore instance, FormatBehavior formatBehavior) {
        super(formatBehavior);
        this.instance = instance;
    }

    @Override
    public void setupPlayer(Player player) {
        setScoreboard(player);
    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public void removePlayer(Player player) {

    }

    private void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("guildScoreboard", player.getName(), getFormatBehavior().getServerTitle());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank1 = obj.getScore(" ");
        blank1.setScore(15);

        Team dateTracker = scoreboard.registerNewTeam("guildNameTracker");
        dateTracker.addEntry(ChatColor.YELLOW + "");
        dateTracker.setPrefix(getGuildName(player));
        obj.getScore(ChatColor.YELLOW + "").setScore(14);

        Score blank2 = obj.getScore("  ");
        blank2.setScore(13);

        Score grainHeader = obj.getScore(Type.GRAIN.getDisplayName());
        grainHeader.setScore(12);

        Team coinsTracker = scoreboard.registerNewTeam("grainTracker");
        coinsTracker.addEntry(ChatColor.GOLD + "");
        coinsTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.GOLD + "").setScore(11);

        Score blank3 = obj.getScore("   ");
        blank3.setScore(10);

        Score metalHeader = obj.getScore(Type.METAL.getDisplayName());
        metalHeader.setScore(9);

        Team metalTracker = scoreboard.registerNewTeam("metalTracker");
        metalTracker.addEntry(ChatColor.AQUA + "");
        metalTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.AQUA + "").setScore(8);

        Score blank4 = obj.getScore("    ");
        blank4.setScore(7);

        Score crystalHeader = obj.getScore(Type.CRYSTAL.getDisplayName());
        crystalHeader.setScore(6);

        Team crystalTracker = scoreboard.registerNewTeam("crystalTracker");
        crystalTracker.addEntry(ChatColor.BLUE + "");
        crystalTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.BLUE + "").setScore(5);

        Score blank5 = obj.getScore("     ");
        blank5.setScore(4);

        Score essenceHeader = obj.getScore(Type.ESSENCE.getDisplayName());
        essenceHeader.setScore(3);

        Team essenceTracker = scoreboard.registerNewTeam("essenceTracker");
        essenceTracker.addEntry(ChatColor.RED + "");
        essenceTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.RED + "").setScore(2);

        player.setScoreboard(scoreboard);
    }

    private String getGuildName(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        String name = guild.getName();
        return ChatColor.YELLOW + name;
    }

    private String getGrainCount() {
        return getFormatBehavior().getPreferredElementColor() + getFormatBehavior().getElementPrefix() + "0 / 0";
    }
}
