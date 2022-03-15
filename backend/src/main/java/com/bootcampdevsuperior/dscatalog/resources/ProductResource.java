package com.bootcampdevsuperior.dscatalog.resources;

import com.bootcampdevsuperior.dscatalog.dto.ProductDto;
import com.bootcampdevsuperior.dscatalog.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductResource {

    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "15") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        log.info("buscando todos os registros.");
        Page<ProductDto> list = productService.findAllPaged(pageRequest);
        log.info("registros encontrados.");
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        log.info("Buscando por id.");
        ProductDto dto = productService.findById(id);
        log.info("id encontrado.");
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ProductDto dto){
        log.info("Salvando os dados.");
        dto = productService.insert(dto);
        log.info("Dados salvos no banco.");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDto dto){
        log.info("Atualizando os dados.");
        dto = productService.update(id, dto);
        log.info("Dados atualizados.");
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deletando os dados.");
        productService.delete(id);
        log.info("Dados deletados.");
        return ResponseEntity.noContent().build();
    }
}
