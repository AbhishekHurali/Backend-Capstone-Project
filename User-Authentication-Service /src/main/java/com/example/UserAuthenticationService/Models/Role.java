package com.example.UserAuthenticationService.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModel{//This can be done with Enum also. But Enum is used when we have fixed number of roles.

    private String value;

}
