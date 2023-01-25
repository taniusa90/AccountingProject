package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDto> findAllById(Long id);

    List<InvoiceProductDto> findAllInvoiceProducts(Long invoiceId);

    BigDecimal totalTax(Long invoiceId);

    BigDecimal totalPriceWithoutTax(Long invoiceId);

    void savePurchaseProductByInvoiceId(InvoiceProductDto invoiceProduct, Long id);

    void deletePurchaseProduct(Long productId);

    void approvePurchaseInvoice(Long purchaseInvoiceId);

    void saveProduct(InvoiceProductDto invoiceProductDto, Long id);

    void deleteSalesInvoiceProduct(Long invoiceProductId);

    boolean hasEnoughProductQuantityInStock(Long invoiceId);

    void calculateProfitLossForSoldInvoiceProduct(Long invoiceId);

    List<InvoiceProduct> getAllApprovedInvoiceProductsByCompany(CompanyDto company);

    List<InvoiceProduct> findAllMonthlyProfitLoss();

    void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto);

    InvoiceProductDto findById(Long id);

}
