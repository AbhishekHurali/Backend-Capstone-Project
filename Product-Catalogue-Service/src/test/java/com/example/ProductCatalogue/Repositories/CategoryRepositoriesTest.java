package com.example.ProductCatalogue.Repositories;

import com.example.ProductCatalogue.Models.Category;
import com.example.ProductCatalogue.Models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoriesTest {

    @Autowired
    private CategoryRepositories categoryRepo;


    @Test
    @Transactional
    public void testFetchTypes(){

        //FetchTypes

        //Scenario 1 -> FetchType is Lazy, and we are asking for Products explicitly
        // Here it will generate 2 select statements one for category and one for Products
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
//        for(Product p:category.getProducts()){
//            System.out.println(p.getPrice());
//        }

        //Scenario 2 -> FetchType is Lazy, and we are not asking for Products explicitly
        // Here it will generate 1 select statements one for category that's it.
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());

        //Scenario 3 -> FetchType is Eager, and we are asking for Products explicitly
        // Here it will generate 1 left Join query will be run
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
//        for(Product p:category.getProducts()){
//            System.out.println(p.getPrice());
//       }

        //Scenario 4 -> FetchType is Eager, and we are not asking for Products explicitly
        // Then also Here it will generate 1 left join query will be run
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());

        //----------------------------------------------------------------
        //FetchModes

        //This operation does not need subselect query to get the result
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
//        for(Product p:category.getProducts()){
//            System.out.println(p.getPrice());
//       }

        //Changing the code to make sure that operation uses Subselect query.
        List<Category> categoryList = categoryRepo.findAll();
        for(Category c:categoryList){
            List<Product> products = c.getProducts();
            for(Product p:products){
                System.out.println(p.getPrice());
            }
        }

    }

}
//FetchType  FetchMode           Action                            Result
//Lazy        Select           Asked Product explicitly          2 select query - one on category and one on product
//                             Not Asked Product explicitly      1 select query on category is executed

//Eager       Select           Asked Product explicitly          2 select query - one on category and one on product
//                             Not Asked Product explicitly      2 select query - one on category and one on product


//Lazy        Join           Asked Product explicitly            1 Join query
//                           Not Asked Product explicitly        1 Join query

//Eager       Join           Asked Product explicitly            1 Join query
//                           Not Asked Product explicitly        1 Join query


//Lazy        SubSelect        Asked Product explicitly
//Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
// Since this operation does not need subselect query to get the result
// It will be just two select query


//Lazy        SubSelect       Not Asked Product explicitly
//Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
// Since this operation does not need subselect query to get the result
// It will be just one select query


//Eager       SubSelect        Asked Product explicitly
//Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
// Since this operation does not need subselect query to get the result
// It will be just two select query


//Eager      SubSelect        Not Asked Product explicitly
//Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
// Since this operation does not need subselect query to get the result
// It will be just two select query

//FetchMode Join will be dominant over any case (be it any fetch type - whether you asked for child entities or not)

//FetchMode Select and subselect will actually honor fetch type LAZY/EAGER

//Revision Notes on Fetch Types and Fetch Modes in JPA
//## Introduction
//In this class, we explored the concepts of fetch types and fetch modes in Java Persistence API (JPA), focusing on how they affect data retrieval between a database and an application. Understanding these concepts is crucial for optimizing database interactions in a Java application.

//## Fetch Types
//Fetch types determine when associated data will be retrieved, with two main types:
//
//        - **Lazy Loading**: Data is fetched when it is specifically requested. It results in a select query being executed only when the related data is accessed.
//
//- **Eager Loading**: Associated data is retrieved immediately, alongside the entity itself, often resulting in a join query being executed at the time of the initial retrieval.
//
//The choice between lazy and eager loading affects query performance and application efficiency. For instance, if the fetch type was lazy, select queries were observed, whereas an eager fetch type led to join queries【4:1†transcript.txt】.
//
//        ## Fetch Modes
//
//Fetch modes define how data is fetched and can be specified explicitly, allowing developers to optimize query performance better than default JPA behavior. There are three main fetch modes:
//
//        1. **Join**: Uses SQL joins to retrieve associated data in a single query. This mode is generally more performance-intensive but reduces the number of queries executed.
//
//        2. **Select**: Executes separate select queries to fetch associated data. This mode can lead to an `N+1` queries problem, where one query retrieves the parent entity, and `N` additional queries retrieve each associated entity【4:2†transcript.txt】.
//
//        3. **Subselect**: Fetches all related entities using a subquery, improving efficiency over multiple selects but can be complex to optimize correctly【4:4†transcript.txt】.
//
//        ## Dominance of Fetch Modes
//
//Fetch mode 'Join' dominates over fetch types. Regardless of whether lazy or eager fetch type is specified, if fetch mode is 'Join', a join query is executed for fetching the data【4:3†transcript.txt】.
//
//        ## N+1 Problem
//
//This problem occurs when an application executes one query to fetch a category and N subsequent queries to fetch its related products, resulting in inefficient database interaction. Instead, using `Join` or `Subselect` fetch modes can alleviate this issue by reducing the number of queries【4:7†transcript.txt】【4:13†transcript.txt】.
//
//        ## Practical Example
//
//For a practical understanding, consider the following scenarios:
//
//        - **Lazy Load with Select Mode**: Fetching products when requested (results in multiple select queries).
//
//        - **Eager Load with Select Mode**: Products are fetched regardless of explicit request (always two select queries)【4:16†transcript.txt】.
//
//        - **Join Mode**: Overpowers the fetch type and executes a join query【4:3†transcript.txt】.
//
//        ## Conclusion
//
//Understanding fetch types and fetch modes in JPA is essential for efficient data retrieval in Java applications. While lazy loading reduces initial data fetch size, eager loading ensures all necessary data is preloaded. Effective use of fetch modes can significantly optimize performance, especially in dealing with large datasets or complex data models.
//The class emphasized the importance of testing different combinations to see how JPA aligns with specific query optimizations and to avoid potential pitfalls like the N+1 problem【4:6†transcript.txt】【4:5†transcript.txt】.