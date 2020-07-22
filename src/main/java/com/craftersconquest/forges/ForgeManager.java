package com.craftersconquest.forges;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.core.Settings;
import com.craftersconquest.items.conquestitem.Category;
import com.craftersconquest.items.conquestitem.ItemUtil;
import com.craftersconquest.messaging.Messaging;
import com.craftersconquest.object.Component;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.object.guild.SimpleLocation;
import com.craftersconquest.player.ConquestPlayer;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import me.arasple.mc.trhologram.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ForgeManager implements Component {

    private final ConquestCore instance;
    private final ForgeConverter forgeConverter;
    private final ArrayList<Forge> forges;
    private final ArrayList<Player> loadedPlayers;

    private final HashMap<Guild, List<Hologram>> holograms;

    public ForgeManager(ConquestCore instance) {
        this.instance = instance;
        forgeConverter = new ForgeConverter();
        holograms = new HashMap<>();
        forges = new ArrayList<>();
        loadedPlayers = new ArrayList<>();
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack blockPlaced = event.getItemInHand();
        if (ItemUtil.getCategory(blockPlaced).isSubcategoryOf(Category.FORGE)) {
            Player player = event.getPlayer();
            Forge placedForge = forgeConverter.getForgeFromItemStack(blockPlaced);
            checkCompatibilityAndAddForge(event, player, placedForge);
        }
    }

    private void checkCompatibilityAndAddForge(BlockPlaceEvent event, Player player, Forge forge) {
        Guild playerGuild = instance.getCacheManager().getConquestPlayer(player).getGuild();

        if (!player.getWorld().getName().contains("guild")) {
            Messaging.sendErrorMessage(player, "Forges can only be placed at your guild.");
        } else if (playerGuild == null) {
            Messaging.sendErrorMessage(player, "You must be in a guild to place a forge.");
        } else if (!hasCapacityForForge(forge, playerGuild)) {
            Messaging.sendErrorMessage(player, "You cannot place any more forges of that type.");
        } else {
            addAndPlaceForge(event.getBlockPlaced().getLocation(), playerGuild, forge);
            return;
        }

        // If any of the checks fail, cancel the event
        event.setCancelled(true);
    }

    private boolean hasCapacityForForge(Forge potentialForge, Guild guild) {
        int typeCount = 0;
        for (Forge forge : guild.getForges()) {
            if (forge.getType() == potentialForge.getType()) {
                typeCount++;
            }
        }

        return typeCount + 1 <= Settings.MAX_FORGES_PER_TYPE;
    }

    private void addAndPlaceForge(Location location, Guild guild, Forge forge) {
        forge.setLocation(SimpleLocation.fromLocation(location));
        forges.add(forge);
        guild.addForge(forge);
        List<Hologram> currentGuildForges = holograms.get(guild);
        Hologram hologram = getHologramForForge(guild, forge);
        currentGuildForges.add(hologram);
        displayHologramToOnlineGuildMembers(hologram, guild);
    }

    private Hologram getHologramForForge(Guild guild, Forge forge) {
        String firstLine = forge.getType().getDisplayName() + " Forge";
        String secondLine = ChatColor.AQUA + "Tier " + forge.getTier().toString();
        String thirdLine = ChatColor.RED + "Production rate: " + forge.getTier().getProductionRate();
        return TrHologramAPI.createHologram(instance, "forgeHolo", getOptimalHologramLocation(guild, forge), firstLine, secondLine, thirdLine);
    }

    private Location getOptimalHologramLocation(Guild guild, Forge forge) {
        Location location = forge.getLocation().getLocation(instance.getGuildManager().getWorld(guild));
        return location.add(0.5, 2, 0.5);
    }

    private void displayHologramToOnlineGuildMembers(Hologram hologram, Guild guild) {
        for (Player player : guild.getOnlinePlayers()) {
            hologram.display(player);
        }
    }

    public void setupGuildHologramsForPlayer(Player player) {
        if (!loadedPlayers.contains(player)) {
            UUID playerUUID = player.getUniqueId();
            ConquestPlayer conquestPlayer = instance.getCacheManager().getConquestPlayer(playerUUID);
            if (conquestPlayer.hasGuild()) {
                Guild guild = conquestPlayer.getGuild();
                for (Hologram hologram : holograms.get(guild)) {
                    hologram.display(Bukkit.getPlayer(playerUUID));
                }
            }
            loadedPlayers.add(player);
        }
    }

    public void loadHologramsForGuild(Guild guild) {
        Bukkit.getLogger().info("Loading holograms...");
        holograms.put(guild, getHologramsForGuild(guild));
    }

    private List<Hologram> getHologramsForGuild(Guild guild) {
        List<Hologram> holograms = new ArrayList<>();
        for (Forge forge : guild.getForges()) {
            holograms.add(getHologramForForge(guild, forge));
        }
        return holograms;
    }

    public void unloadHologramsForGuild(Guild guild) {
        for (Hologram hologram : holograms.get(guild)) {
            hologram.delete();
        }

        holograms.remove(guild);
    }

    public void onPlayerQuit(Player player) {
        loadedPlayers.remove(player);
    }

    public void onPistonEvent(BlockPistonEvent event, List<Block> blocks) {
        if (blocks.size() >= 1) {
            Location location = blocks.get(0).getLocation();
            String worldName = location.getWorld().getName();

            if (worldName.contains("guild")) {
                int cutoffIndex = worldName.indexOf('_');
                String guildName = worldName.substring(cutoffIndex + 1);
                Guild guild = instance.getGuildManager().getGuild(guildName);
                World world = instance.getGuildManager().getWorld(guild);

                for (Block block : blocks) {
                    for (Forge forge : guild.getForges()) {
                        if (forge.getLocation().getLocation(world).equals(block.getLocation())) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
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
