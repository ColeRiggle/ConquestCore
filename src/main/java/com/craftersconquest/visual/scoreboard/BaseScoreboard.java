package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.util.NumbersUtil;
import com.craftersconquest.visual.scoreboard.format.FormatBehavior;
import com.craftersconquest.visual.tracker.AutomaticRecordTrackerManager;
import com.craftersconquest.visual.tracker.BalanceTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class BaseScoreboard extends ConquestScoreboard {

    private final ConquestCore instance;

    private AutomaticRecordTrackerManager trackerManager;
    private BalanceTracker balanceTracker;

    public BaseScoreboard(ConquestCore instance, FormatBehavior formatBehavior) {
        super(formatBehavior);
        this.instance = instance;
        this.trackerManager = new AutomaticRecordTrackerManager(instance);
        this.balanceTracker = new BalanceTracker(instance);
        trackerManager.registerTracker(balanceTracker);
    }

    @Override
    public void setupPlayer(Player player) {
        trackerManager.addPlayer(player);
        setScoreboard(player);
    }

    @Override
    public void updatePlayer(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("dateTracker").setPrefix(getDate());
        scoreboard.getTeam("timeTracker").setPrefix(getTime());
        scoreboard.getTeam("locationTracker").setPrefix(getLocation(player));
        scoreboard.getTeam("coinsTracker").setPrefix(getFormattedCoinsBalance(player));
    }

    @Override
    public void removePlayer(Player player) {
        trackerManager.removePlayer(player);
    }

    private void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("craftersconquest", player.getName(), getFormatBehavior().getServerTitle());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank1 = obj.getScore(" ");
        blank1.setScore(15);

        Team dateTracker = scoreboard.registerNewTeam("dateTracker");
        dateTracker.addEntry(ChatColor.YELLOW + "");
        dateTracker.setPrefix(getDate());
        obj.getScore(ChatColor.YELLOW + "").setScore(14);

        Team timeTracker = scoreboard.registerNewTeam("timeTracker");
        timeTracker.addEntry(ChatColor.RED + "");
        timeTracker.setPrefix(getTime());
        obj.getScore(ChatColor.RED + "").setScore(13);

        Score blank2 = obj.getScore("  ");
        blank2.setScore(12);

        Score location = obj.getScore(ChatColor.GRAY + "Location ");
        location.setScore(11);

        Team locationTracker = scoreboard.registerNewTeam("locationTracker");
        locationTracker.addEntry(ChatColor.AQUA + "");
        locationTracker.setPrefix(getLocation(player));
        obj.getScore(ChatColor.AQUA + "").setScore(10);

        Score blank3 = obj.getScore("   ");
        blank3.setScore(9);

        Score coins = obj.getScore(ChatColor.GRAY + "Satchel ");
        coins.setScore(8);

        Team coinsTracker = scoreboard.registerNewTeam("coinsTracker");
        coinsTracker.addEntry(ChatColor.GOLD + "");
        coinsTracker.setPrefix(getFormattedCoinsBalance(player));
        obj.getScore(ChatColor.GOLD + "").setScore(7);


        Score blank4 = obj.getScore("    ");
        blank4.setScore(6);

        player.setScoreboard(scoreboard);
    }

    private String getDate() {
        return "  " + ChatColor.WHITE + instance.getTimeManager().getDate().toString();
    }

    private String getTime() {
        return "  " + ChatColor.GRAY + instance.getTimeManager().getTime();
    }

    private String getLocation(Player player) {
        String regionName = instance.getRegionManager().getFlagValueAt(Flags.PRETTY_NAME, player.getLocation());
        return getFormatBehavior().getElementPrefix() + ChatColor.AQUA + regionName;
    }

    private String getFormattedCoinsBalance(Player player) {
        double balance = instance.getEconomy().getBalance(player);
        int balanceDifference = (int) (balance - balanceTracker.getRecord(player));

        String change = "";

        if (balanceDifference > 0) {
            change = " (+" + NumbersUtil.formatInt(balanceDifference) + ")";
        } else if (balanceDifference < 0) {
            change = " (" + NumbersUtil.formatInt(balanceDifference) + ")";
        }

        return getFormatBehavior().getElementPrefix() + ChatColor.GOLD + NumbersUtil.formatDouble(balance) + change;
    }
}
