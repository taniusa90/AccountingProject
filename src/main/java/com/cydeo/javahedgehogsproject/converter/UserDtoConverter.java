package com.cydeo.javahedgehogsproject.converter;

import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<String, UserDto> {
    UserService userService;

    public UserDtoConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto convert(String source) {

        if (source == null || source.equals("")) { 
            return null;
        }

        return userService.findById(Long.parseLong(source));

    }

}