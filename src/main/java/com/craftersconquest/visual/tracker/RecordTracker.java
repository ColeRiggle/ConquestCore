package com.craftersconquest.visual.tracker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class RecordTracker<T extends Number> {

    private final String name;
    private final Map<Player, T> records;

    public RecordTracker(String name) {
        this.name = name;
        records = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void removePlayer(Player player) {
        records.remove(player);
    }

    public void updateRecord(Player player, T value) {
        records.put(player, value);
    }

    public Set<Player> getTrackedPlayers() {
        return records.keySet();
    }

    public T getRecord(Player player) {
        return records.get(player);
    }
}
