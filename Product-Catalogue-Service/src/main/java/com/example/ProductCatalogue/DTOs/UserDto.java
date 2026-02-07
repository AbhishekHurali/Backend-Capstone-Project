package com.example.ProductCatalogue.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private Long id;

    private String email;

    //private List<Role> roles;

    //Password is not exposed
}
