package com.craftersconquest.visual.scoreboard;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.guild.Stockpile;
import com.craftersconquest.object.guild.StockpileFormatter;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.visual.scoreboard.format.FormatBehavior;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class GuildScoreboard extends ConquestScoreboard {

    private final ConquestCore instance;
    private final String resourcePrefix;
    private final StockpileFormatter stockpileFormatter;

    public GuildScoreboard(ConquestCore instance, FormatBehavior formatBehavior, StockpileFormatter stockpileFormatter) {
        super(formatBehavior);
        this.instance = instance;
        this.stockpileFormatter = stockpileFormatter;
        resourcePrefix = getFormatBehavior().getPreferredElementColor() + getFormatBehavior().getElementPrefix();
    }

    @Override
    public void setupPlayer(Player player) {
        setScoreboard(player);
    }

    @Override
    public void updatePlayer(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();

        scoreboard.getTeam("guildNameTracker").setPrefix(getGuildName(guild));
        scoreboard.getTeam("grainTracker").setPrefix(getFormattedQuantity(guild, Type.GRAIN));
        scoreboard.getTeam("metalTracker").setPrefix(getFormattedQuantity(guild, Type.METAL));
        scoreboard.getTeam("crystalTracker").setPrefix(getFormattedQuantity(guild, Type.CRYSTAL));
        scoreboard.getTeam("essenceTracker").setPrefix(getFormattedQuantity(guild, Type.ESSENCE));
    }

    @Override
    public void removePlayer(Player player) {

    }

    private void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("guildScoreboard", player.getName(), getFormatBehavior().getServerTitle());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();

        Score blank1 = obj.getScore(" ");
        blank1.setScore(15);

        Team dateTracker = scoreboard.registerNewTeam("guildNameTracker");
        dateTracker.addEntry(ChatColor.YELLOW + "");
        dateTracker.setPrefix(getGuildName(guild));
        obj.getScore(ChatColor.YELLOW + "").setScore(14);

        Score blank2 = obj.getScore("  ");
        blank2.setScore(13);

        Score grainHeader = obj.getScore(Type.GRAIN.getDisplayName());
        grainHeader.setScore(12);

        Team coinsTracker = scoreboard.registerNewTeam("grainTracker");
        coinsTracker.addEntry(ChatColor.GOLD + "");
        coinsTracker.setPrefix(getFormattedQuantity(guild, Type.GRAIN));
        obj.getScore(ChatColor.GOLD + "").setScore(11);

        Score blank3 = obj.getScore("   ");
        blank3.setScore(10);

        Score metalHeader = obj.getScore(Type.METAL.getDisplayName());
        metalHeader.setScore(9);

        Team metalTracker = scoreboard.registerNewTeam("metalTracker");
        metalTracker.addEntry(ChatColor.AQUA + "");
        coinsTracker.setPrefix(getFormattedQuantity(guild, Type.METAL));
        obj.getScore(ChatColor.AQUA + "").setScore(8);

        Score blank4 = obj.getScore("    ");
        blank4.setScore(7);

        Score crystalHeader = obj.getScore(Type.CRYSTAL.getDisplayName());
        crystalHeader.setScore(6);

        Team crystalTracker = scoreboard.registerNewTeam("crystalTracker");
        crystalTracker.addEntry(ChatColor.BLUE + "");
        coinsTracker.setPrefix(getFormattedQuantity(guild, Type.CRYSTAL));
        obj.getScore(ChatColor.BLUE + "").setScore(5);

        Score blank5 = obj.getScore("     ");
        blank5.setScore(4);

        Score essenceHeader = obj.getScore(Type.ESSENCE.getDisplayName());
        essenceHeader.setScore(3);

        Team essenceTracker = scoreboard.registerNewTeam("essenceTracker");
        essenceTracker.addEntry(ChatColor.RED + "");
        coinsTracker.setPrefix(getFormattedQuantity(guild, Type.ESSENCE));
        obj.getScore(ChatColor.RED + "").setScore(2);

        player.setScoreboard(scoreboard);
    }

    private String getGuildName(Guild guild) {
        String name = guild.getName();
        return ChatColor.YELLOW + name;
    }

    private String getFormattedQuantity(Guild guild, Type type) {
        return resourcePrefix + stockpileFormatter.getResourceQuantity(guild.getStockpile(), type, getFormatBehavior().getPreferredElementColor());
    }
}
