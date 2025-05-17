package com.example.accountservice;

import com.example.accountservice.AccountManagement.Account;
import com.example.accountservice.AccountManagement.AccountDTO;
import com.example.accountservice.AccountManagement.AccountRepository;
import com.example.accountservice.AccountManagement.AccountService;
import com.example.accountservice.ResponseMessage.EntityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountService accountService;

	private Account account;
	private AccountDTO accountDTO;

	@BeforeEach
	void setup() {
		accountDTO = AccountDTO.builder()
				.iban("DE1234567890")
				.bicSwift("DEUTDEFF")
				.customerId(1L)
				.build();

		account = Account.builder()
				.iban("DE1234567890")
				.bicSwift("DEUTDEFF")
				.customerId(1L)
				.build();
		account.setId(1L);

	}

	@Test
	void testCreateAccount() {
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		EntityResponse<AccountDTO> response = accountService.createAccount(accountDTO);
		AccountDTO result = response.getEntity();

		assertNotNull(result.getId());
		assertEquals("Account created successfully", response.getMessage());
		assertEquals(201, response.getStatusCode());
		assertEquals(accountDTO.getIban(), result.getIban());

		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	void testGetAccountFound() {
		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(account));

		EntityResponse<AccountDTO> response = accountService.getAccount(1L);
		AccountDTO result = response.getEntity();

		assertEquals(200, response.getStatusCode());
		assertEquals("Account found", response.getMessage());
		assertNotNull(result);
		assertEquals(account.getIban(), result.getIban());
	}

	@Test
	void testGetAccountNotFound() {
		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.empty());

		EntityResponse<AccountDTO> response = accountService.getAccount(1L);

		assertEquals(404, response.getStatusCode());
		assertEquals("Account not found", response.getMessage());
		assertNull(response.getEntity());
	}

	@Test
	void testUpdateAccountFound() {
		AccountDTO updatedDTO = AccountDTO.builder()
				.iban("DE0987654321")
				.bicSwift("NEDSZAJJ")
				.customerId(1L)
				.build();

		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		EntityResponse<AccountDTO> response = accountService.updateAccount(1L, updatedDTO);
		AccountDTO result = response.getEntity();

		assertEquals(200, response.getStatusCode());
		assertEquals("Account updated successfully", response.getMessage());
		assertEquals(updatedDTO.getIban(), result.getIban());

		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	void testUpdateAccountNotFound() {
		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.empty());

		EntityResponse<AccountDTO> response = accountService.updateAccount(1L, accountDTO);

		assertEquals(404, response.getStatusCode());
		assertEquals("Account not found", response.getMessage());
		assertNull(response.getEntity());
	}

	@Test
	void testDeleteAccountFound() {
		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		EntityResponse<String> response = accountService.deleteAccount(1L);

		assertEquals(200, response.getStatusCode());
		assertEquals("Account deleted successfully", response.getMessage());

		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	void testDeleteAccountNotFound() {
		when(accountRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.empty());

		EntityResponse<String> response = accountService.deleteAccount(1L);

		assertEquals(404, response.getStatusCode());
		assertEquals("Account not found", response.getMessage());

		verify(accountRepository, never()).save(any());
	}

	@Test
	void testSearchAccounts() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Account> page = new PageImpl<>(Collections.singletonList(account));

		when(accountRepository.search(any(), any(), eq(pageable))).thenReturn(page);

		EntityResponse<Page<Account>> response = accountService.search("DE123", "DEUTDEFF", pageable);

		assertEquals(200, response.getStatusCode());
		assertEquals("Search completed", response.getMessage());
		assertNotNull(response.getEntity());
		assertEquals(1, response.getEntity().getTotalElements());

		verify(accountRepository, times(1)).search(any(), any(), eq(pageable));
	}
}