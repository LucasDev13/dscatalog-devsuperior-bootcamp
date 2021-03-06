package com.bootcampdevsuperior.dscatalog.resources;

import com.bootcampdevsuperior.dscatalog.dto.CategoryDto;
import com.bootcampdevsuperior.dscatalog.entities.Category;
import com.bootcampdevsuperior.dscatalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<CategoryDto> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        CategoryDto dto = categoryService.findById(id);
        return ResponseEntity.ok().body(dto);
    }
}
