package com.maemresen.ecommerce.backend.user.service;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountCreateDto {

  @NotBlank
  private String username;
}
