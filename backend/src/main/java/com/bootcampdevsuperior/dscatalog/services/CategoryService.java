package com.bootcampdevsuperior.dscatalog.services;

import com.bootcampdevsuperior.dscatalog.dto.CategoryDto;
import com.bootcampdevsuperior.dscatalog.entities.Category;
import com.bootcampdevsuperior.dscatalog.repositories.CategoryRepository;
import com.bootcampdevsuperior.dscatalog.services.exceptions.DatabaseException;
import com.bootcampdevsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
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
    public CategoryDto update(Long id, CategoryDto dto) {
        try {
            Category entity = categoryRepository.getOne(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDto(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    public void delete(Long id) {
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation.");
        }
    }
}
