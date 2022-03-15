package com.bootcampdevsuperior.dscatalog.services;

import com.bootcampdevsuperior.dscatalog.dto.ProductDto;
import com.bootcampdevsuperior.dscatalog.entities.Product;
import com.bootcampdevsuperior.dscatalog.repositories.ProductRepository;
import com.bootcampdevsuperior.dscatalog.services.exceptions.DatabaseException;
import com.bootcampdevsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll(){
        List<Product> list = productRepository.findAll();
        return list.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(ProductDto::new);
    }

    @Transactional
    public ProductDto findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new ProductDto(entity, entity.getCategories());
    }

    @Transactional
    public ProductDto insert(ProductDto dto) {
        Product entity = new Product();
//        entity.setName(dto.getName());
        entity = productRepository.save(entity);
        return new ProductDto(entity);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        try {
            Product entity = productRepository.getOne(id);
//            entity.setName(dto.getName());
            entity = productRepository.save(entity);
            return new ProductDto(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    public void delete(Long id) {
        try{
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id " + id + " not found");
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation.");
        }
    }
}
