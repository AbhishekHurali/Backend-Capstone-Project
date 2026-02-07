package com.example.ProductCatalogue.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestModel { // A sample Java class used for experimenting with Database schema changes.

    @Id
    private int numField;

    private String nameField;
}
//Actually this class I have created to practice the DB versioning
//But I didn't do it because JPA buddy advanced operations was available only with IntelliJ Ultimate
// So just follow the video Day 188, Dec 9 lecture -> 40 mins to 1 hr 10 mins