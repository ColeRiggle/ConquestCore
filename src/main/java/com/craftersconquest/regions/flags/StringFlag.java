package com.craftersconquest.regions.flags;

public class StringFlag extends Flag<String> {

    private final String defaultValue;

    public StringFlag(String name) {
        this(name, null);
    }

    public StringFlag(String name, String defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefault() {
        return defaultValue;
    }

    @Override
    public String parseInput(String input) throws InvalidFlagFormat {
        return input;
    }

    @Override
    public String serialize(String value) {
        return value;
    }
}
