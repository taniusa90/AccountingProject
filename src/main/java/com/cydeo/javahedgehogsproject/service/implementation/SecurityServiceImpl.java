package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.entity.User;
import com.cydeo.javahedgehogsproject.entity.common.UserPrincipal;
import com.cydeo.javahedgehogsproject.repository.UserRepository;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CompanyService companyService;

    public SecurityServiceImpl(UserRepository userRepository, @Lazy UserService userService, @Lazy CompanyService companyService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }

    @Override
    public UserDto getLoggedInUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(currentUsername);
    }

    @Override
    public CompanyDto getLoggedInCompany() {
        CompanyDto company = getLoggedInUser().getCompany();
        return companyService.findById(company.getId());
    }

}
