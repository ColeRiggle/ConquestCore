package com.craftersconquest.forges;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.guild.Guild;

import java.util.ArrayList;
import java.util.List;

public class ForgeManager implements Component {

    private final ConquestCore instance;
    private final List<Forge> forges;

    public ForgeManager(ConquestCore instance) {
        this.instance = instance;
        forges = new ArrayList<>();
    }

    @Override
    public void onEnable() {
        registerForgesFromGuilds();
    }

    private void registerForgesFromGuilds() {
        for (Guild guild : instance.getGuildManager().getGuilds()) {
            for (Forge forge : guild.getForges()) {
                forges.add(forge);
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
