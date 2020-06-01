package com.craftersconquest.core;

import com.craftersconquest.database.ConquestDataSource;
import com.craftersconquest.database.ConquestSQLSource;
import org.bukkit.plugin.java.JavaPlugin;

public class ConquestCore extends JavaPlugin {

    private final ConquestDataSource dataSource = new ConquestSQLSource(this);

    @Override
    public void onEnable() {
        dataSource.open();
    }

    @Override
    public void onDisable() {
        dataSource.close();
    }

}
