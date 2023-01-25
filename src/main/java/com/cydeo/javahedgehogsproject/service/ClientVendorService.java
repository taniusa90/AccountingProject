package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById(Long id);

    List<ClientVendorDto> findAllClients();

    List<ClientVendorDto> findAllVendors();

    List<ClientVendorDto> findAll();

    ClientVendorDto create(ClientVendorDto clientVendorDto);

    ClientVendorDto update(ClientVendorDto clientVendorDto);

    void deleteById(Long id);

    boolean checkIfThereIsAnyClientVendorWithSameNameAndType(String name, ClientVendorType type);


}
