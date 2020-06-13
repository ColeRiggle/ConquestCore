package com.craftersconquest.time;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.object.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeManager implements Component {

    private final ConquestCore instance;
    private final TimeFormatter formatter;
    private Date date;
    private String time;

    public TimeManager(ConquestCore instance) {
        this.instance = instance;
        formatter = new TimeFormatter();
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    private void generateTime() {
        World world = Bukkit.getWorld(Settings.RPG_WORLD_NAME);
        time = formatter.fromWorldTickets(world.getTime());
    }

    @Override
    public void onEnable() {
        loadDate();
        scheduleDateUpdater();
    }

    private void loadDate() {
        date = Date.fromInt(instance.getConfiguration().getDay());
    }

    private void scheduleDateUpdater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> checkForDateChangeAndUpdate(), 0, 200);
    }

    private void checkForDateChangeAndUpdate() {
        World world = Bukkit.getWorld(Settings.RPG_WORLD_NAME);
        generateTime();
        if (world.getTime() < 180) {
            Bukkit.getLogger().info("New day");
            advanceDate();
        }
    }

    private void advanceDate() {
        date = Date.fromInt(date.getElapsedDays() + 1);
        instance.getConfiguration().setDay(date.getElapsedDays());
    }

    @Override
    public void onDisable() {
    }
}
