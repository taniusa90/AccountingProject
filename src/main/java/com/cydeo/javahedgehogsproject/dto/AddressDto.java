package com.cydeo.javahedgehogsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String addressLine1;
    @NotEmpty
    @Size(max = 100)
    private String addressLine2;
    @NotBlank
    @Size(max = 50, min = 2)
    private String city;
    @NotBlank
    @Size(max = 50, min = 2)
    private String state;
    @NotBlank
    @Size(max = 50, min = 2)
    private String country;
    @NotBlank
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
    private String zipCode;

}
