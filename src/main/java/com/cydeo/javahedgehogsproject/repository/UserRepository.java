package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.Role;
import com.cydeo.javahedgehogsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByRoleDescriptionAndCompanyOrderByCompanyTitleAscRoleDescription(String role, Company company);

    List<User> findAllByCompanyOrderByRoleDescription(Company company);

    List<User> findAllByRoleDescriptionOrderByCompanyTitle(String role);


}
