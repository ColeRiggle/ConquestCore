package com.craftersconquest.database;

import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SQLGuildParser {

    private final static String ENTRY_DELIMITER = ",";

    public List<UUID> deserializeMembers(String value) {
        List<UUID> members = new ArrayList<>();
        for (String entry : value.split(ENTRY_DELIMITER)) {
            members.add(UUID.fromString(entry));
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
            forges.add(createForgeFromEntry(entry));
        }
        return forges;
    }

    private Forge createForgeFromEntry(String entry) {
        String[] components = entry.split(":");
        Type type = Type.valueOf(components[0]);
        Tier tier = Tier.valueOf(components[1]);
        return new Forge(type, tier);
    }

    public String serializeForges(List<Forge> forges) {
        StringBuilder value = new StringBuilder();
        Iterator<Forge> iterator = forges.iterator();
        while(iterator.hasNext()) {
            Forge forge = iterator.next();
            value.append(forge.getType().toString() + ":" + forge.getTier().toString());
            if (iterator.hasNext()) {
                value.append(ENTRY_DELIMITER);
            }
        }

        return value.toString();
    }
}
