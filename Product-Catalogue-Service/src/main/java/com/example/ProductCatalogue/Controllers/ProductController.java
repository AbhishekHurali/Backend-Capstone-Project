package com.example.ProductCatalogue.Controllers;

import com.example.ProductCatalogue.DTOs.CategoryDto;
import com.example.ProductCatalogue.DTOs.ProductDto;
import com.example.ProductCatalogue.Models.Category;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> response = productService.getProducts();
        if(response==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        for(Product product : response){
            productDtos.add(from(product));
        }
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }


    //If we are using the same variable as we use in the GetMapping then we need to
    //use @PathVariable(id passed). Otherwise we need to map the path variable to any variable we use in the code
    // Using @PathVariable(id used) annotation.
    //@GetMapping("/products/{productId}")
    //public ProductDto getProductById(@PathVariable Long productId)
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        try{
            if(productId<0){
                throw new IllegalArgumentException("ProductId is invalid");
            }else if(productId==0){
                throw new IllegalArgumentException("ProductId Zero is not accessible");

            }
           // productId--;
            Product product = productService.getProductById(productId);
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();

            if (product == null) {
                headers.add("message","product does not exist");
                return new ResponseEntity<>(null,headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ProductDto productDto= from(product);
            headers.add("Called by", "Postman");
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);

            //If we use the HttpStatus.INTERNAL_SERVER_ERROR then also we get the
            //correct output but status code will be INTERNAL_SERVER_ERROR
            //return new ResponseEntity<>(productDto, HttpStatus.OK);
        }catch(IllegalArgumentException exception){
            //return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            // Now we have GlobalExceptionHandler then we can remove the above statement
            // We can even remove try and catch method
            // We are using the catch block here just for comfortability
            //We are reset and close resources etc in the catch block.
            throw exception;
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(from(productDto));
        return productDto;
    }

    @PutMapping("/products/{productId}")
    public ProductDto replaceProduct(@PathVariable Long productId,
                                     @RequestBody ProductDto productDto) {
        Product inputProduct = from(productDto);
        Product response = productService.replaceProduct(productId,inputProduct);

        return from(response);
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
    }


    @GetMapping("/products/{productId}/{id}")
    public ProductDto getProductDtoBasedOnUserScope(@PathVariable Long productId,@PathVariable Long id){
        Product product = productService.getProductBasedOnUserScope(productId,id);
        return from(product);
    }


    private Product from(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        if(productDto.getCategory()!=null){
            Category category= new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
 }
//All the exception should be handled by Controller -> resembles Waiter in restaurant example
// If the item is not available then Waiter will come and tell that item is not available, not the chef.