package com.example.ProductCatalogue.Inheritance.JoinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name="jc_instructor")
@PrimaryKeyJoinColumn(name = "user_id") // If we don't use this annotation
// then it will take the same field name as present in the User class.
// It is just the renaming the foreign key column.
public class Instructor extends User {

    private String company;
}
