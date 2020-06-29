package com.craftersconquest.guilds;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.guild.Guild;

public class GuildManager implements Component {

    private final ConquestCore instance;

    private GuildWorldLoader loader;

    public GuildManager(ConquestCore instance) {
        this.instance = instance;
    }

    public void createWorld(Guild guild) {
        loader.createWorld(guild);
    }

    public void unloadWorld(Guild guild) {
        loader.unload(guild);
    }

    public void loadWorld(Guild guild) {
        if (loader.isLoaded(guild)) {
            loader.load(guild);
        }
    }

    public boolean hasWorldLoaded(Guild guild) {
        return loader.isLoaded(guild);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }
}
