package com.craftersconquest.items.conquestitem;

public enum Category {
    VANILLA(null),
    FORGE(null),
    METAL_FORGE(FORGE),
    GRAIN_FORGE(FORGE),
    CRYSTAL_FORGE(FORGE),
    ESSENCE_FORGE(FORGE);

    private final Category parent;

    Category(Category parent) {
        this.parent = parent;
    }

    public boolean isSubcategoryOf(Category category) {
        Category currentParent = this.parent;
        while (currentParent != null) {
            if (currentParent == category) {
                return true;
            }
            currentParent = currentParent.parent;
        }

        return false;
    }
}
