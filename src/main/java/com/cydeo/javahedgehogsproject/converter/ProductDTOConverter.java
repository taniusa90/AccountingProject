package com.cydeo.javahedgehogsproject.converter;


import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOConverter implements Converter<String, ProductDto> {

    ProductService productService;

    public ProductDTOConverter(ProductService productService) {
        this.productService=productService;
    }

    @Override
    public ProductDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return productService.findById(Long.parseLong(source));

    }
}
