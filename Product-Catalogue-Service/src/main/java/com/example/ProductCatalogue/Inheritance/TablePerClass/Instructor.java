package com.example.ProductCatalogue.Inheritance.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name="tpc_instructor")
public class Instructor extends User{

    private String company;
}
