package com.craftersconquest.regions;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.regions.flags.Flag;
import com.craftersconquest.regions.flags.Flags;
import com.craftersconquest.regions.flags.InvalidFlagFormat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.BlockVector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RegionManager implements Component {

    private final ConquestCore instance;
    private final static String STORAGE_NAME = "regions.yml";

    private final List<Region> regions;

    private File regionsFile;
    private FileConfiguration regionsConfiguration;

    public RegionManager(ConquestCore instance) {
        this.instance = instance;
        regions = new ArrayList<>();
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

//    private void test() {
//        Region region = new Region("sqiLand", 15);
//        region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
//        region.setFlag(Flags.BLOCKLIST, StateFlag.State.ALLOW);
//
//        BlockVector pos1 = new BlockVector(10, 10, 10);
//        BlockVector pos2 = new BlockVector(15, 6, 4);
//
//        Area area = new Area("area1", "badlands", pos1, pos2);
//        Area area2 = new Area("area2", "badlands", pos1, pos2);
//
//        region.addArea(area);
//        region.addArea(area2);
//
//        saveRegion(region);
//    }

    public Region getRegion(String name) {
        for (Region region : regions) {
            if (region.getName().equals(name)) {
                return region;
            }
        }

        return null;
    }

    private void loadRegions() {
        Set<String> regionNames = regionsConfiguration.getKeys(false);
        regionNames.forEach(value -> regions.add(loadRegion(value)));
        loadParents();

        Bukkit.getLogger().info("Loaded " + regions.size() + " regions");
    }

    private Region loadRegion(String key) {
        Bukkit.getLogger().info("Loading... " + key);
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
        Bukkit.getLogger().info("ANs: " + areaNames.toString());
        Set<Area> areas = new HashSet<>();
        areaNames.forEach(name -> areas.add(loadArea(configurationSection, name)));
        return areas;
    }

    private Area loadArea(ConfigurationSection section, String name) {
        String basePath = name + ".";
        String worldName = section.getString(basePath + "worldName");
        Bukkit.getLogger().info("WN: " + worldName + " " + section.getString(basePath + "pos1"));

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
            Region parent = getRegion(regionsConfiguration.getString(path));

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
        Bukkit.getLogger().info("Saving " + regions.size() + " regions...");

        regions.forEach(this::saveRegion);
    }

    private void saveRegion(Region region) {

        Bukkit.getLogger().info("Saving region: " + region.getName());

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
            ioException.printStackTrace();
            Bukkit.getLogger().info("Saving error");
        }
    }
}
