package com.bootcampdevsuperior.dscatalog.resources;

import com.bootcampdevsuperior.dscatalog.dto.CategoryDto;
import com.bootcampdevsuperior.dscatalog.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryResource {

    private static final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        log.info("buscando todos os registros.");
        List<CategoryDto> list = categoryService.findAll();
        log.info("registros encontrados.");
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        log.info("Buscando por id.");
        CategoryDto dto = categoryService.findById(id);
        log.info("id encontrado.");
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody CategoryDto dto){
        log.info("Salvando os dados.");
        dto = categoryService.insert(dto);
        log.info("Dados salvos no banco.");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryDto dto){
        log.info("Atualizando os dados.");
        dto = categoryService.update(id, dto);
        log.info("Dados atualizados.");
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deletando os dados.");
        categoryService.delete(id);
        log.info("Dados deletados.");
        return ResponseEntity.noContent().build();
    }
}
