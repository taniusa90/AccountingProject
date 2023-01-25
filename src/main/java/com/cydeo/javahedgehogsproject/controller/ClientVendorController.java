package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.enums.ClientVendorType;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String getClientVendorsList(Model model) {
        model.addAttribute("clientVendors", clientVendorService.findAll());
        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String insertClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {

        if (clientVendorService.checkIfThereIsAnyClientVendorWithSameNameAndType(clientVendorDto.getClientVendorName(), clientVendorDto.getClientVendorType())) {
            bindingResult.rejectValue("clientVendorName", " ", "Company with this name and this type already exists. Please try with a different name or type!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "/clientVendor/clientVendor-create";
        }

        clientVendorService.create(clientVendorDto);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendorTypes", clientVendorService.findById(id).getClientVendorType());
        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String editClientVendor(@Valid @ModelAttribute("clientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {

        if (clientVendorService.checkIfThereIsAnyClientVendorWithSameNameAndType(clientVendorDto.getClientVendorName(), clientVendorDto.getClientVendorType())) {
            bindingResult.rejectValue("clientVendorName", " ", "Company with this name and this type already exists. Please try with a different name or type!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", clientVendorDto.getClientVendorType());
            return "/clientVendor/clientVendor-update";
        }

        clientVendorService.update(clientVendorDto);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        clientVendorService.deleteById(id);
        return "redirect:/clientVendors/list";
    }

}
