package com.example.accountservice.AccountManagement;

import com.example.accountservice.ResponseMessage.EntityResponse;
import com.example.accountservice.Utils.shared.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public EntityResponse<AccountDTO> createAccount(AccountDTO dto) {
        Account account = Account.builder()
                .iban(dto.getIban())
                .bicSwift(dto.getBicSwift())
                .customerId(dto.getCustomerId())
                .build();
        account = accountRepository.save(AuditTrailService.POSTAudit((account)));
        dto.setId(account.getId());
        return new EntityResponse<>("Account created successfully", dto, 201);
    }

    public EntityResponse<AccountDTO> getAccount(Long id) {
        Optional<Account> accountOpt = accountRepository.findByIdAndDeletedFlag(id, 'N');
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            AccountDTO dto = AccountDTO.builder()
                    .id(account.getId())
                    .iban(account.getIban())
                    .bicSwift(account.getBicSwift())
                    .customerId(account.getCustomerId())
                    .build();
            return new EntityResponse<>("Account found", dto, 200);
        } else {
            return new EntityResponse<>("Account not found", null, 404);
        }
    }


    public EntityResponse<AccountDTO> updateAccount(Long id, AccountDTO dto) {
        Optional<Account> accountOpt = accountRepository.findByIdAndDeletedFlag(id, 'N');
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setIban(dto.getIban());
            account.setBicSwift(dto.getBicSwift());
            accountRepository.save(AuditTrailService.MODIFYAudit(accountOpt.get(), account));
            dto.setId(account.getId());
            return new EntityResponse<>("Account updated successfully", dto, 200);
        } else {
            return new EntityResponse<>("Account not found", null, 404);
        }
    }


    public EntityResponse<String> deleteAccount(Long id) {
        Optional<Account> accountOpt = accountRepository.findByIdAndDeletedFlag(id, 'N');

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            accountRepository.save(AuditTrailService.DELETEAudit(account));
            return new EntityResponse<>("Account deleted successfully", null, 200);
        } else {
            return new EntityResponse<>("Account not found", null, 404);
        }
    }



    public EntityResponse<Page<Account>> search(String iban, String bicSwift, Pageable pageable) {
        Page<Account> page = accountRepository.search(iban, bicSwift, pageable);
        return new EntityResponse<>("Search completed", page, 200);
    }
}