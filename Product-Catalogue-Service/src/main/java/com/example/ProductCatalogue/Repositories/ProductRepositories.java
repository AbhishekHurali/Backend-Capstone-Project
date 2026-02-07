package com.example.ProductCatalogue.Repositories;

import com.example.ProductCatalogue.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositories extends JpaRepository<Product,Long> {

    @Override
    Optional<Product> findById(Long productId);

    Product save(Product product);

    List<Product> findProductByPriceBetween(Double low, Double high);//it's inclusive

    List<Product> findProductByOrderByPriceDesc();

    List<Product> findProductByOrderByIdDesc();// Here we get the whole row

    // If we want to extract particular cell value then we can write our own custom queries.
    //First find the required use case is present in the inbuilt method. If not then go with @Query custom queries.
    @Query("Select p.description from Product p where p.id=?1") //Here ?1 means first parameter will bind
    String getProductDescriptionById(Long id);

    @Query("Select c.name from Category c join Product p on p.category.id=c.id where p.id=?1")// We have to take the Java class name and field name declared
    String getCategoryNameByProductId(Long id);

    Page<Product> findByName(String query, Pageable pageable);
    //JPA repository extends Pageable interface
    //Pageable is the interface. And PageRequest is one of the class which implements Pageable.
    //PageRequest class uses pageSize and pageNumber. Which we acn use in the code.
//    Paging and Sorting with JPA
//    The Java Persistence API (JPA) provides repositories like PagingAndSortingRepository which facilitate pagination and sorting.
//    Paging: Implemented using the Pageable interface. This involves setting page numbers and page sizes to control the data size per request.
//    Sorting: Handled by passing sort parameters which determine the order of data retrieval, such as by price or another attribute
}
