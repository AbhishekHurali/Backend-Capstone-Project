package com.example.ProductCatalogue.Inheritance.TablePerClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity(name="tpc_user")
public class User {

    @Id
    private Long id;

    private String email;
}
//Concept: Create a separate table for each class.
//Implementation: Use JPA annotations to specify @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS).
//Attributes: Each table will have its own specific attributes. For example:
//User table contains common user attributes like name and email.
//TA, Instructor, Mentor tables will have additional specific attributes such as hours, company, and ratings.
//Attributes in Java:
//User: private long id, private String email.
//TA: private long hours.
//Instructor: private String company.
//Mentor: private double ratings.
//Pros: Simple as each class has its own table.
//Cons: Requires unions or joins to gather all user data