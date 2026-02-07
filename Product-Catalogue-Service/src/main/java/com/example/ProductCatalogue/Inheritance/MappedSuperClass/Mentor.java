package com.example.ProductCatalogue.Inheritance.MappedSuperClass;

import jakarta.persistence.Entity;

@Entity(name="msc_mentor")
public class Mentor extends User {

    private double ratings;
}
