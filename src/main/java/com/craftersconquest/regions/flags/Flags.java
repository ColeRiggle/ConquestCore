package com.craftersconquest.regions.flags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Flags {

    private static final List<String> INBUILT_FLAGS_LIST = new ArrayList<>();
    public static final List<String> INBUILT_FLAGS = Collections.unmodifiableList(INBUILT_FLAGS_LIST);

    public static final StateFlag BLOCK_PLACE = register(new StateFlag("block-place"));
    public static final StateFlag BLOCK_BREAK = register(new StateFlag("block-break"));
    public static final StateFlag USE = register(new StateFlag("use"));
    public static final StateFlag INTERACT = register(new StateFlag("interact"));
    public static final StateFlag PVP = register(new StateFlag("pvp"));
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

    public static final StringFlag GREET_MESSAGE = register(new StringFlag("greeting"));
    public static final StringFlag FAREWELL_MESSAGE = register(new StringFlag("farewell"));

    public static <T extends Flag<?>> T register(final T flag) {
        INBUILT_FLAGS_LIST.add(flag.getName());
        return flag;
    }

}
