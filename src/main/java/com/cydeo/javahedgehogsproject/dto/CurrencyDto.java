
package com.cydeo.javahedgehogsproject.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "usd"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
@ToString
public class CurrencyDto {

    @JsonProperty("date")
    private String date;
    @JsonProperty("usd")
    private UsdDto usd;


}
