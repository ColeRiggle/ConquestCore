package com.craftersconquest.object;

public class Cost {

    private final Currency currency;
    private final double buyPrice;
    private final double sellPrice;

    private Cost(Currency currency, double buyPrice, double sellPrice) {
        this.currency = currency;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Cost coinCostFromBuyAndSellPrice(double buyPrice, double sellPrice) {
        return null;
    }
}
