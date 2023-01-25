package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.enums.ClientVendorType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;
    @NotBlank
    @Size(min = 2, max = 50)
    private String clientVendorName;
    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
    @NotBlank
    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*")
    private String website;
    @NotNull
    private ClientVendorType clientVendorType;
    @Valid
    @NotNull
    private AddressDto address;
    private CompanyDto company;

}
