package com.cydeo.javahedgehogsproject.service;


import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto findById(long id);

    List<CategoryDto> retrieveCategoryByCompany();

    List<CategoryDto> listAllCategoriesByCompany();

    void save(CategoryDto dto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(Long id);
    boolean hasProduct(Long id);

    CategoryDto findCategoryById(Long id);
    boolean isCategoryExist(String category);







}
