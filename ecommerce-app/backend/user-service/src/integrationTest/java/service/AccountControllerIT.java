package service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maemresen.ecommerce.backend.user.service.AccountCreateDto;
import com.maemresen.ecommerce.backend.user.service.UserServiceApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = UserServiceApp.class)
@ContextConfiguration(classes = IntegrationTestPersistenceConfig.class)
@AutoConfigureMockMvc
class AccountControllerIT {

  private static final String USERNAME = "username";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void createAccount() throws Exception {
    this.mockMvc.perform(get("/account/%s".formatted(USERNAME)))
        .andExpect(status().isNotFound());

    final AccountCreateDto accountCreateDto = new AccountCreateDto();
    accountCreateDto.setUsername(USERNAME);

    this.mockMvc.perform(
            post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto))
        )
        .andExpect(status().isOk());

    this.mockMvc.perform(get("/account/%s".formatted(USERNAME)))
        .andExpect(status().isOk());
  }
}
