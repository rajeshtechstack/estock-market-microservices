package com.estock.market.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse<T> {
    String message;
    private T data;

    public GenericResponse(String message,T data) {
        this.message = message;
        this.data = data;
    }
    public GenericResponse(String message) {
        this.message = message;
    }
}
