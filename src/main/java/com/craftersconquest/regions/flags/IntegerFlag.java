package com.craftersconquest.regions.flags;

public class IntegerFlag extends Flag<Integer> {

    private final Integer defaultValue;

    public IntegerFlag(String name) {
        this(name, 0);
    }

    public IntegerFlag(String name, Integer defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    @Override
    public Integer parseInput(String input) throws InvalidFlagFormat {
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException exception) {
            throw new InvalidFlagFormat("Must be a valid integer");
        }
    }

    @Override
    public String serialize(Integer value) {
        return value.toString();
    }
}
