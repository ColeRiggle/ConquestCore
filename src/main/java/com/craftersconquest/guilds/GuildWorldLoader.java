package com.craftersconquest.guilds;

import com.craftersconquest.object.Guild;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import com.grinderwolf.swm.plugin.SWMPlugin;
import com.grinderwolf.swm.plugin.config.ConfigManager;
import com.grinderwolf.swm.plugin.config.WorldData;
import com.grinderwolf.swm.plugin.config.WorldsConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;
import java.util.ArrayList;

public class GuildWorldLoader {

    private final ArrayList<Guild> loadedGuilds;

    private final static String TEMPLATE_NAME = "guild";
    private final static String STORAGE_PREFIX = "guild_";
    private final static String DATASOURCE = "mysql";

    private final WorldData worldData;
    private final WorldsConfig worldsConfig;
    private final SlimeLoader slimeLoader;

    public GuildWorldLoader() {
        this.loadedGuilds = new ArrayList<>();
        worldData = new WorldData();
        worldsConfig = ConfigManager.getWorldConfig();
        slimeLoader = SWMPlugin.getInstance().getLoader(DATASOURCE);
        setupWorldData();
    }

    private void setupWorldData() {
        worldData.setSpawn("0, 64, 0");
        worldData.setDataSource(DATASOURCE);
        worldData.setDifficulty("peaceful");
        worldData.setAllowAnimals(false);
        worldData.setLoadOnStartup(false);
        worldData.setPvp(false);
    }

    public boolean isLoaded(Guild guild) {
        return loadedGuilds.contains(guild);
    }

    public void createWorld(Guild guild) {
        String worldName = getStorageName(guild);

        try {
            SlimeWorld slimeWorld = SWMPlugin.getInstance().loadWorld(slimeLoader, TEMPLATE_NAME, false,
                    worldData.toPropertyMap()).clone(worldName, slimeLoader);
            generateWorld(slimeWorld, worldName);

        } catch (IOException | CorruptedWorldException | WorldInUseException |
                NewerFormatException | WorldAlreadyExistsException | UnknownWorldException exception) {
            Bukkit.getLogger().severe("Fatal error occurred while creating: " + worldName + " from copy: " + TEMPLATE_NAME + ".");
        }
    }

    public void load(Guild guild) {
        loadedGuilds.add(guild);
        String worldName = getStorageName(guild);
        loadWorld(worldName);
    }

    private void loadWorld(String worldName) {
        // World has to be loaded asynchronously, then generated synchronously
        Bukkit.getScheduler().runTaskAsynchronously(SWMPlugin.getInstance(), () -> {
            try {
                SlimePropertyMap propertyMap = worldData.toPropertyMap();
                SlimeWorld slimeWorld = SWMPlugin.getInstance().loadWorld(slimeLoader, worldName, false, propertyMap);
                generateWorld(slimeWorld, worldName);

            } catch (IOException | CorruptedWorldException | WorldInUseException |
                    NewerFormatException | UnknownWorldException exception) {
                Bukkit.getLogger().severe("Fatal error occurred while loading: " + worldName);
            }
        });
    }

    private void generateWorld(SlimeWorld slimeWorld, String worldName) {
        Bukkit.getScheduler().runTask(SWMPlugin.getInstance(), () -> {
            try {
                SWMPlugin.getInstance().generateWorld(slimeWorld);

                worldsConfig.getWorlds().put(worldName, worldData);
                worldsConfig.save();

                Bukkit.getLogger().info("Generated: " + worldName);
            } catch (IllegalArgumentException ex) {
                Bukkit.getLogger().severe("Fatal error occurred while generating: " + worldName);
            }
        });
    }

    private String getStorageName(Guild guild) {
        return STORAGE_PREFIX + "" + guild.getName().toLowerCase();
    }

    public void unload(Guild guild) {
        loadedGuilds.remove(guild);
        World world = Bukkit.getWorld(getStorageName(guild));
        Bukkit.unloadWorld(world, true);
        worldsConfig.getWorlds().put(getStorageName(guild), null);
    }
}
