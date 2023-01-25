package com.cydeo.javahedgehogsproject.enums;

import lombok.Getter;

@Getter
public enum InvoiceType {
    PURCHASE("Purchase"), SALES("Sales");

    private final String value;

    InvoiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
