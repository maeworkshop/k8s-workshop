package com.maemresen.ecommerce.ms.product.catalog.business.service;

import com.maemresen.ecommerce.ms.product.catalog.business.mapper.ProductMapper;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductCreateDto;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductResponseDto;
import com.maemresen.ecommerce.ms.product.catalog.persistence.entity.Product;
import com.maemresen.ecommerce.ms.product.catalog.persistence.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public List<ProductResponseDto> findAll() {
    return repository.findAll().stream()
        .map(mapper::mapToResponseDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public Optional<Product> findById(final Long id) {
    return repository.findById(id);
  }

  public ProductResponseDto create(final ProductCreateDto productCreateDto) {
    final Product product = mapper.mapToEntity(productCreateDto);
    return mapper.mapToResponseDto(repository.save(product));
  }
}
