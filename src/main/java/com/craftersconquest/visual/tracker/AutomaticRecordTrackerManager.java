package com.craftersconquest.visual.tracker;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.visual.tracker.AutomaticRecordTracker;
import com.craftersconquest.visual.tracker.RecordTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AutomaticRecordTrackerManager {

    private final ConquestCore instance;
    private final List<AutomaticRecordTracker> trackers;

    public AutomaticRecordTrackerManager(ConquestCore instance) {
        this.instance = instance;
        trackers = new ArrayList<>();
    }

    public void update() {
        for (AutomaticRecordTracker tracker : trackers) {
            Set<Player> players = tracker.getTrackedPlayers();
            for (Player player : players) {
                tracker.updateRecord(player);
            }
        }
    }

    public void addPlayer(Player player) {
        for (AutomaticRecordTracker tracker : trackers) {
            tracker.updateRecord(player);
        }
    }

    public void removePlayer(Player player) {
        for (AutomaticRecordTracker tracker : trackers) {
            tracker.removePlayer(player);
        }
    }

    public void registerTracker(AutomaticRecordTracker tracker) {
        trackers.add(tracker);
        update();
    }

    public RecordTracker getTracker(String name) {
        for (RecordTracker tracker : trackers) {
            if (tracker.getName().equals(name)) {
                return tracker;
            }
        }

        return null;
    }
}
