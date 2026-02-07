package com.example.ProductCatalogue.Inheritance.MappedSuperClass;

import jakarta.persistence.Entity;

@Entity(name="msc_instructor")
public class Instructor extends User {

    private String company;
}
