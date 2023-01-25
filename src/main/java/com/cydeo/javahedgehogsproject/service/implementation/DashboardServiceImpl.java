package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.*;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;
    private final CurrencyClient currencyClient;
    private final  InvoiceProductService invoiceProductService;
    private final SecurityService securityService;
    private final InvoiceProductRepository invoiceProductRepository;

    public DashboardServiceImpl(InvoiceService invoiceService, CurrencyClient currencyClient, InvoiceProductService invoiceProductService, SecurityService securityService, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceService = invoiceService;
        this.currencyClient = currencyClient;
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public UsdDto getCurrency() {
        CurrencyDto currencies = currencyClient.getCurrencies();
        return currencies.getUsd();
    }

    @Override
    public Map<String, BigDecimal> dashboardNumbers(){

        Map<String, BigDecimal> totalNumbersToDisplay=new HashMap<>();
        totalNumbersToDisplay.put("totalCost", calculateTotalCost());
        totalNumbersToDisplay.put("totalSales", calculateTotalSales());
        totalNumbersToDisplay.put("Profit / Loss", calculateProfitLoss());

        return totalNumbersToDisplay;
    }


    @Override
    public BigDecimal calculateTotalCost() {
        BigDecimal total=invoiceService.findAllInvoice(InvoiceType.PURCHASE).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .map(InvoiceDto::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    @Override
    public BigDecimal calculateTotalSales() {
        BigDecimal total=invoiceService.findAllInvoice(InvoiceType.SALES).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .map(InvoiceDto::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    @Override
    public BigDecimal calculateProfitLoss() {

        BigDecimal profitLoss = BigDecimal.ZERO;
       CompanyDto company= securityService.getLoggedInCompany();

        List<InvoiceProduct> listOfProductsEntity=invoiceProductRepository.findAllByInvoice_Company_Id(company.getId());

        for (InvoiceProduct invoiceProduct : listOfProductsEntity) {
            profitLoss = profitLoss.add(invoiceProduct.getProfitLoss());
        }
        return  profitLoss;
    }

}
