package com.example.ProductCatalogue.Repositories;

import com.example.ProductCatalogue.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepositories extends JpaRepository<Category,Long> {


    Optional<Category> findById(Long id);

}
