package com.example.ProductCatalogue.Services;

import com.example.ProductCatalogue.DTOs.SortParam;
import com.example.ProductCatalogue.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface ISearchService {

    Page<Product> search(String query, Integer pageSize, Integer pageNumber, List<SortParam> sortParams);
}
