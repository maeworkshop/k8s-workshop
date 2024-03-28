package com.maemresen.ecommerce.backend.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("account")
public class AccountController {

  private final AccountRepository accountRepository;

  @GetMapping("{username}")
  public ResponseEntity<Account> getByUsername(@PathVariable final String username) {
    return accountRepository.findByUsername(username)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Account> create(@RequestBody @Valid final AccountCreateDto accountCreateDto) {
    final Account account = new Account();
    account.setUsername(accountCreateDto.getUsername());
    return ResponseEntity.ok(accountRepository.save(account));
  }
}
