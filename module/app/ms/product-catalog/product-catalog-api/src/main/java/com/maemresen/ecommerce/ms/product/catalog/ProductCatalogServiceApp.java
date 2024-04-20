package com.maemresen.ecommerce.ms.product.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.maemresen.ecommerce.ms.product.catalog.persistence.entity")
@EnableJpaRepositories("com.maemresen.ecommerce.ms.product.catalog.persistence.repository")
@SpringBootApplication(scanBasePackages = "com.maemresen.ecommerce.ms.product.catalog")
public class ProductCatalogServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(ProductCatalogServiceApp.class, args);
  }
}
