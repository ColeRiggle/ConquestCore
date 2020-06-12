package com.craftersconquest.time;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.object.Component;
import com.craftersconquest.util.NumbersUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class TimeManager implements Component {

    private final ConquestCore instance;
    private Date date;
    private String time;

    public TimeManager(ConquestCore instance) {
        this.instance = instance;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    private void generateTime() {

        World world = Bukkit.getWorld(Settings.RPG_WORLD_NAME);
        String ending = "";
        String symbol = "";

        long time = world.getTime();

        if (time <= 18000) {
            time += 6000;
        } else {
            time -= 18000;
        }

        long hours = (int) (time / 1000);
        long minutes = (int) ((time - (1000 * hours)) / 20);

        minutes = formatMinutes(minutes);

        if (isNight(hours)) {
            symbol += ChatColor.AQUA + "☽";
        } else {
            symbol += ChatColor.GOLD + "☀";
        }

        if (hours > 18) {
            hours -= 12;
            ending = "pm ";
        } else {
            ending = "am ";
        }

        String minString = String.valueOf(minutes);
        if (minString.equals("0")) {
            minString = "00";
        }

        this.time = ChatColor.GRAY + "" + hours + ":" + minString + ending + symbol;
    }

    private long formatMinutes(long minutes) {
        return Math.round(minutes / 10.0) * 10;
    }

    private boolean isNight(long hours) {
       return (hours < 6 || hours > 22);
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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                checkForDateChangeAndUpdate();
            }
        }, 0, 200);
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
