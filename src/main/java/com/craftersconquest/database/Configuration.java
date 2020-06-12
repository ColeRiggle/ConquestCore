package com.craftersconquest.database;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration implements Component {

    private final ConquestCore instance;
    private FileConfiguration config;

    public Configuration(ConquestCore instance) {
        this.instance = instance;
    }

    public int getDay() {
        return config.getInt("day");
    }

    public void setDay(int day) {
        config.set("day", day);
    }

    @Override
    public void onEnable() {
        setupConfig();
    }

    private void setupConfig() {
        config = instance.getConfig();
        addDefaults();
        config.options().copyDefaults(true);
        instance.saveConfig();
    }

    private void addDefaults() {
        config.addDefault("day", 1);
    }

    @Override
    public void onDisable() {
        instance.saveConfig();
        Bukkit.getLogger().info("Config saved.");
        Bukkit.getLogger().info("REAL: " + getDay());
    }
}
