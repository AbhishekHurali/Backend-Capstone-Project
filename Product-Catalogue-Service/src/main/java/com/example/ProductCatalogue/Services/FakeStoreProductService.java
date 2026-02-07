package com.example.ProductCatalogue.Services;

import com.example.ProductCatalogue.Clients.FakeStoreApiClient;
import com.example.ProductCatalogue.DTOs.FakeStoreProductDto;
import com.example.ProductCatalogue.Models.Category;
import com.example.ProductCatalogue.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient apiClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        // If we see the getForEntity method signature then the second parameter it expects is .class
        // but if we use List<FakeStoreProductDto> the List is a generic class so we can't use it
        // So better we use Array which is not generic.
        // If we use List<Integer> and List<String> then both are generic classes
        //It will be converted to relevant classes during retrieval.
        if(response.getBody()==null
        || response.getStatusCode().equals(HttpStatusCode.valueOf(500))){
            return null;
        }

        for(FakeStoreProductDto fakeStoreProductDto : response.getBody()){
            products.add(from(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long productId){

//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}",FakeStoreProductDto.class,productId);
//        if(response.getBody()==null
//                || response.getStatusCode().equals(HttpStatusCode.valueOf(500))){
//            return null;
//        }        //Just see the getForEntity function for method calling signature
//        return from(response.getBody());
        //Above code now added in FakeStoreApiClient
        //return from(apiClient.getProductById(productId));

        //Implementing Using Redis
        //check in cache
        //return
        //else make call to fakestore
        //cache the result
        //return

        FakeStoreProductDto fakeStoreProductDto =null;

        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get("_products_",productId);
        //.opsForHash().get() and .opsForHash().put() these take two keys
        //One of the key(Based on what we choose) will act as differentiator
        // if we are storing product id 1 and category 1 then we need differentiator to differentiate between those two
        //Another example is When we want to store the luxury products or premier products and want to differentiate between other products
        if(fakeStoreProductDto==null){
            fakeStoreProductDto = apiClient.getProductById(productId);
            redisTemplate.opsForHash().put("_products_",productId,fakeStoreProductDto);
            System.out.println("Found by calling FakeStore!");
        }else{
            System.out.println("Found by calling Redis!");
        }
        return from(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product){
    return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        FakeStoreProductDto input = from(product);
        ResponseEntity<FakeStoreProductDto> response = requestForEntity("https://fakestoreapi.com/products/{productId}",HttpMethod.PUT,input,FakeStoreProductDto.class,productId);
        if(response.getBody()==null
                || response.getStatusCode().equals(HttpStatusCode.valueOf(500))){
            return null;
        }
        return from(response.getBody());
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long userId) {
        return null;
    }


    // We don't have putForEntity in the RestTemplate class
    //So we are modifying the postForEntity and converting that method into requestForEntity
    // We are adding one more parameter to that method HttpMethod and make this as a generalized method
    // We can use the same method for all PUT, POST, GET etc, just we need to send the respective HttpMethod while calling this method.
    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                               Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    private Product from(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category =new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
    //We can add the converters in either Product class and FakeStoreProduct class
    // We have ambiguity here. So better to ass in Service class.
    //Converts can be in Service class.

    private FakeStoreProductDto from(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory()!=null){
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}
