package com.bootcampdevsuperior.dscatalog.repositories;

import com.bootcampdevsuperior.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
