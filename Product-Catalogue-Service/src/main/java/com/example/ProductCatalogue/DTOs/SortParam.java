package com.example.ProductCatalogue.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {//Sort Param:Parameter indicating sorting preferences like ascending or descending
//SortParam DTO: A new DTO for managing sorting parameters was introduced. It contains attributes for sort type (ascending or descending) and the primary criteria on which to sort, like price or discount
    private SortType sortType;

    private String sortCriteria;

}
