package com.maemresen.ecommerce.ms.product.catalog.controller;

import com.maemresen.ecommerce.ms.product.catalog.business.service.ProductService;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductCreateDto;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductResponseDto;
import com.maemresen.ecommerce.ms.product.catalog.persistence.entity.Product;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> findAll() {
    return ResponseEntity.ok(productService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Product> findById(@PathVariable final Long id) {
    return productService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(
      @RequestBody @Valid ProductCreateDto productCreateDto
  ) {
    return ResponseEntity.ok(productService.create(productCreateDto));
  }
}