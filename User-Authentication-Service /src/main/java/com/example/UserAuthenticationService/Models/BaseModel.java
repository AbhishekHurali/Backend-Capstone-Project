package com.example.UserAuthenticationService.Models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // the id is created only when we try to insert the record in the table
    //If we are just creating the user object then Id will not be generated.

    private Status status;

    private Date createdAt;

    private Date lastUpdatedAt;
}
