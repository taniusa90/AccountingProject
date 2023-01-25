package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {

    InvoiceDto findById(Long id);

    List<InvoiceDto> findAllInvoice(InvoiceType invoiceType);

    String generateInvoiceNoForPurchase(InvoiceType invoiceType, Long companyId);

    String createInvoiceNoForSalesInvoice(InvoiceType invoiceType, Long companyId);

    InvoiceDto getNewInvoice(InvoiceType invoiceType);

    InvoiceDto getNewSalesInvoice(InvoiceType invoiceType);

    void savePurchaseInvoice(InvoiceDto purchaseInvoice);

    InvoiceDto save(InvoiceDto invoiceDto);

    void update(InvoiceDto invoice);

    void delete(Long id);

    void deletePurchaseInvoice(Long id);

    void approvePurchaseInvoice(Long purchaseInvoiceId);

    void approveSalesInvoice(Long invoiceId);

    List<InvoiceDto> findAllApprovedInvoice(InvoiceStatus invoiceStatus);


}
