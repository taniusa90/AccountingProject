package com.cydeo.javahedgehogsproject.converter;


import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDtoConverter implements Converter<String, InvoiceDto> {

    InvoiceService invoiceService;

    public InvoiceDtoConverter(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDto convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return invoiceService.findById(Long.parseLong(source));
    }

}
