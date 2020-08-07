package com.craftersconquest.regions.command.subcommand;

public interface SelectionSubcommand extends Subcommand {

    default boolean requiresSelection() {
        return false;
    }
}
