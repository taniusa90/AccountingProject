package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.User;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.UserRepository;
import com.cydeo.javahedgehogsproject.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id).get();
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> findAll() {

        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return userRepository.findAllByRoleDescriptionOrderByCompanyTitle("Admin").stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .peek(userDto -> userDto.setIsOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        } else {

            Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

            return userRepository.findAllByCompanyOrderByRoleDescription(company).stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .peek(userDto -> userDto.setIsOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        }

    }

    private Boolean isOnlyAdmin(UserDto userDto) {
        Company company = mapperUtil.convert(userDto.getCompany(), new Company());
        List<User> admins = userRepository.findAllByRoleDescriptionAndCompanyOrderByCompanyTitleAscRoleDescription("Admin", company);
        return userDto.getRole().getDescription().equals("Admin") && admins.size() == 1;
    }

    @Override
    public void save(UserDto user) {

        User obj = mapperUtil.convert(user, new User());
        obj.setEnabled(true);
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        userRepository.save(obj);

    }

    @Override
    public void update(UserDto user) {

        User dbUser = userRepository.findById(user.getId()).get();
        User convertedUser = mapperUtil.convert(user, new User());
        convertedUser.setId(dbUser.getId());
        convertedUser.setPassword(dbUser.getPassword());
        convertedUser.setEnabled(dbUser.isEnabled());
        userRepository.save(convertedUser);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).get();

        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId()
                + user.getCompany().getId()
                + user.getRole().getId());
        userRepository.save(user);
    }

    @Override
    public boolean isEmailExist(String username) {
        boolean anyMatch = userRepository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));

        return anyMatch;
    }
}
