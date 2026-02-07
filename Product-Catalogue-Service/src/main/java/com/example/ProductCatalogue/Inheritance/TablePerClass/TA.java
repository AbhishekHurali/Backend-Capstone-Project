package com.example.ProductCatalogue.Inheritance.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name="tpc_ta")
public class TA extends User{

    private double hours;
}
