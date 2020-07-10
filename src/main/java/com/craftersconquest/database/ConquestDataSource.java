package com.craftersconquest.database;

import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public abstract class ConquestDataSource {

    abstract public void open();
    abstract public void close();

    abstract public String getStoredItem(String id);
    abstract public void addStoredItem(String id, String data);

    abstract public ConquestPlayer loadPlayer(UUID playerUUID);
    abstract public void savePlayer(ConquestPlayer player);

    abstract public boolean blocklistContains(Location location);
    abstract public void addToBlocklist(Location location);
    abstract public void removeFromBlocklist(Location location);

    abstract public List<Guild> loadGuilds();
    abstract public void saveGuilds(List<Guild> guilds);

}
