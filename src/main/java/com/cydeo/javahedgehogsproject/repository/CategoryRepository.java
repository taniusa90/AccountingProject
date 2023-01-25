package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.entity.Category;
import com.cydeo.javahedgehogsproject.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findAllByCompanyId(Long id);
    List<Category> getCategoriesByCompany(Company company);
    Optional<Category> getCategoryById (Long id);


}
