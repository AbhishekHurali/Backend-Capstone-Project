package com.example.UserAuthenticationService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String email;

    private String password;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();//If the new user is created
    // we wouldn't assign that person any role immediately
    //Hence, whenever a new user is created the roles will be just empty list


}

//User   Role
//   1       m
//   m       1
//   m   :   m