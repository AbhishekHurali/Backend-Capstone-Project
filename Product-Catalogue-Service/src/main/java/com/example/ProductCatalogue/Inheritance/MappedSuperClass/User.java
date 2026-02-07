package com.example.ProductCatalogue.Inheritance.MappedSuperClass;

import jakarta.persistence.*;

@MappedSuperclass // In this case table will not be created for User in the DB.
public class User {

    @Id
    private Long id;

    private String email;
}
//Concept: The base class is not an entity and doesn't have a corresponding table; its properties are inherited by subclasses.
//Implementation: Use annotation @MappedSuperclass in the base class.
//Pros: Avoids the creation of additional tables when they are not necessary.
//        Cons: The base class cannot be queried by itself