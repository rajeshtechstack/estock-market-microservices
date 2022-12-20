package com.estock.market.controller;

import org.apache.commons.codec.binary.Base64;

public class TestToken {
    public static void main(String[] args) {
        String signkey = "eStockMarketClientId:eStockMarketClientSecret";
        String sceretKey= new String(Base64.encodeBase64(signkey.getBytes()));
        System.out.println(sceretKey);

    }
}
