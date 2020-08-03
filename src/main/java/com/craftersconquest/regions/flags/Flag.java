package com.craftersconquest.regions.flags;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public abstract class Flag<T> {

    private static final Pattern VALID_NAME = Pattern.compile("^[:A-Za-z0-9\\-]{1,40}$");
    private final String name;

    protected Flag(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    @Nullable
    public T getDefault() {
        return null;
    }

    public abstract T parseInput(String input) throws InvalidFlagFormat;

    public abstract String serialize(T value);

    public static boolean isValidName(String name) {
        return VALID_NAME.matcher(name).matches();
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' + "name='" + name + '\'' + '}';
    }
}
