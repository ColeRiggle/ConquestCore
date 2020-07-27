package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class GuildScoreboard extends ConquestScoreboard {

    private final ConquestCore instance;

    private List<Player> players;

    public GuildScoreboard(ConquestCore instance) {
        this.instance = instance;
        players = new ArrayList<>();
    }


    @Override
    public void update() {
        for (Player player : players) {
            update(player);
        }
    }

    private void update(Player player) {

    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        setScoreboard(player);
    }

    private void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Objective obj = scoreboard.registerNewObjective("craftersconquest", player.getName(), Settings.SCOREBOARD_HEADER);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank1 = obj.getScore(" ");
        blank1.setScore(15);

        Team dateTracker = scoreboard.registerNewTeam("nameTracker");
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

        Score metalHeader = obj.getScore(Type.METAL.getDisplayName());
        metalHeader.setScore(10);

        Team metalTracker = scoreboard.registerNewTeam("metalTracker");
        metalTracker.addEntry(ChatColor.AQUA + "");
        metalTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.AQUA + "").setScore(9);

        Score crystalHeader = obj.getScore(Type.CRYSTAL.getDisplayName());
        crystalHeader.setScore(8);

        Team crystalTracker = scoreboard.registerNewTeam("crystalTracker");
        crystalTracker.addEntry(ChatColor.BLUE + "");
        crystalTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.BLUE + "").setScore(7);

        Score essenceHeader = obj.getScore(Type.CRYSTAL.getDisplayName());
        essenceHeader.setScore(6);

        Team essenceTracker = scoreboard.registerNewTeam("essenceTracker");
        essenceTracker.addEntry(ChatColor.RED + "");
        essenceTracker.setPrefix(getGrainCount());
        obj.getScore(ChatColor.RED + "").setScore(5);

        player.setScoreboard(scoreboard);
    }

    private String getGuildName(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        String name = guild.getName();
        return ChatColor.YELLOW + name;
    }

    private String getGrainCount() {
        return ChatColor.WHITE + "0/0";
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }
}
