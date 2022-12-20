package com.estock.market.cmd.models;

public enum ExchangeType {
    NSE("NSE"), BSE("BSE");
    private final String name;
    ExchangeType (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
