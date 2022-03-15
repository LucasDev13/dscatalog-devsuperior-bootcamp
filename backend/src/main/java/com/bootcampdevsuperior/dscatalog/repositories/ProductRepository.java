package com.bootcampdevsuperior.dscatalog.repositories;

import com.bootcampdevsuperior.dscatalog.entities.Category;
import com.bootcampdevsuperior.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
