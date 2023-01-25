package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String retrieveAllCategories(Model model) {
        model.addAttribute("categories", categoryService.listAllCategoriesByCompany());
        return "category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String insertCategory(@Valid @ModelAttribute("newCategory") CategoryDto category, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || categoryService.isCategoryExist(category.getDescription())) {
            if (categoryService.isCategoryExist(category.getDescription())) {
                bindingResult.rejectValue("description", " ", "A category with this name already exists. Please try with different name.");
            }
            return "/category/category-create";
        }

        categoryService.save(category);

        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model) {
        CategoryDto retrievedCategory = categoryService.findCategoryById(id); //getting needed category by id
        retrievedCategory.setHasProduct(categoryService.hasProduct(id)); //setting hasProduct to true or false

        model.addAttribute("category", categoryService.findById(id));

        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String editCategory(@Valid @ModelAttribute("category") CategoryDto categoryDto, BindingResult bindingResult, @PathVariable("id") Long id) {
        categoryDto.setId(id); //to save the same id

        if (bindingResult.hasErrors() || categoryService.isCategoryExist(categoryDto.getDescription())) {
            if (categoryService.isCategoryExist(categoryDto.getDescription())) {
                bindingResult.rejectValue("description", " ", "A category with this name already exists. Please try with different name.");
            }
            return "/category/category-update";
        }
        categoryService.update(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/categories/list";
    }

}


