package com.example.ProductCatalogue.Repositories;

import com.example.ProductCatalogue.Models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoriesTest {

    @Autowired
    private ProductRepositories productRepo;

    @Test
    //@Transactional if I use @Transactional then during testing part it will insert the
    //data, and again it will roll back
    //If I don't use the @Transactional then the dat inserted will not be rolled back and permanently stored in the DB.
    public void addTestProductsInAWSDb(){
        Product product = new Product();
        product.setId(1L);
        product.setName("IPhone");
        product.setPrice(150000D);
        productRepo.save(product);

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("MacBook");
        product1.setPrice(150000D);
        productRepo.save(product1);

    }

    @Test
    @Transactional
    public void testJPAQueries(){
//        List<Product> products = productRepo.findProductByPriceBetween(20000D,80000D);
//        System.out.println(products.size());
//        for(Product p:products){
//            System.out.println(p.getPrice());
//        }

//        List<Product> products = productRepo.findProductByOrderByPriceDesc();
//        for(Product p:products){
//            System.out.println(p.getPrice());
//        }

//        System.out.println(productRepo.getProductDescriptionById(3L));

        System.out.println(productRepo.getCategoryNameByProductId(6L));

    }

}