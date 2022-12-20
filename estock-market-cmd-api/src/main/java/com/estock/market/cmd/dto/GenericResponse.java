package com.estock.market.cmd.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericResponse<T> {
    String message;
    private T data;

    public GenericResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public GenericResponse(String message) {
        this.message = message;
    }
}
