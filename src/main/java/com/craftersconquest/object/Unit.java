package com.craftersconquest.object;

public class Unit {

    private final String singular;
    private final String plural;

    public Unit(String singular, String plural) {
        this.singular = singular;
        this.plural = plural;
    }

    public String getSingular() {
        return singular;
    }

    public String getPlural() {
        return plural;
    }

    public String getUnit(double quantity) {
        return quantity == 1 ? singular : plural;
    }
}
