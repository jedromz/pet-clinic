package com.jedromz.petclinic.error;

import lombok.Value;

@Value
public class BadDateException extends RuntimeException {
    private String name;
    private String key;

    public BadDateException(String name, String key) {
        this.name = name;
        this.key = key;
    }
}
