package com.example.ProductCatalogue.DTOs;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDto implements Serializable {//We use implements Serializable because
    //Redis Stores the objects in the byte format.

    //Redis is an in-memory database, that's why we need to serializable/deserialize our objects during
    //the storing /fetching of them in redis

    //Serialization means transforming an object into a stream of bytes to store the object in memory.
    //The main goal is to save the state of an object from being able to recreate it when required.
    //The reverse process of serialization is called deserialization.

    private Long id;

    private String title;

    private Double price;

    private String description;

    private String image;

    private String category;
}
