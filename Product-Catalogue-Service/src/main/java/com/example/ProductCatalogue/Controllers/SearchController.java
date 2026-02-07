package com.example.ProductCatalogue.Controllers;

import com.example.ProductCatalogue.DTOs.CategoryDto;
import com.example.ProductCatalogue.DTOs.ProductDto;
import com.example.ProductCatalogue.DTOs.SearchDto;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    //The SearchController handles API requests. It decides on post requests to process search queries, invoking the appropriate service methods to interact with the database and return a list of products according to the search criteria

    @Autowired
    private ISearchService searchService;

    @PostMapping
    //Rather than List we can page which helps to give all the information
    // This is useful for frontend. Frontend will get to know all the details.
    //The use of post requests over get requests was justified, particularly due to the ability to send larger payloads in the body, facilitating complex queries and filtering that can't fit in URL parameters.
    //Post requests are deemed more secure and versatile for transmitting search parameters and criteria
    public Page<Product> searchProduct(@RequestBody SearchDto searchDto){

        //Page<ProductDto> results = new ArrayList<>();
        Page<Product> products = searchService.search(searchDto.getQuery(),searchDto.getPageSize(),searchDto.getPageNumber(),searchDto.getSortParams());

//        for(Product product:products){
//            results.add(from(product));
//        } //Write a code to convert it into Page<ProductDto>
        //take help from ChatGPT
        return products;
    }

    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        if(product.getCategory()!=null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }// Since this method is used in both ProductController and SearchController
    // We need to add it in separate class and call this method
}
