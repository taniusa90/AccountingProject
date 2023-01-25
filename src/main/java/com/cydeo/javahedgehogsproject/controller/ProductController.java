package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listAllProducts(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return "/product/product-list";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.retrieveCategoryByCompany());
        model.addAttribute("productUnits", ProductUnit.values());
        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@Valid @ModelAttribute("newProduct") ProductDto newProduct, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || productService.isNameExist(newProduct.getName())) {
            if (productService.isNameExist(newProduct.getName())) {
                bindingResult.rejectValue("name", " ", "This Product Name already exists");
            }
            model.addAttribute("categories", categoryService.retrieveCategoryByCompany());
            model.addAttribute("productUnits", ProductUnit.values());

            return "/product/product-create";
        }
        productService.save(newProduct);
        return "redirect:/products/list";
    }

    @GetMapping("update/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.retrieveCategoryByCompany());
        model.addAttribute("productUnits", ProductUnit.values());
        return "/product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            product.getCategory().setHasProduct(true);
            model.addAttribute("categories", categoryService.retrieveCategoryByCompany());
            model.addAttribute("productUnits", ProductUnit.values());
            return "/product/product-update";
        }
        productService.update(product);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/products/list";
    }

}

