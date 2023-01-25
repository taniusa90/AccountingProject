package com.cydeo.javahedgehogsproject.entity;

import com.cydeo.javahedgehogsproject.enums.ClientVendorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients_vendors")
@Where(clause = "is_deleted=false")
public class ClientVendor extends BaseEntity{

    String clientVendorName;
    String phone;
    String website;
    @Enumerated(EnumType.STRING)
    ClientVendorType clientVendorType;
    @OneToOne(cascade = CascadeType.ALL)
    Address address;
    @ManyToOne
    Company company;

}
