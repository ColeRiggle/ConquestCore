package com.craftersconquest.database;

import com.craftersconquest.objects.Guild;
import com.craftersconquest.player.ConquestPlayer;

import java.util.UUID;

public abstract class ConquestDataSource {

    abstract public void open();
    abstract public void close();

    abstract public ConquestPlayer loadPlayer(UUID playerUUID);
    abstract public void savePlayer(ConquestPlayer player);
    abstract public Guild loadGuild(String name);

}
