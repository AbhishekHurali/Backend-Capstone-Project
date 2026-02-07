package com.example.ProductCatalogue.Controllers;

import com.example.ProductCatalogue.DTOs.ProductDto;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    //Arrange
    @Autowired // because we are using the actual object to call the testing method
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;
    //Argument Capture and Matchers
    //Argument capture was introduced as a method to validate that function calls are passing the correct arguments.
    // This technique is useful in testing scenarios where functions might modify parameters unintendedly during execution.
    // By capturing arguments, developers can assert that the expected values are being used downstream, enhancing the reliability of tests.
    //
    //Practical Usage
    //Argument Capture: Developers should use annotations like @Captor to define argument captures and can then leverage these to write comprehensive unit tests ensuring the integrity of data passing through layers.
    //Argument Matchers: These are used to ensure that functions in control layers, such as a product controller, are invoking services like getProductByID correctly with the intended parameters

    @Test
    public void TestGetProductById_WithValidId_RunsSuccessfully(){

        //Arrange
        Long productId=2L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");


        when(productService.getProductById(productId))
                            .thenReturn(product);
        //You are telling Mockito:
        //“Whenever the controller calls productService.getProductById(2L),
        //don’t run the real method — instead return this fake product object.”
        //So:
        //No database call happens
        //No real service logic runs
        //You fully control the output
        //This is called mocking the dependency.

        //Act
        ResponseEntity<ProductDto> productDtoResponseEntity = productController.getProductById(productId);
//        But you are NOT testing the real service logic — you are mocking it.
//        So your test focuses only on the Controller behavior, not the Service.
        //Assert
        assertNotNull(productDtoResponseEntity);
        assertNotNull(productDtoResponseEntity.getBody());
        assertEquals(productId,productDtoResponseEntity.getBody().getId());
        assertEquals("Iphone",productDtoResponseEntity.getBody().getName());

        // Now in this case function of ProductService class is called only once
        // If suppose in the same method if we are calling the function of ProductService many times, and we want to confirm that
        //We are calling the function of ProductService -> the number of times we can test that
        //Any validation done on mock dependency we can always use Verify
        verify(productService,times(1)).getProductById(2L);
    }

    @Test
    public void TestGetProductById_WithZeroID_ThrowsIllegalArgumentException(){

        //no need to arrange anything in this test
        //here I don't need to mock the ProductService here
        //because we will not touch those lines here
        //Before we touch that line we get the exception

        //act and assert
        assertThrows(IllegalArgumentException.class,
                ()-> productController.getProductById(0L));

    }

    @Test
    public void TestGetProductById_WithZeroID_DilemmaIllegalArgumentException(){

        //no need to arrange anything in this test
        //here I don't need to mock the ProductService here
        //because we will not touch those lines here
        //Before we touch that line we get the exception

        //In product Controller class we made this change now
//        try{
//            if(productId<0){
//                throw new IllegalArgumentException("ProductId is invalid");
//            }else if(productId==0){
//                throw new IllegalArgumentException("ProductId Zero is not accessible");
//
//            }

        //act and assert
        Exception exception= assertThrows(IllegalArgumentException.class,
                ()-> productController.getProductById(0L));

        //Now I should be confident that out of two IllegalArgumentException
        //I am getting the right IllegalArgument exception with message

        assertEquals("ProductId Zero is not accessible",exception.getMessage());

    }

    @Test
    //Considering the exception is thrown from the downstream (ProductService)
    public void TestGetProductById_WhenProductServiceThrowsException_ThenThrowSameException(){

        //arrange
        //Here it is mocking the behavior of productService for any of the input Id given
        when(productService.getProductById(any(Long.class)))
                .thenThrow(new RuntimeException("Something went bad!!"));

        //act and assert
        assertThrows(RuntimeException.class,()->productController.getProductById(1L));
    }


    @Test
    public void TestGetProductById_WhetherCorrectProductIDPassed_RunsSuccessfully(){

        //Arrange
        Long productId=2L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");


        when(productService.getProductById(productId))
                .thenReturn(product);

        //act
        productController.getProductById(productId);

        //assert
        verify(productService).getProductById(idCaptor.capture()); // This makes sure that the id which we
        // have passed the same id is sent in the argument while calling the productService.getProductById()
        //If the id passed in the argument has been changed then it will throw an error
        // I have added a commented line productId-- in the getProductById() method of the productController class
        assertEquals(productId,idCaptor.getValue());

    }

}