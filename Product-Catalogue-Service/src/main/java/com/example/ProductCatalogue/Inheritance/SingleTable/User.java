package com.example.ProductCatalogue.Inheritance.SingleTable;

import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name="st_user")
@DiscriminatorColumn(name="user_type",discriminatorType = DiscriminatorType.INTEGER)
// We use above @DiscriminatorColumn to discriminate the user(Instructor, Mentor, TA). We have specified each user by unique number(Integer)
// using @DiscriminatorValue
public class User {

    @Id
    private Long id;

    private String email;
}
//Concept: All classes (base and derived) share a single table.
//Implementation: @Inheritance(strategy = InheritanceType.SINGLE_TABLE).
//Features:
//Use a discriminator column to distinguish between entity types.
//Efficient in terms of storage space as no joins are required.
//        Pros: No table joins needed for retrieving user data, improves read performance.
//        Cons: Can create a sparse table with many nullable columns