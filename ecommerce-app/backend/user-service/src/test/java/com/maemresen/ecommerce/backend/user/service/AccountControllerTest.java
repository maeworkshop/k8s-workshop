package com.maemresen.ecommerce.backend.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {


  private static final Account ACCOUNT = Account.builder()
      .id(1L)
      .username("USERNAME")
      .build();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountRepository accountRepository;

  @Nested
  class FindAccount {

    @Test
    void shouldFindAccount() throws Exception {
      when(accountRepository.findByUsername(ACCOUNT.getUsername())).thenReturn(
          Optional.of(ACCOUNT));
      mockMvc.perform(get("/account/%s".formatted(ACCOUNT.getUsername())))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(ACCOUNT.getId()))
          .andExpect(jsonPath("$.username").value(ACCOUNT.getUsername()));
    }

    @Test
    void shouldFindAccountReturnEmpty() throws Exception {
      when(accountRepository.findByUsername(ACCOUNT.getUsername())).thenReturn(Optional.empty());
      mockMvc.perform(get("/account/%s".formatted(ACCOUNT.getUsername())))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindAccountReturnError() throws Exception {
      when(accountRepository.findByUsername(ACCOUNT.getUsername())).thenThrow(
          RuntimeException.class);
      mockMvc.perform(get("/account/%s".formatted(ACCOUNT.getUsername())))
          .andExpect(status().isInternalServerError());
    }
  }

  @Nested
  class CreateAccount {

    @Test
    void shouldCreateAccount() throws Exception {
      when(accountRepository.save(any())).thenReturn(ACCOUNT);
      final AccountCreateDto accountCreateDto = new AccountCreateDto();
      accountCreateDto.setUsername(ACCOUNT.getUsername());
      mockMvc.perform(
              post("/account")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(accountCreateDto))
          )
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(ACCOUNT.getId()))
          .andExpect(jsonPath("$.username").value(ACCOUNT.getUsername()));
    }

    @Test
    void shouldCreateAccountReturnBadRequest() throws Exception {
      when(accountRepository.save(any())).thenReturn(ACCOUNT);
      final AccountCreateDto accountCreateDto = new AccountCreateDto();
      accountCreateDto.setUsername(null);
      mockMvc.perform(
              post("/account")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(accountCreateDto))
          )
          .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateAccountReturnError() throws Exception {
      when(accountRepository.save(any())).thenThrow(RuntimeException.class);
      final AccountCreateDto accountCreateDto = new AccountCreateDto();
      accountCreateDto.setUsername(ACCOUNT.getUsername());
      mockMvc.perform(
              post("/account")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(accountCreateDto))
          )
          .andExpect(status().isInternalServerError());
    }
  }
}
