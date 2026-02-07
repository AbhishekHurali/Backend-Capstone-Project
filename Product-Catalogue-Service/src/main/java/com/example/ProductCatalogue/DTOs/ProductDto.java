package com.example.ProductCatalogue.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Long id;
    //We are not adding createdAt and updatedAt as we used to add in BaseModel
    // because Models are used at DB level. But in Dtos are just used for abstraction
    // that's why we don't need createdAt and updatedAt fields.

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private CategoryDto category;
}
