package com.cydeo.javahedgehogsproject.converter;

import com.cydeo.javahedgehogsproject.dto.RoleDto;
import com.cydeo.javahedgehogsproject.service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDto> {
    RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return roleService.findById(Long.parseLong(source));

    }

}