package com.example.ProductCatalogue.Inheritance.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name="st_instructor")
@DiscriminatorValue(value="1")
public class Instructor extends User {

    private String company;
}
