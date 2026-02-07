package com.example.ProductCatalogue.Controllers;

import com.example.ProductCatalogue.DTOs.ProductDto;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)//We don't want to add the dependency
//We want to shadow the path when API call is made through dispatcher servlet
// We don't want to go through all those paths just for testing purpose
// So we used @WebMvcTest annotation
public class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_RunsSuccessfully() throws Exception {

        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    public void TestGetAllProducts_ReceiveProductList() throws Exception {

        Product product1=new Product();
        product1.setId(1L);
        product1.setName("IPhone");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Macbook");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        when(productService.getProducts())
                .thenReturn(products);

        //the underlining json structure is same for both product and productDto is same
        //we can pass anything here
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(products)))
                .andExpect(jsonPath("$.length()").value(2)) // Here it is the number of list of json
                .andExpect(jsonPath("$[0].length()").value(2))// Here it is number of attributes inside first json
                .andExpect(jsonPath("$[1].name").value("Macbook"));
    }

    @Test
    public void TestCreateProductApi_RunsSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("IPhone");

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("IPhone");

        //because we are sending product object in the productService.createProduct(product)
        //and it returns the product
        when(productService.createProduct(product))
                .thenReturn(product);

        //Act and Assert
        //But in the controller we are sending the ProductDto object and returning the
        //ProductDto object from the controller class back
        //While using postman it will add contentType automatically on our behalf
        // So when we use mockMvc we should add the contentType here
        // While calling the post Api we should also send the content
        //so we sued content and productDto while calling the Api here
        mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.length()").value(2));//length here means number of attributes inside the one json
        //We use jsonPath when we want to match the exact results rather than the complete json structure

    }
}


