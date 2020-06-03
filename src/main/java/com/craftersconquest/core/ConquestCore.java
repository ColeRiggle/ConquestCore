package com.craftersconquest.core;

import com.craftersconquest.database.ConquestDataSource;
import com.craftersconquest.database.ConquestSQLSource;
import com.craftersconquest.listeners.ListenerManager;
import com.craftersconquest.objects.Component;
import com.craftersconquest.player.ConquestPlayer;
import com.craftersconquest.player.ConquestPlayerCacheManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConquestCore extends JavaPlugin {

    private final ConquestDataSource dataSource = new ConquestSQLSource(this);
    private final HashMap<OfflinePlayer, ConquestPlayer> conquestPlayers = new HashMap<>();
    private final List<Component> components = new ArrayList<>();

    private final ConquestPlayerCacheManager cacheManager = new ConquestPlayerCacheManager(this);
    private final ListenerManager listenerManager = new ListenerManager(this);

    @Override
    public void onEnable() {
        dataSource.open();
        registerComponents();
        enableComponents();
    }

    private void registerComponents() {
        components.add(cacheManager);
        components.add(listenerManager);
    }

    private void enableComponents() {
        for (Component component : components) {
            component.onEnable();
        }
    }

    @Override
    public void onDisable() {
        disableComponents();
        dataSource.close();
    }

    private void disableComponents() {
        for (Component component : components) {
            component.onDisable();
        }
    }

    public ConquestDataSource getDataSource() {
        return dataSource;
    }

    public ConquestPlayerCacheManager getCacheManager() {
        return cacheManager;
    }

}
