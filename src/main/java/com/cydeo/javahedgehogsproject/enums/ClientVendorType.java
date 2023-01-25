package com.cydeo.javahedgehogsproject.enums;

public enum ClientVendorType {

    CLIENT ("Client"),
    VENDOR ("Vendor");

    private final String value;

    public String getValue() {
        return value;
    }

    ClientVendorType(String value) {
        this.value = value;
    }

}
