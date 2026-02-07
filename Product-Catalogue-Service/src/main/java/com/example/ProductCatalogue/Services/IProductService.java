package com.example.ProductCatalogue.Services;

import com.example.ProductCatalogue.Models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getProducts();

    Product getProductById(Long productId);

    Product createProduct(Product product);

    Product replaceProduct(Long productId, Product product);

    Product getProductBasedOnUserScope(Long productId,Long userId);
}
