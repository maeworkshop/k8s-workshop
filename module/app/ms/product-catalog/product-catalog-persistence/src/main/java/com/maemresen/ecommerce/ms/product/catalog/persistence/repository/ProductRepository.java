package com.maemresen.ecommerce.ms.product.catalog.persistence.repository;

import com.maemresen.ecommerce.ms.product.catalog.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}