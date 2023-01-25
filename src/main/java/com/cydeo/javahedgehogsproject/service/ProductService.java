package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto findById(long id);

    List<ProductDto> listAllProducts();

    void delete(Long id);

    void save(ProductDto productDto);

    void update(ProductDto product);

    List<ProductDto> findAllProductsByCategoryId(Long id);

    List<ProductDto> listAllProductsByCategory(Long categoryId);

    boolean isNameExist(String productName);

    List<ProductDto> findAll();

    public ProductDto create(ProductDto productDto);

}
