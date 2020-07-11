package com.craftersconquest.guilds;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildManager implements Component {

    private final ConquestCore instance;
    private final List<Guild> guilds;
    private GuildWorldLoader loader;

    public GuildManager(ConquestCore instance) {
        this.instance = instance;
        this.guilds = new ArrayList<>();
    }

    private void loadGuilds() {
        Bukkit.getLogger().info("Loading guilds...");
        guilds.addAll(instance.getDataSource().loadGuilds());
        Bukkit.getLogger().info("Finished loading guilds!");
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public Guild getGuild(String name) {
        for (Guild guild : guilds) {
            if (guild.getName().equals(name)) {
                return guild;
            }
        }

        return null;
    }

    public void createWorld(Guild guild) {
        loader.createWorld(guild);
    }

    public void unloadWorld(Guild guild) {
        Bukkit.getLogger().info("Unloading world for: " + guild.getName());
        loader.unload(guild);
    }

    public void teleportPlayerToGuild(Player player, Guild guild) {
        Location location = new Location(loader.getWorld(guild), 0, 50, 0);
        player.teleport(location);
    }

    public void onPlayerJoin(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        if (conquestPlayer.getGuild() != null) {
            loadGuildIfApplicable(guild);
        }
    }

    private void loadGuildIfApplicable(Guild guild) {
        if (!hasWorldLoaded(guild)) {
            loadWorld(guild);
        }
    }

    private boolean hasWorldLoaded(Guild guild) {
        return loader.isLoaded(guild);
    }

    private void loadWorld(Guild guild) {
        if (loader.isLoaded(guild)) {
            loader.load(guild);
        }
    }

    public void onPlayerQuit(Player player) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        if (guild != null) {
            unloadWorld(guild);
        }
    }

    @Override
    public void onEnable() {
        loader = new GuildWorldLoader();
        loadGuilds();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Saving guilds...");
        instance.getDataSource().saveGuilds(guilds);
        Bukkit.getLogger().info("Saved guilds.");

        Bukkit.getLogger().info("Unloading guild worlds...");
        for (Guild guild : guilds) {
            if (hasWorldLoaded(guild)) {
                loader.unload(guild);
            }
        }
        Bukkit.getLogger().info("Unloaded guild worlds.");
    }
}
