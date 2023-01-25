package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.enums.CompanyStatus;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.CompanyRepository;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;


    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), new CompanyDto());
    }

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companyList = companyRepository.findAllByIdIsNotOrderByCompanyStatusAscTitleAsc(1L);
        return companyList.stream().map(company -> mapperUtil.convert(company, new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        Company company = mapperUtil.convert(companyDto, new Company());

        Company createdCompany = companyRepository.save(company);

        return mapperUtil.convert(createdCompany, new CompanyDto());
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) {
        CompanyDto foundCompany = findById(companyDto.getId());
        companyDto.setCompanyStatus(foundCompany.getCompanyStatus());

        Company company = mapperUtil.convert(companyDto, new Company());

        Company createdCompany = companyRepository.save(company);

        return mapperUtil.convert(createdCompany, new CompanyDto());
    }

    @Override
    public void activate(Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(company);
    }

    @Override
    public void deactivate(Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);
    }

    @Override
    public List<CompanyDto> findAllByUsers() {
        CompanyDto loggedInCompany = securityService.getLoggedInCompany();

        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return companyRepository.findAll().stream()
                    .filter(company -> !company.getTitle().equals(loggedInCompany.getTitle()))
                    .map(company -> mapperUtil.convert(company, new CompanyDto()))
                    .collect(Collectors.toList());
        } else {
            return companyRepository.findAll().stream()
                    .filter(company -> company.getTitle().equals(loggedInCompany.getTitle()))
                    .map(company -> mapperUtil.convert(company, new CompanyDto()))
                    .collect(Collectors.toList());
        }

    }

    @Override
    public boolean isCompanyNameUnique(String companyTitle) {
        return companyRepository.findAll().stream().anyMatch(company -> company.getTitle().equalsIgnoreCase(companyTitle));
    }

}
