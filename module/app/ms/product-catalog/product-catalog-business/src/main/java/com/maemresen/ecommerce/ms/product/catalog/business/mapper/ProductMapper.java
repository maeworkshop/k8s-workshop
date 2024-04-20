package com.maemresen.ecommerce.ms.product.catalog.business.mapper;

import com.maemresen.ecommerce.ms.product.catalog.dto.ProductCreateDto;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductResponseDto;
import com.maemresen.ecommerce.ms.product.catalog.persistence.entity.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

  ProductResponseDto mapToResponseDto(final Product product);

  Product mapToEntity(final ProductCreateDto productCreateDto);
}
