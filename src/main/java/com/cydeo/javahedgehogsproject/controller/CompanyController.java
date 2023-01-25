package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getCompanyList(Model model) {
        model.addAttribute("companies", companyService.findAll());
        return "/company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {
        model.addAttribute("newCompany", new CompanyDto());
        return "/company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@Valid @ModelAttribute("newCompany") CompanyDto company, BindingResult bindingResult) {

        if (companyService.isCompanyNameUnique(company.getTitle())) {
            bindingResult.rejectValue("title", " ", "Company name already exists. Please try with different name.");
            return "/company/company-create";
        }

        if (bindingResult.hasErrors()) {
            return "/company/company-create";
        }

        companyService.create(company);

        return "redirect:/companies/list";
    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyService.findById(id));
        return "/company/company-update";
    }

    @PostMapping("/update/{id}")
    public String editCompany(@Valid @ModelAttribute("company") CompanyDto company, BindingResult bindingResult) {

        if (companyService.isCompanyNameUnique(company.getTitle())) {
            bindingResult.rejectValue("title", " ", "Company name already exists. Please try with different name.");
            return "/company/company-update";
        }

        if (bindingResult.hasErrors()) {
            return "/company/company-update";
        }

        companyService.update(company);

        return "redirect:/companies/list";
    }

    @GetMapping("/activate/{id}")
    public String activateCompany(@PathVariable("id") Long id) {
        companyService.activate(id);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompany(@PathVariable("id") Long id) {
        companyService.deactivate(id);
        return "redirect:/companies/list";
    }

}
