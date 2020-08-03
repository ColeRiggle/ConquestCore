package com.craftersconquest.regions.flags;

public class StateFlag extends Flag<StateFlag.State>{

    public enum State {
        ALLOW,
        DENY
    }

    private final StateFlag.State defaultValue;

    public StateFlag(String name) {
        this(name, State.DENY);
    }

    public StateFlag(String name, State defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    @Override
    public State getDefault() {
        return defaultValue;
    }

    @Override
    public State parseInput(String input) throws InvalidFlagFormat {
        try {
            return State.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidFlagFormat("State must be ALLOW or DENY");
        }
    }

    @Override
    public String serialize(State value) {
        return value.toString();
    }
}
