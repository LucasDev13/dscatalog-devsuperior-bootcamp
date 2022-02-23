package com.bootcampdevsuperior.dscatalog.services;

import com.bootcampdevsuperior.dscatalog.dto.CategoryDto;
import com.bootcampdevsuperior.dscatalog.entities.Category;
import com.bootcampdevsuperior.dscatalog.repositories.CategoryRepository;
import com.bootcampdevsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private Category entity;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(){
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found."));
        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto insert(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto update(CategoryDto dto) {

    }
}
