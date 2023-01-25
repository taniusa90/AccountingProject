package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long id);
    List<CompanyDto> findAll();
    CompanyDto create(CompanyDto companyDto);
    CompanyDto update(CompanyDto companyDto);
    void activate(Long id);
    void deactivate(Long id);
    List<CompanyDto> findAllByUsers();
    boolean isCompanyNameUnique(String companyTitle);

}
