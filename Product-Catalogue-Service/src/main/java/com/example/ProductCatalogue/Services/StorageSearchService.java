package com.example.ProductCatalogue.Services;

import com.example.ProductCatalogue.DTOs.SortParam;
import com.example.ProductCatalogue.DTOs.SortType;
import com.example.ProductCatalogue.Models.Product;
import com.example.ProductCatalogue.Repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageSearchService implements ISearchService{

    @Autowired
    private ProductRepositories productRepo;


    @Override
    public Page<Product> search(String query, Integer pageSize, Integer pageNumber, List<SortParam> sortParams) {

        Sort sort = null;

      //  Sort sort = Sort.by("price").and(Sort.by("id").descending());//Tie breaker will be ID in descending order. It is hardcoded values. So we will have different approach now.

        if(!sortParams.isEmpty()){
            if(sortParams.get(0).getSortType().equals(SortType.ASC)){
                sort = sort.by(sortParams.get(0).getSortCriteria());
            }else{
                sort = sort.by(sortParams.get(0).getSortCriteria()).descending();
            }
        }

        for(int i=1;i<sortParams.size();i++){
            if(sortParams.get(i).getSortType().equals(SortType.ASC)){
                sort = sort.and(Sort.by(sortParams.get(i).getSortCriteria()));
            }else{
                sort = sort.and(Sort.by(sortParams.get(i).getSortCriteria()).descending());
            }
        }

        if(sort!=null){
            return productRepo.findByName(query, PageRequest.of(pageNumber,pageSize,sort));
            //Pagination involves setting offset and limit when JPA is absent, and using PageRequest to construct pageable slices of data automatically
        }else{
            return productRepo.findByName(query, PageRequest.of(pageNumber,pageSize));
        }
    }
}
