package com.maemresen.ecommerce.ms.product.catalog;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseControllerIT {

  private ObjectMapper objectMapper;

  @BeforeEach
  protected void init() {
    initMapper();
  }

  protected void initMapper() {
    objectMapper = new ObjectMapper();
  }

  protected <T> String toJson(final T object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  protected <T> T fromJson(final String json, final TypeReference<T> typeReference)
      throws IOException {
    return objectMapper.readValue(json, typeReference);
  }

  protected <T> T parseResponse(
      final ResultActions resultActions,
      final TypeReference<T> typeReference
  ) throws IOException {
    return fromJson(
        resultActions.andReturn().getResponse().getContentAsString(),
        typeReference
    );
  }

  protected <T> MockHttpServletRequestBuilder withBody(final MockHttpServletRequestBuilder builder,
                                                       final T body)
      throws JsonProcessingException {
    return builder.contentType(MediaType.APPLICATION_JSON).content(toJson(body));
  }
}
