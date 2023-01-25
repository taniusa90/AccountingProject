package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto findById(long id);
    List<RoleDto> findAll();
}
