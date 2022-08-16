package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    @Size(min = 3, max = 15, message = "First name should have 3-15 characters")
    private String firstName;
    @Size(min = 3, max = 15, message = "Last name should have 3-15 characters")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private  String password;
    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private  String repeatPassword;

}
