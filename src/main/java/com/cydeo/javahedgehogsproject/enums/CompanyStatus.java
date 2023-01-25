package com.cydeo.javahedgehogsproject.enums;


public enum CompanyStatus {

    ACTIVE ("Active"), PASSIVE("Passive");

    private String value;

    CompanyStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
