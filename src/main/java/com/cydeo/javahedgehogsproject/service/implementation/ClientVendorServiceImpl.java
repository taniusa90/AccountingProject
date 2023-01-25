package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.entity.ClientVendor;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.enums.ClientVendorType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.ClientVendorRepository;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public ClientVendorDto findById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id), new ClientVendorDto());
    }

    @Override
    public List<ClientVendorDto> findAllClients() {
        Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

        List<ClientVendor> clientList = clientVendorRepository.findAllByCompanyAndClientVendorTypeOrderByClientVendorName(company, ClientVendorType.CLIENT);

        return clientList.stream().map(client -> mapperUtil.convert(client, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> findAllVendors() {
        Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

        List<ClientVendor> vendorList = clientVendorRepository.findAllByCompanyAndClientVendorTypeOrderByClientVendorName(company, ClientVendorType.VENDOR);

        return vendorList.stream().map(vendor -> mapperUtil.convert(vendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> findAll() {
        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<ClientVendor> clientVendorList = clientVendorRepository.findAllByCompanyOrderByClientVendorTypeAscClientVendorNameAsc(company);

        return clientVendorList.stream().map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto create(ClientVendorDto clientVendorDto) {
        CompanyDto companyDto = securityService.getLoggedInCompany();
        clientVendorDto.setCompany(companyDto);

        ClientVendor createdClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());

        ClientVendor clientVendor = clientVendorRepository.save(createdClientVendor);

        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public ClientVendorDto update(ClientVendorDto clientVendorDto) {
        ClientVendorDto foundClientVendor = findById(clientVendorDto.getId());
        clientVendorDto.setCompany(foundClientVendor.getCompany());

        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());

        ClientVendor updatedClientVendor = clientVendorRepository.save(clientVendor);

        return mapperUtil.convert(updatedClientVendor, new ClientVendorDto());
    }

    @Override
    public void deleteById(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
        clientVendor.setIsDeleted(true);

        clientVendorRepository.save(clientVendor);
    }


    @Override
    public boolean checkIfThereIsAnyClientVendorWithSameNameAndType(String name, ClientVendorType type) {
        return clientVendorRepository.findAllByCompany(mapperUtil.convert(securityService.getLoggedInCompany(), new Company()))
                .stream()
                .filter(clientVendor -> clientVendor.getClientVendorType().equals(type))
                .anyMatch(clientVendor -> clientVendor.getClientVendorName().equalsIgnoreCase(name));
    }

}