package com.craftersconquest.listeners;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.skills.SkillFormatter;

public class ListenerManager implements Component {

    private final ConquestCore instance;
    private final SkillFormatter formatter;

    public ListenerManager(ConquestCore instance) {
        this.instance = instance;
        formatter = new SkillFormatter();
    }

    @Override
    public void onEnable() {
        registerListeners();
    }

    private void registerListeners() {
        instance.getServer().getPluginManager().registerEvents(new PlayerJoinListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerQuitListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new BlockBreakListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new BlockPlaceListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new SkillLevelUpEventListener(instance, formatter), instance);
        instance.getServer().getPluginManager().registerEvents(new SkillXpGainEventListener(instance, formatter), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerDeathListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerDropItemListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerInteractListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new InventoryMoveItemListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new InventoryClickListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(instance), instance);
    }

    @Override
    public void onDisable() {

    }
}
