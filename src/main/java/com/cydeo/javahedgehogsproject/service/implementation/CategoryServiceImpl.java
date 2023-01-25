package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.entity.Category;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.CategoryRepository;
import com.cydeo.javahedgehogsproject.service.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final SecurityService securityService;
    private final CompanyService companyService;
    private final ProductService productService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserService userService, SecurityService securityService, CompanyService companyService, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.securityService = securityService;
        this.companyService = companyService;
        this.productService = productService;
    }


    @Override
    public CategoryDto findById(long id) {
        Category category = categoryRepository.findById(id).get();
        CategoryDto categoryDto=mapperUtil.convert(category, new CategoryDto());
        categoryDto.setHasProduct(hasProduct(id));
        return categoryDto;
    }

    @Override
    public List<CategoryDto> retrieveCategoryByCompany() {

        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company1 = mapperUtil.convert(companyDto, new Company());
        List<Category> categoryList = categoryRepository.getCategoriesByCompany(company1);
        return categoryList.stream().map(category -> mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());

    }

    @Override
    public List<CategoryDto> listAllCategoriesByCompany() {

        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Category> listOfCategories = categoryRepository.findAllByCompanyId(company.getId());

        List<CategoryDto> categoryDtoList = listOfCategories.stream().map(category -> mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());

        for(CategoryDto each : categoryDtoList) {
            each.setHasProduct(hasProduct(each.getId()));
        }
        return categoryDtoList.stream().sorted(Comparator.comparing(CategoryDto::getDescription)).collect(Collectors.toList());
    }

    @Override
    public void save(CategoryDto dto) {

        CompanyDto companyDto = securityService.getLoggedInCompany();
        dto.setCompany(companyDto);

        dto.setHasProduct(false);

        Category category = mapperUtil.convert(dto, new Category());
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {

        //getting category from DB with the same id
        Category category=categoryRepository.findById(categoryDto.getId()).get();
        //setting its description to a new description
        category.setDescription(categoryDto.getDescription());
        //saving any changes back to DB
        categoryRepository.save(category);

        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findById(id).get();
        category.setIsDeleted(true);
        categoryRepository.save(category);

    }

    @Override
    public CategoryDto findCategoryById(Long id) {

        Category category=categoryRepository.getCategoryById(id).get();

        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public boolean isCategoryExist(String category) {
            boolean anyMatch = categoryRepository.findAll().stream().anyMatch( category1 -> category1.getDescription().equals(category));

            return anyMatch;

    }

    @Override
    public boolean hasProduct(Long id) {
        //checking if category has more than 0 products
        return productService.findAllProductsByCategoryId(id).size()>0;
    }


}
