package com.cydeo.javahedgehogsproject.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductDto {

    private Long id;
    @NotNull(message = "Quantity is Required Field")
    @Range(min = 1, max = 100, message = "Quantity should be less than 100")
    private Integer quantity;
    @NotNull(message = "Price is Required Field")
    @Range(min = 1, message = "Price should be at least $1")
    private BigDecimal price;
    @NotNull(message = "Tax is Required Field")
    @Range(min = 5, max = 20, message = "Tax should be between 5% and 20%")
    private BigDecimal tax;
    private BigDecimal total;
    private BigDecimal profitLoss;
    private Integer remainingQty;
    private InvoiceDto invoice;
    @NotNull(message = "Product is Required Field")
    private ProductDto product;

}
