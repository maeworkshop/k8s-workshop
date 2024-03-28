package com.maemresen.ecommerce.ms.product.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maemresen.ecommerce.ms.product.catalog.dto.ProductCreateDto;
import com.maemresen.ecommerce.ms.product.catalog.dto.ProductResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@ContextConfiguration(classes = {
    ProductCatalogServiceApp.class,
    IntegrationTestPersistenceConfig.class})
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class ProductIT extends BaseControllerIT {

  private static final String NAME = "NAME";
  private static final String DESCRIPTION = "DESC";
  private static final Double PRICE = 1D;

  private static final String NAME_2 = "NAME_2";
  private static final String DESCRIPTION_2 = "DESC_2";
  private static final Double PRICE_2 = 2D;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void createProduct() throws Exception {
    assertFindAllEmpty();
    final ProductCreateDto invalidProductCreateDto = ProductCreateDto.builder().name(null)
        .description(DESCRIPTION).price(PRICE).build();
    createRequest(invalidProductCreateDto).andExpect(status().isBadRequest());

    assertFindAllEmpty();

    final ProductCreateDto productCreateDto = ProductCreateDto.builder().name(NAME)
        .description(DESCRIPTION).price(PRICE).build();
    final ProductResponseDto createdProduct = createAndParseResponse(productCreateDto);
    assertThat(createdProduct).isNotNull()
        .satisfies(created -> assertThat(created.getName()).isEqualTo(NAME))
        .satisfies(created -> assertThat(created.getDescription()).isEqualTo(DESCRIPTION))
        .satisfies(created -> assertThat(created.getPrice()).isEqualTo(PRICE));

    final ProductResponseDto foundProduct = findById(createdProduct.getId());
    assertThat(foundProduct.getId()).isEqualTo(createdProduct.getId());

    final ProductCreateDto productCreateDto2 = ProductCreateDto.builder().name(NAME_2)
        .description(DESCRIPTION_2).price(PRICE_2).build();
    final ProductResponseDto createdProduct2 = createAndParseResponse(productCreateDto2);

    final List<ProductResponseDto> products = parseResponse(
        findAllRequest().andExpect(status().isOk()), new TypeReference<>() {
        });
    assertThat(products).extracting(ProductResponseDto::getId)
        .containsExactlyInAnyOrder(createdProduct.getId(), createdProduct2.getId());
  }

  private void assertFindAllEmpty() throws Exception {
    final List<ProductResponseDto> products = findAll();
    assertThat(products).isEmpty();
  }

  private ProductResponseDto createAndParseResponse(final ProductCreateDto productCreateDto)
      throws Exception {
    return parseResponse(createRequest(productCreateDto), new TypeReference<>() {
    });
  }

  private ProductResponseDto findById(final Long id) throws Exception {
    return parseResponse(findByIdRequest(id).andExpect(status().isOk()), new TypeReference<>() {
    });
  }

  private List<ProductResponseDto> findAll() throws Exception {
    return parseResponse(findAllRequest().andExpect(status().isOk()), new TypeReference<>() {
    });
  }

  private ResultActions findAllRequest() throws Exception {
    return mockMvc.perform(get("/api/product"));
  }

  private ResultActions findByIdRequest(final Long id) throws Exception {
    return mockMvc.perform(get("/api/product/{id}", id));
  }

  private ResultActions createRequest(final ProductCreateDto productCreateDto) throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post("/api/product");
    return mockMvc.perform(withBody(requestBuilder, productCreateDto));
  }
}
