package com.craftersconquest.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SQLGuildParser {

    private final static String ENTRY_DELIMITER = ",";

    public List<UUID> readMembers(String value) {
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
}
