package com.estock.market.security;

public interface PasswordEncoder {
    String hashPassword(String password);
}
