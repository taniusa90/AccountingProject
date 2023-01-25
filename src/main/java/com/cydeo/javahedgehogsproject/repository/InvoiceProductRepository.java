package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoiceId(Long invoice_id);

    List<InvoiceProduct> findAllByInvoice_CompanyAndInvoiceId(Company company, Long id);

    List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_CompanyOrderByInvoice_LastUpdateDateTimeDesc(InvoiceStatus invoiceStatus, Company company);

    List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_InvoiceTypeAndInvoice_CompanyAndProduct_IdOrderByInvoice_IdAscIdAsc(InvoiceStatus invoiceStatus, InvoiceType invoiceType, Company company, Long product_id);

    List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_InvoiceTypeAndInvoice_CompanyOrderByInvoice_DateDesc(InvoiceStatus invoiceStatus, InvoiceType invoiceType, Company company);

    List<InvoiceProduct> findAllByInvoice_Company_Id(Long companyId);

}
