package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cydeo.javahedgehogsproject.dto.UserDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "/user/user-list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("companies", companyService.findAllByUsers());

        return "user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@Valid @ModelAttribute("newUser") UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors() || userService.isEmailExist(user.getUsername())) {
            if (userService.isEmailExist(user.getUsername())) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }
            model.addAttribute("userRoles", roleService.findAll());
            model.addAttribute("companies", companyService.findAllByUsers());

            return "user/user-create";
        }
        userService.save(user);

        return "redirect:/users/list";

    }

    @GetMapping("/update/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("companies", companyService.findAllByUsers());

        return "/user/user-update";

    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoles", roleService.findAll());
            model.addAttribute("companies", companyService.findAllByUsers());

            return "user/user-update";
        }
        userService.update(user);

        return "redirect:/users/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users/list";
    }
}
