package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    UserDto getLoggedInUser();
    CompanyDto getLoggedInCompany();

}
