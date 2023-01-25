package com.cydeo.javahedgehogsproject.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    @Size(max = 15, min = 2)
    private String firstname;
    @NotBlank
    @Size(max = 15, min = 2)
    private String lastname;
    @NotBlank
    @Email
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;
    @NotNull
    private String confirmPassword;
    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
    @NotNull
    private RoleDto role;
    @NotNull
    private CompanyDto company;
    private Boolean isOnlyAdmin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public Boolean getIsOnlyAdmin() {
        return isOnlyAdmin;
    }

    public void setIsOnlyAdmin(Boolean onlyAdmin) {
        isOnlyAdmin = onlyAdmin;
    }
}