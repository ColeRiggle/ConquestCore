package com.craftersconquest.database;

import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.guild.SimpleLocation;
import com.craftersconquest.object.guild.Stockpile;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SQLGuildParser {

    private final static String ENTRY_DELIMITER = ",";

    public List<UUID> deserializeMembers(String value) {
        List<UUID> members = new ArrayList<>();
        for (String entry : value.split(ENTRY_DELIMITER)) {
            if (entry.length() > 0) {
                members.add(UUID.fromString(entry));
            }
        }

        return members;
    }

    public String serializeMembers(List<UUID> members) {
        StringBuilder value = new StringBuilder();
        Iterator<UUID> iterator = members.iterator();
        while (iterator.hasNext()) {
            UUID memberUUID = iterator.next();
            value.append(memberUUID.toString());
            if (iterator.hasNext()) {
                value.append(ENTRY_DELIMITER);
            }
        }

        return value.toString();
    }

    // Storage format:
    // <TYPE>:<TIER>

    public List<Forge> deserializeForges(String value) {
        List<Forge> forges = new ArrayList<>();
        for (String entry : value.split(ENTRY_DELIMITER)) {
            if (entry.length() > 0) {
                forges.add(createForgeFromEntry(entry));
            }
        }
        return forges;
    }

    private Forge createForgeFromEntry(String entry) {
        String[] components = entry.split(":");
        Type type = Type.valueOf(components[0]);
        Tier tier = Tier.valueOf(components[1]);

        String[] locationComponents = components[2].split("%");
        SimpleLocation location = new SimpleLocation(Integer.valueOf(locationComponents[0]),
                Integer.valueOf(locationComponents[1]),
                Integer.valueOf(locationComponents[2]));

        return new Forge(type, tier, location);
    }

    public String serializeForges(List<Forge> forges) {
        StringBuilder value = new StringBuilder();
        Iterator<Forge> iterator = forges.iterator();
        while(iterator.hasNext()) {
            Forge forge = iterator.next();
            value.append(forge.toString());
            if (iterator.hasNext()) {
                value.append(ENTRY_DELIMITER);
            }
        }

        return value.toString();
    }

    public Stockpile createStockpileFromEntry(String entry) {
        String[] components = entry.split(":");
        int resourceCapacity = Integer.valueOf(components[0]);
        int essenceCapacity = Integer.valueOf(components[1]);
        double grain = Double.valueOf(components[2]);
        double metal = Double.valueOf(components[3]);
        double crystal = Double.valueOf(components[4]);
        double essence = Double.valueOf(components[5]);
        return new Stockpile(resourceCapacity, essenceCapacity, grain, metal, crystal, essence);
    }
}
