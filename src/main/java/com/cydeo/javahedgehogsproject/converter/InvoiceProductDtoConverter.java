package com.cydeo.javahedgehogsproject.converter;

import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductDtoConverter implements Converter<String, InvoiceProductDto> {

    InvoiceProductService invoiceProductService;

    public InvoiceProductDtoConverter(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDto convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return invoiceProductService.findById(Long.parseLong(source));
    }

}
