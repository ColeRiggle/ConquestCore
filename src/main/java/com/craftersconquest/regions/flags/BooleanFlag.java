package com.craftersconquest.regions.flags;

public class BooleanFlag extends Flag<Boolean> {

    private final Boolean defaultValue;

    public BooleanFlag(String name) {
        this(name, false);
    }

    public BooleanFlag(String name, Boolean defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    @Override
    public String serialize(Boolean value) {
        return value.toString();
    }

    @Override
    public Boolean parseInput(String input) throws InvalidFlagFormat {
        if (input.equalsIgnoreCase("true")) {
            return true;
        } else if (input.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new InvalidFlagFormat("Not a true/false value: " + input);
        }
    }
}
