package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    @NotBlank
    @Size(min=2, max=100)
    private String name;
    private Integer quantityInStock;
    @NotNull
    @Min(1)
    private Integer lowLimitAlert;
    @NotNull
    private ProductUnit productUnit;
    @Valid
    @NotNull
    private CategoryDto category;

}
