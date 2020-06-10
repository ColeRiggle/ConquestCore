package com.craftersconquest.visual;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.util.NumbersUtil;
import com.craftersconquest.visual.tracker.ManualRecordTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {

    private final ConquestCore instance;

    private final String scoreboardTitle = ChatColor.RED + "" + ChatColor.BOLD + "Conquest";
    private final ManualRecordTracker<Double> balanceTracker = new ManualRecordTracker<>();

    public ScoreboardManager(ConquestCore instance) {
        this.instance = instance;
    }

    public void addPlayer(Player player) {
        updateLastBalance(player);
        player.setScoreboard(generateScoreboard(player));
    }

    private void updateLastBalance(Player player) {
        double balance = instance.getEconomy().getBalance(player);
        balanceTracker.updateRecord(player, balance);
    }

    private Scoreboard generateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("craftersconquest", player.getName(), scoreboardTitle);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank = obj.getScore("");
        blank.setScore(15);

        Score location = obj.getScore(ChatColor.WHITE + "Location ");
        location.setScore(14);

        Team locationTracker = scoreboard.registerNewTeam("locationTracker");
        locationTracker.addEntry(ChatColor.AQUA + "");
        locationTracker.setPrefix(getLocation(player));
        obj.getScore(ChatColor.AQUA + "").setScore(13);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(12);

        Score coins = obj.getScore(ChatColor.WHITE + "Satchel ");
        coins.setScore(11);

        Team coinsTracker = scoreboard.registerNewTeam("coinsTracker");
        coinsTracker.addEntry(ChatColor.GOLD + "");
        coinsTracker.setPrefix(getFormattedCoinsBalance(player));
        obj.getScore(ChatColor.GOLD + "").setScore(10);


        Score blank3 = obj.getScore("  ");
        blank3.setScore(9);

        return scoreboard;
    }

    private String getLocation(Player player) {
        return "Lobby";
    }

    private String getFormattedCoinsBalance(Player player) {
        double balance = instance.getEconomy().getBalance(player);
        int balanceDifference = (int) (balance - balanceTracker.getRecord(player));

        String change = "";

        if (balanceDifference > 0) {
            change = " +" + NumbersUtil.formatInt(balanceDifference);
        } else if (balanceDifference < 0) {
            change = " " + NumbersUtil.formatInt(balanceDifference);
        }

        return ChatColor.GOLD + NumbersUtil.formatDouble(balance) + change;
    }

    public void removePlayer(Player player) {
        balanceTracker.removePlayer(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }


    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("locationTracker").setPrefix(getLocation(player));
        scoreboard.getTeam("coinsTracker").setPrefix(getFormattedCoinsBalance(player));
    }
}
