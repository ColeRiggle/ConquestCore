package com.craftersconquest.regions.flags;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Flags {

    private static final Map<String, Flag> INBUILT_FLAGS_MAP = new HashMap<>();

    public static final StateFlag BLOCK_PLACE = register(new StateFlag("block-place"));
    public static final StateFlag BLOCK_BREAK = register(new StateFlag("block-break"));
    public static final StateFlag USE = register(new StateFlag("use"));
    public static final StateFlag INTERACT = register(new StateFlag("interact"));
    public static final StateFlag PVP = register(new StateFlag("pvp"));
    public static final StateFlag INDIVIDUAL_PVP = register(new StateFlag("individual-pvp"));
    public static final StateFlag DAMAGE_ANIMALS = register(new StateFlag("damage-animals", StateFlag.State.ALLOW));
    public static final StateFlag RIDE_HORSE = register(new StateFlag("ride-horse", StateFlag.State.ALLOW));
    public static final StateFlag CHEST_ACCESS = register(new StateFlag("chest-access", StateFlag.State.ALLOW));
    public static final StateFlag POTION_SPLASH = register(new StateFlag("potion-splash"));

    public static final StateFlag ITEM_PICKUP = register(new StateFlag("item-pickup", StateFlag.State.ALLOW));
    public static final StateFlag ITEM_DROP = register(new StateFlag("item-drop", StateFlag.State.ALLOW));
    public static final StateFlag EXP_DROPS = register(new StateFlag("exp-drops", StateFlag.State.ALLOW));
    public static final StateFlag BLOCKLIST = register(new StateFlag("blocklist", StateFlag.State.ALLOW));

    public static final BooleanFlag NOTIFY_ENTER = register(new BooleanFlag("notify-enter"));
    public static final BooleanFlag NOTIFY_LEAVE = register(new BooleanFlag("notify-leave"));

    public static final StringFlag GREET_MESSAGE = register(new StringFlag("greet-message"));
    public static final StringFlag FAREWELL_MESSAGE = register(new StringFlag("farewell-message"));

    public static final Map<String, Flag> INBUILT_FLAGS = Collections.unmodifiableMap(INBUILT_FLAGS_MAP);
    public static final Set<Flag> INBUILT_FLAGS_SET = Sets.newHashSet(INBUILT_FLAGS.values());

    private static <T extends Flag<?>> T register(final T flag) {
        INBUILT_FLAGS_MAP.put(flag.getName(), flag);
        return flag;
    }

    public static Flag<?> matchFlag(String id) {
        return INBUILT_FLAGS_MAP.get(id.toLowerCase());
    }

    public static Set<Flag> getFlags() {
        return INBUILT_FLAGS_SET;
    }
}
