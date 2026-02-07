package com.example.ProductCatalogue.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchDto {//SearchDto: A Java class used for passing search criteria as request body in a controller.
    private String query;

    private Integer pageSize; //Determines how many results are shown on one page in a paginated list.

    private Integer pageNumber;// The current page being viewed in a paginated list

    List<SortParam> sortParams= new ArrayList<>();

}
