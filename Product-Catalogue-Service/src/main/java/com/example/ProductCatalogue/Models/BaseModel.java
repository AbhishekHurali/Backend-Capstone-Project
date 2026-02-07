package com.example.ProductCatalogue.Models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass // I don't want to create table for this class, But need the values to be in the table.
public abstract class BaseModel {

    @Id
    private Long id;

    private Date createdAt;

    private Date lastUpdatedAt;

    private State state;
}
