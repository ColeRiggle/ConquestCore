package com.craftersconquest.horses;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.horse.Horse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HorseManager implements Component {

    private final ConquestCore instance;
    private final HorseConverter converter;
    private final ArrayList<HorseRide> rides;

    private static final double XP_GAIN_PER_SECOND = 30;

    public HorseManager(ConquestCore instance) {
        this.instance = instance;
        this.converter = new HorseConverter();
        this.rides = new ArrayList<>();
    }

    private void scheduleTimeTracker() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            for (HorseRide ride : rides) {
                Player player = ride.getPlayer();
                if (player.getVehicle() != null && player.getVehicle().equals(ride.getHorseEntity())) {
                    addHorseXp(ride, XP_GAIN_PER_SECOND);
                }
            }
        }, 20L, 20L);
    }

    private void addHorseXp(HorseRide ride, double amount) {
        Horse horse = ride.getHorse();

        int beginningXp = (int) horse.getXp();
        horse.addXp(amount);
        int endingXp = (int) horse.getXp();

        if (beginningXp != endingXp) {
            updateRide(ride);
        }
    }

    public void updateRide(HorseRide ride) {
        Inventory inventory = ride.getPlayer().getInventory();
        ItemStack triggerItem = ride.getTriggerItem();
        converter.updateHorseEntity(ride.getHorse(), ride.getHorseEntity());
        for (Map.Entry entry : inventory.all(triggerItem).entrySet()) {
            triggerItem = converter.createItemStackFromHorse(ride.getHorse());
            ride.setTriggerItem(triggerItem);
            inventory.setItem((Integer) entry.getKey(), triggerItem);
        }
    }

    // Todo
    // Add support for horse items

    public void onHorseClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack horseItemStack = event.getItem();

        for (Iterator<HorseRide> iterator = rides.iterator(); iterator.hasNext();) {
            HorseRide ride = iterator.next();
            if (ride.getTriggerItem().equals(horseItemStack)) {
                endRide(ride);
                iterator.remove();
                return;
            }
        }

        Block targetBlock =  event.getPlayer().getTargetBlock(null, 100);
        Location spawnLocation;

        if (targetBlock == null) {
            spawnLocation = event.getPlayer().getLocation();
        } else {
            spawnLocation = targetBlock.getLocation();
        }

        spawnLocation.add(0, 1, 0);

        Horse horse = converter.getHorseFromItemStack(horseItemStack);
        org.bukkit.entity.Horse horseEntity = converter.spawnHorseEntity(spawnLocation, horse, player);

        rides.add(new HorseRide(player, horseItemStack, horse, horseEntity));
    }

    private void endRide(HorseRide ride) {
        ride.getHorseEntity().remove();
    }

    public void endPlayerRides(Player player) {
        for (Iterator<HorseRide> iterator = rides.iterator(); iterator.hasNext();) {
            HorseRide ride = iterator.next();
            if (ride.getPlayer().equals(player)) {
                endRide(ride);
                iterator.remove();
            }
        }
    }

    @Override
    public void onEnable() {
        scheduleTimeTracker();
    }

    @Override
    public void onDisable() {
        for (Iterator<HorseRide> iterator = rides.iterator(); iterator.hasNext();) {
            HorseRide ride = iterator.next();
            endRide(ride);
            iterator.remove();
        }
    }

    public void onSaddleDrop(Player player, ItemStack item) {
        for (Iterator<HorseRide> iterator = getActiveRides(player).iterator(); iterator.hasNext();) {
            HorseRide ride = iterator.next();
            if (ride.getTriggerItem().getItemMeta().equals(item.getItemMeta())) {
                endRide(ride);
                iterator.remove();
            }
        }
    }

    private List<HorseRide> getActiveRides(Player player) {
        List<HorseRide> activeRides = new ArrayList<>();

        for (HorseRide ride : rides) {
            if (ride.getPlayer().equals(player)) {
                activeRides.add(ride);
            }
        }

        return activeRides;
    }

}
