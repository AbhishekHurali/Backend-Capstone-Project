package com.example.ProductCatalogue.Inheritance.JoinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name="jc_user")
public class User {

    @Id
    private Long id;

    private String email;
}
//Concept: Use foreign key relationship where subclasses share a common table with a foreign key referencing the superclass table.
//Implementation: @Inheritance(strategy = InheritanceType.JOINED).
//Features:
//Each subclass will hold a foreign key pointing to the user table.
//Requires defining a primary key join column to identify the foreign key.
//Pros: Reduces redundancy, as the user-specific data is kept separately.
//        Cons: Can lead to data inconsistency if operations are not synchronized