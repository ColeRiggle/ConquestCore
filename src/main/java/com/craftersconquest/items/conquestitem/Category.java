package com.craftersconquest.items.conquestitem;

public enum Category {
    VANILLA(null),
    CUSTOM_TOOL(null),
    FORGE(null),
    METAL_FORGE(FORGE),
    GRAIN_FORGE(FORGE),
    CRYSTAL_FORGE(FORGE),
    ESSENCE_FORGE(FORGE),
    HORSE(null);

    private final Category parent;

    Category(Category parent) {
        this.parent = parent;
    }

    public boolean isSubcategoryOf(Category category) {
        Category currentCategory = this;
        while (currentCategory != null) {
            if (currentCategory == category) {
                return true;
            }
            currentCategory = currentCategory.parent;
        }

        return false;
    }
}
