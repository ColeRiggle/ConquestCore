package com.craftersconquest.regions;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.InvalidFlagFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RegionManager implements Component {

    private final ConquestCore instance;

    private final List<Region> regions;
    private final Map<String, Region> lastKnownRegions;

    private final static String STORAGE_NAME = "regions.yml";
    private File regionsFile;
    private FileConfiguration regionsConfiguration;

    public RegionManager(ConquestCore instance) {
        this.instance = instance;
        regions = new ArrayList<>();
        lastKnownRegions = new HashMap<>();
    }

    public Region getRegionByName(String name) {
        for (Region region : regions) {
            if (region.getName().equals(name)) {
                return region;
            }
        }

        return null;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void add(Region region) {
        regions.add(region);
    }

    public void remove(Region region) {
        regions.remove(region);
    }

    public Region getRegionAt(Player player) {
        Location playerLocation = player.getLocation();
        String playerName = player.getName();
        if (lastKnownRegions.containsKey(playerName)) {
            Region lastKnownRegion = lastKnownRegions.get(playerName);
            if (lastKnownRegion.contains(playerLocation)) {
                return lastKnownRegion;
            }
        }

        Region region = getRegionAt(playerLocation);
        lastKnownRegions.put(playerName, region);

        return getRegionAt(playerLocation);
    }

    public Region getRegionAt(Location location) {
        Region highestPriorityRegion = null;
        for (Region region : regions) {
            if (region.contains(location)) {
                if (highestPriorityRegion == null) {
                    highestPriorityRegion = region;
                } else {
                    if (region.getPriority() > highestPriorityRegion.getPriority()) {
                        highestPriorityRegion = region;
                    }
                }
            }
        }

        return highestPriorityRegion;
    }

    public List<Region> getRegionsAt(Player player) {
        return getRegionsAt(player.getLocation());
    }

    public List<Region> getRegionsAt(Location location) {
        List<Region> playerRegions = new ArrayList<>();

        for (Region region : regions) {
            if (region.contains(location)) {
                playerRegions.add(region);
            }
        }

        playerRegions.sort((o1, o2) -> o2.getPriority() - o1.getPriority());

        return playerRegions;
    }

    @Override
    public void onEnable() {
        setupRegionsFile();
        loadRegions();
    }

    private void setupRegionsFile() {
        regionsFile = new File(instance.getDataFolder(), STORAGE_NAME);
        regionsConfiguration = YamlConfiguration.loadConfiguration(regionsFile);
        if (!regionsFile.exists()) {
            instance.saveResource(STORAGE_NAME, false);
        }
    }

    private void loadRegions() {
        Set<String> regionNames = regionsConfiguration.getKeys(false);
        regionNames.forEach(value -> regions.add(loadRegion(value)));
        loadParents();
    }

    private Region loadRegion(String key) {
        String basePath = key + '.';
        String name = key;
        int priority = regionsConfiguration.getInt(basePath + "priority");
        Map<Flag<?>, Object> flags = getFlagsFromString(regionsConfiguration.getString(basePath + "flags"));
        Set<Area> areas = getAreas(basePath);
        return new Region(name, priority, flags, areas);
    }

    private Map<Flag<?>, Object> getFlagsFromString(String value) {
        Map<Flag<?>, Object> flags = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder(value);
        stringBuilder.deleteCharAt(value.length() - 1);
        stringBuilder.deleteCharAt(0);
        String flagsStringList = stringBuilder.toString();
        String[] components = flagsStringList.split(",");
        for (String flagString : components) {
            int colonIndex = flagString.indexOf(':');
            String flagName = flagString.substring(0, colonIndex);
            String flagValue = flagString.substring(colonIndex + 2);
            Flag<?> flag = Flags.matchFlag(flagName);
            try {
                Object obj = flag.parseInput(flagValue);
                flags.put(flag, obj);
            } catch (InvalidFlagFormat invalidFlagFormat) {
                Bukkit.getLogger().
                        info("Invalid flag format exception encountered while reading region '" +
                                value + "': " + invalidFlagFormat.toString());
            }
        }

        return flags;
    }

    private Set<Area> getAreas(String regionBasePath) {
        ConfigurationSection configurationSection = regionsConfiguration.getConfigurationSection(regionBasePath + "areas");
        Set<String> areaNames = configurationSection.getKeys(false);
        Set<Area> areas = new HashSet<>();
        areaNames.forEach(name -> areas.add(loadArea(configurationSection, name)));
        return areas;
    }

    private Area loadArea(ConfigurationSection section, String name) {
        String basePath = name + ".";
        String worldName = section.getString(basePath + "worldName");

        BlockVector pos1 = deserializeBlockVector(section.getString(basePath + "pos1"));
        BlockVector pos2 = deserializeBlockVector(section.getString(basePath + "pos2"));
        return new Area(name, worldName, pos1, pos2);
    }

    private BlockVector deserializeBlockVector(String value) {
        String[] components = value.split(",");
        double x = Double.parseDouble(components[0]);
        double y = Double.parseDouble(components[1]);
        double z = Double.parseDouble(components[2]);
        return new BlockVector(x, y, z);
    }

    private void loadParents() {
        regions.forEach(this::loadParent);
    }

    private void loadParent(Region region) {
        String path = region.getName() + ".parent";
        if (regionsConfiguration.contains(path)) {
            Region parent = getRegionByName(regionsConfiguration.getString(path));

            if (parent != null) {
                region.setParent(parent);
            } else {
                Bukkit.getLogger().severe("Could not find parent for " + region.getName());
            }
        }
    }

    private <T> String serialize(Flag<T> flag, Object value) {
        return flag.serialize((T) value);
    }

    @Override
    public void onDisable() {
        saveRegions();
    }

    private void saveRegions() {
        regions.forEach(this::saveRegion);
    }

    private void saveRegion(Region region) {
        String basePath = region.getName() + '.';
        regionsConfiguration.set(basePath + "priority", region.getPriority());
        for (Area area : region.getAreas()) {
            String areaPath = basePath + "areas." + area.getName() + ".";
            regionsConfiguration.set(areaPath + "worldName", area.getWorldName());
            regionsConfiguration.set(areaPath + "pos1", area.getPos1().toString());
            regionsConfiguration.set(areaPath + "pos2", area.getPos2().toString());
        }

        StringBuilder flagsSerialization = new StringBuilder();
        flagsSerialization.append('{');

        Iterator<Map.Entry<Flag<?>, Object>> iterator = region.getFlags().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Flag<?>, Object> entry = iterator.next();
            Flag<?> flag = entry.getKey();
            Object value = entry.getValue();
            flagsSerialization.append(flag.getName() + ": " + serialize(flag, value));

            if (iterator.hasNext()) {
                flagsSerialization.append(",");
            }
        }

        flagsSerialization.append('}');

        regionsConfiguration.set(basePath + "flags", flagsSerialization.toString());

        try {
            regionsConfiguration.save(regionsFile);
        } catch (IOException ioException) {
            Bukkit.getLogger().info("Saving error: " + ioException.toString());
        }
    }
}
