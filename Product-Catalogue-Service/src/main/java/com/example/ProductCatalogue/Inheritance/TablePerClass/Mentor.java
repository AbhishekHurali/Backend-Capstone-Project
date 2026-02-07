package com.example.ProductCatalogue.Inheritance.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name="tpc_mentor")
public class Mentor extends User{

    private double ratings;
}
