package com.cydeo.javahedgehogsproject.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eur",
        "cad",
        "gbp",
        "inr",
        "jpy"
})
@Generated("jsonschema2pojo")
public class UsdDto {

    @JsonProperty("eur")
    private Double eur;

    @JsonProperty("cad")
    private Double cad;

    @JsonProperty("gbp")
    private Double gbp;

    @JsonProperty("inr")
    private Double inr;

    @JsonProperty("jpy")
    private Double jpy;
}