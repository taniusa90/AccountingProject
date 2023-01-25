package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.ClientVendor;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {
    List<ClientVendor> findAllByCompany(Company company);
    List<ClientVendor> findAllByCompanyOrderByClientVendorTypeAscClientVendorNameAsc(Company company);
    List<ClientVendor> findAllByCompanyAndClientVendorTypeOrderByClientVendorName(Company company, ClientVendorType clientVendorType);
}
