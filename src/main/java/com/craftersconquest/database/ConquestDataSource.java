package com.craftersconquest.database;

import com.craftersconquest.object.Guild;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Location;

import java.util.UUID;

public abstract class ConquestDataSource {

    abstract public void open();
    abstract public void close();

    abstract public String getStoredItem(String id);
    abstract public void addStoredItem(String id, String data);

    abstract public ConquestPlayer loadPlayer(UUID playerUUID);
    abstract public void savePlayer(ConquestPlayer player);
    abstract public Guild loadGuild(String name);

    abstract public boolean blocklistContains(Location location);
    abstract public void addToBlocklist(Location location);
    abstract public void removeFromBlocklist(Location location);

}
