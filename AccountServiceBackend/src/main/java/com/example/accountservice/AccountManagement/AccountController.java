package com.example.accountservice.AccountManagement;

import com.example.accountservice.ResponseMessage.EntityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<EntityResponse<AccountDTO>> createAccount(@RequestBody AccountDTO dto) {
        EntityResponse<AccountDTO> response = accountService.createAccount(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<AccountDTO>> getAccount(@PathVariable Long id) {
        EntityResponse<AccountDTO> response = accountService.getAccount(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<AccountDTO>> updateAccount(@PathVariable Long id, @RequestBody AccountDTO dto) {
        EntityResponse<AccountDTO> response = accountService.updateAccount(id, dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<String>> deleteAccount(@PathVariable Long id) {
        EntityResponse<String> response = accountService.deleteAccount(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<EntityResponse<Page<Account>>> searchAccounts(
            @RequestParam(required = false) String iban,
            @RequestParam(required = false) String bicSwift,
            Pageable pageable
    ) {
        EntityResponse<Page<Account>> response = accountService.search(iban, bicSwift, pageable);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}