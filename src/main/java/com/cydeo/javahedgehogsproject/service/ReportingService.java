package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportingService {

    Map<String, BigDecimal> profitMonthlyLoss();

    List<InvoiceProductDto> getInvoiceProductsOfApprovedInvoices();

}
