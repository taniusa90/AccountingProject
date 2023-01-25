package com.cydeo.javahedgehogsproject.dto;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.validation.constraints.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String description;
    private CompanyDto company;
    private boolean hasProduct;


}
