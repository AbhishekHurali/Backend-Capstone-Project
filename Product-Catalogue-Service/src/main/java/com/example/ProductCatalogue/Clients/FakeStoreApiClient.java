package com.example.ProductCatalogue.Clients;

import com.example.ProductCatalogue.DTOs.FakeStoreProductDto;
import com.example.ProductCatalogue.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
//When we are calling a client side API, the best practice is to create a separate class and write the methods in this class
//We can call these methods from Service class now, following SRP.
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductById(Long productId){

        ResponseEntity<FakeStoreProductDto> response = requestForEntity("https://fakestoreapi.com/products/{productId}",HttpMethod.GET,null,FakeStoreProductDto.class,productId);
               //Just see the getForEntity function for method calling signature
        return validateResponse(response);
    }

    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                  Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDto validateResponse(ResponseEntity<FakeStoreProductDto> response){
        if(response.getBody()==null
                || response.getStatusCode().equals(HttpStatusCode.valueOf(500))){
            return null;
        }
        return response.getBody();
    }


}
//1. Handling Parameterized Types with REST APIs
//Problem with Parameterized Types
//When using collections such as a List in Java with RestTemplate.getForEntity, there is an issue with parameterized types.
// Collections only recognize the type of elements during insertion or operations, not beforehand.
//As Java cannot determine the element type of a List at runtime, using a direct List<DTO>.class with getForEntity results in an error.
//Solution: Using Arrays Instead
//The solution to the parameterized type issue is to use arrays such as FixedProductDTO[].class. This avoids the need for parameterization and allows the application to typecast correctly.


//Creating a General Method
//Instead of creating specific methods for each HTTP operation (GET, POST, PUT, etc.), you can generalize them by introducing a parameter for the HTTP method, thus reusing the same method for different operation types.
//Example of Method Reuse:
//A generic method can handle different types of requests by using an HttpMethod parameter, thus making your code more modular and easier to maintain.


//Purpose of a Client Layer
//A client layer is created to interact with third-party APIs. It abstracts the details of API calls, making the service layer code cleaner and more focused on business logic.
//Changes to third-party URLs or data formats are contained within the client layer, reducing the impact on the rest of the system.
//Structure of the Client Layer
//The client layer should include all interactions with third-party systems, separating API call logic from the business logic