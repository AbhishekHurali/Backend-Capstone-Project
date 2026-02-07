package com.example.ProductCatalogue.Services;

import com.example.ProductCatalogue.DTOs.UserDto;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepositories productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long productId) {
        System.out.println("Reading from DB!");
        Optional<Product> productOptional = productRepo.findById(productId);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        System.out.println("Storing into DB!");
        productRepo.save(product);
        return product;
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long id) {

        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()){
            return null;
        }

        //RestTemplate restTemplate = new RestTemplate(); Configured via @Bean annotation
        String str = restTemplate
                .getForEntity("http://paymentservice/payment/print/{id}", String.class,id).getBody();

        //Earlier it was http://localhost:8081/payment/print/{id}
        //http://paymentservice/payment/print/{id} by writing this now it goes through the Service Discovery (Eureka Server)
        System.out.println(str);
        return optionalProduct.get();
    }
    //We have created the StorageProductService class because here we read data from DB
    // ProductService will help to read data from third party Apis
    // So we don't want to mix both the services.
}
