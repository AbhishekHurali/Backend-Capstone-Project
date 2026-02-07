package com.example.ProductCatalogue.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends BaseModel{

    private String name;

    private String description;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)//Scenario 1
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)//Scenario 2
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)//Scenario 3
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)//Scenario 4
//    private List<Product> products;

   // FetchTypes completed
//----------------------------------------------------------------------------
    //FetchMode

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SELECT)
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
//    private List<Product> products;
//
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SELECT)//When the operation tries to use subselect query, and we use SELECT then it will lead to N+1 Problem
//    // N is the number of records in category table(For each record Product table will be searched) and 1 query to get the records from category table
//    private List<Product> products;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.SUBSELECT)// It will have 2 queries
//    // One query will get the total number of records from the Category table and another record uses the subquery to get the records from Products for each Category record
//    private List<Product> products;

    //If the interviewer asks to use FetchMode.SELECT for the operation which uses subquery then we can use BatchSize
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 3) // It will run N/Batchsize+1 where N is the total number of records in Category
    @JsonIgnore // Product → Category (NO products inside) Prevents nested long product <-> Category chaining in the output
    private List<Product> products;


}
//FetchType -> Says when the data is fetched (Lazy, Eager)
//FetchMode -> Says how the data is fetched (Select, Join, Subselect)