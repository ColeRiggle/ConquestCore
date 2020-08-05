package com.craftersconquest.regions.flags;

public class EnumFlag<T extends Enum<T>> extends Flag<T> {

    private final Class<T> enumClass;

    public EnumFlag(String name, Class<T> enumClass) {
        super(name);
        this.enumClass = enumClass;
    }

    @Override
    public T parseInput(String input) throws InvalidFlagFormat {
        try {
            return findValue(input);
        } catch (IllegalArgumentException exception) {
            throw new InvalidFlagFormat("Unknown value '" + input + "' in " + enumClass.getName());
        }
    }

    private T findValue(String input) throws IllegalArgumentException {
        if (input != null) {
            input = input.toUpperCase();
        }

        return Enum.valueOf(enumClass, input);
    }

    @Override
    public String serialize(Enum value) {
        return value.toString();
    }
}
