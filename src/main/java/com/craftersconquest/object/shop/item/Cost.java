package com.craftersconquest.object.shop.item;

import com.craftersconquest.object.shop.currency.Currency;

public class Cost {

    private final Currency currency;
    private final double price;

    private Cost(Currency currency, double price) {
        this.currency = currency;
        this.price = price;
    }

    public static Cost fromCurrencyAndPrice(Currency currency, double price) {
        return new Cost(currency, price);
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getPrice() {
        return price;
    }
}
