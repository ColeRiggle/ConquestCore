package com.craftersconquest.regions.flags;

import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

public class SetFlag<T> extends Flag<Set<T>> {

    private Flag<T> subFlag;

    public SetFlag(String name, Flag<T> subFlag) {
        super(name);
        this.subFlag = subFlag;
    }

    @Override
    public Set<T> parseInput(String input) throws InvalidFlagFormat {
        if (input.length() == 0) {
            return Sets.newHashSet();
        } else {
            Set<T> entries = Sets.newHashSet();

            for (String entry : input.split(",")) {
                entries.add(subFlag.parseInput(entry));
            }

            return entries;
        }
    }

    @Override
    public String serialize(Set<T> value) {
        StringBuilder serialization = new StringBuilder();

        Iterator<T> iterator = value.iterator();
        while (iterator.hasNext()) {
            serialization.append(subFlag.serialize(iterator.next()));
            if (iterator.hasNext()) {
                serialization.append(",");
            }
        }

        return serialization.toString();
    }
}
