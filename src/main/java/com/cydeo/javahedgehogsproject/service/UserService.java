package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);

    UserDto findById(long id);

    List<UserDto> findAll();

    void save(UserDto user);
    void update(UserDto user);

    void deleteById(Long id);

    boolean isEmailExist(String username);
}
