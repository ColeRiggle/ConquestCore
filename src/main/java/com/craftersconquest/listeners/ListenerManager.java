package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.objects.Component;

public class ListenerManager implements Component {

    private final ConquestCore instance;

    public ListenerManager(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public void onEnable() {
        registerListeners();
    }

    private void registerListeners() {
        instance.getServer().getPluginManager().registerEvents(new PlayerJoinListener(instance), instance);
    }

    @Override
    public void onDisable() {

    }
}
