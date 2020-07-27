package com.craftersconquest.guilds;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        Bukkit.getLogger().info("Couldn't find player guild: " + name);
        return null;
    }

    public void create(Guild guild) {
        guilds.add(guild);
        loader.createWorld(guild);
        instance.getForgeManager().loadHologramsForGuild(guild);
    }

    public void unload(Guild guild) {
        Bukkit.getLogger().info("Unloading world for: " + guild.getName());
        loader.unload(guild);
        instance.getForgeManager().unloadHologramsForGuild(guild);
    }

    public World getWorld(Guild guild) {
        return loader.getWorld(guild);
    }

    public void onPlayerJoin(UUID playerUUID) {
        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(playerUUID);
        Guild guild = conquestPlayer.getGuild();
        if (conquestPlayer.getGuild() != null) {
            loadGuildIfApplicable(guild);
        }
    }

    private void loadGuildIfApplicable(Guild guild) {
        if (!loader.isLoaded(guild)) {
            Bukkit.getLogger().info("Loading if applicable...");
            load(guild);
        }
    }

    private void load(Guild guild) {
        loader.load(guild);
    }

    public void onPlayerQuit(Player player) {
        Bukkit.getLogger().info("Player quit detected");

        ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(player);
        Guild guild = conquestPlayer.getGuild();
        teleportPlayerFromGuild(conquestPlayer, player);

        List<Player> onlineGuildMembers = guild.getOnlinePlayers();
        onlineGuildMembers.remove(player);

        if (onlineGuildMembers.size() == 0) {
            Bukkit.getLogger().info("Unloading guild world...");
            unload(guild);
        }
    }

    private void teleportPlayerFromGuild(ConquestPlayer conquestPlayer, Player player) {
        if (conquestPlayer.getLastLocation().isPresent()) {
            instance.getTeleporter().teleport(player, conquestPlayer.getLastLocation().get(), false);
        } else {
            instance.getTeleporter().
                    teleport(player, Bukkit.getWorld(Settings.RPG_WORLD_NAME).getSpawnLocation(), false);
        }
    }

    @Override
    public void onEnable() {
        loader = new GuildWorldLoader(instance);
        loadGuilds();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Saving guilds...");
        instance.getDataSource().saveGuilds(guilds);
        Bukkit.getLogger().info("Saved guilds.");

        Bukkit.getLogger().info("Unloading guild worlds...");
        for (Guild guild : guilds) {
            if (loader.isLoaded(guild)) {
                loader.unload(guild);
            }
        }
        Bukkit.getLogger().info("Unloaded guild worlds.");
    }
}
