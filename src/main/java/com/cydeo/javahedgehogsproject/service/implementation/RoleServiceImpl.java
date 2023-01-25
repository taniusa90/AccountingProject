package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.RoleDto;
import com.cydeo.javahedgehogsproject.entity.Role;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.RoleRepository;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public RoleDto findById(long id) {
        Role role = roleRepository.findById(id).get();
        return mapperUtil.convert(role, new RoleDto());
    }

    @Override
    public List<RoleDto> findAll() {
        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return roleRepository.findAll().stream()
                    .filter(role -> role.getDescription().equals("Admin"))
                    .map(role -> mapperUtil.convert(role, new RoleDto()))
                    .collect(Collectors.toList());
        } else {
            return roleRepository.findAll().stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDto()))
                    .collect(Collectors.toList());
        }

    }
}
