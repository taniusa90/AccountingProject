package com.cydeo.javahedgehogsproject.entity;

import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
//@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity{

    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private LocalDate date;
    @ManyToOne
    private ClientVendor clientVendor;
    @ManyToOne
    private Company company;

}